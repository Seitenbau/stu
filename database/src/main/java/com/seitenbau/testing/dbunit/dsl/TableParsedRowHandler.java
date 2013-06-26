package com.seitenbau.testing.dbunit.dsl;

import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Optional;

public class TableParsedRowHandler<R, G, D extends DatabaseRef>
{

  private final TableParserAdapter<R, G, D> _tableAdapter;

  private TableRowModel _head;

  private int _columns;

  private int _lineNr;

  private ColumnBinding<R, G> _refColumn;

  private List<ColumnBinding<R, G>> _uniqueColumns;

  private List<Integer> _traversedColumns;

  TableParsedRowHandler(TableParserAdapter<R, G, D> tableAdapter)
  {
    _tableAdapter = tableAdapter;
    _head = null;
    _columns = 0;
    _lineNr = 0;
    _refColumn = null;
    _uniqueColumns = new LinkedList<ColumnBinding<R, G>>();
    _traversedColumns = Collections.<Integer>emptyList();
  }

  public void handleRow(TableRowModel row)
  {
    _lineNr++;
    if (row.isHeadRow())
    {
      readHeadRow(row);
      return;
    }

    assertCorrectColumnCount(row);

    R rowbuilder = new BuilderFinderOrCreator(row).getBuilder();

    for (int columnIndex : _traversedColumns)
    {
      handleColumn(columnIndex, row, rowbuilder);
    }
  }

  private void handleColumn(int columnIndex, TableRowModel row, R rowbuilder)
  {
    @SuppressWarnings("unchecked")
    ColumnBinding<R, G> column = (ColumnBinding<R, G>) _head.getValue(columnIndex);
    Object value = row.getValue(columnIndex);
    if (value instanceof NoValue)
    {
      return;
    }

    // Closures are considered to be Future values
    if (value instanceof Closure)
    {
      value = new FutureClosure((Closure<?>)value);
    }

    column.set(rowbuilder, value);
  }

  @SuppressWarnings("unchecked")
  private void readHeadRow(TableRowModel row)
  {
    _head = row;
    _columns = _head.getColumnCount();
    _traversedColumns = _head.getTraversableColumns();
    _refColumn = null;
    _uniqueColumns = new LinkedList<ColumnBinding<R, G>>();

    for (Integer i : _head.getUniqueColumnIndexes())
    {
      _uniqueColumns.add((ColumnBinding<R, G>) _head.getValue(i));
    }

    if (_head.getRefColumnIndex() != -1)
    {
      _refColumn = (ColumnBinding<R, G>) _head.getValue(_head.getRefColumnIndex());
    }
  }

  private void assertCorrectColumnCount(TableRowModel row)
  {
    if (row.getColumnCount() != _columns)
    {
      throwColumnsDoNotMatchException(_lineNr, row);
    }
  }

  private void throwColumnsDoNotMatchException(int lineNr, TableRowModel row)
  {
    throwException("column count does not match", lineNr, row);
  }

  private void throwException(String message, int lineNr, TableRowModel row)
  {
    StringBuilder builder = new StringBuilder();
    builder.append("Error in " + _tableAdapter.getTableName() + ", line " + lineNr + ": " + message);
    if (row.getValues().size() > 0)
    {
      builder.append(" [TableRowModel: ");
      for (Object value : row.getValues())
      {
        builder.append(value);
        builder.append(" | ");
      }
      builder.setLength(builder.length() - 3);
      builder.append("]");
    }
    throw new IllegalArgumentException(builder.toString());
  }

  private class BuilderFinderOrCreator
  {

    private final TableRowModel row;

    private final D ref;

    private final List<Object> uniqueValues;

    private R builder;

    public BuilderFinderOrCreator(TableRowModel row)
    {
      this.row = row;
      ref = getRefValue();
      uniqueValues = getUniqueValues();

      R builderByReference = getBuilderByReference();
      R builderById = getBuilderByUniqueValues();
      builder = getBuilderFromTwo(builderByReference, builderById);
      if (builder == null)
      {
        builder = createRowBuilder();
      }

      setUniqueValuesOnRowBuilder();
      bindBuilderToScope();
    }

    /**
     * Ensures both builders represent the same instance (have the
     * same value, or at least one is null...)
     * @param builderByReference
     * @param builderById
     */
    private R getBuilderFromTwo(R builderByReference, R builderById)
    {
      // no problem if both are equal
      if (builderByReference == builderById)
      {
        return builderByReference;
      }

      // no problem if one is null, return other
      if (builderByReference == null || builderById == null)
      {
        if (builderByReference == null)
        {
          return builderById;
        }
        else
        {
          return builderByReference;
        }
      }

      // so none is null and they differ
      throw new RuntimeException("Table structure failure, [Trial to use same ID for different Refs]");
    }

    @SuppressWarnings("unchecked")
    private D getRefValue()
    {
      if (_refColumn != null) {
        Object refValue = row.getValue(_head.getRefColumnIndex());
        if (!(refValue instanceof NoValue))
        {
          return (D) refValue;
        }
      }
      return null;
    }

    private List<Object> getUniqueValues()
    {
      List<Object> result = new ArrayList<Object>();
      for (Integer i : _head.getUniqueColumnIndexes())
      {
        result.add(row.getValue(i));
      }
      return result;
    }

    private R getBuilderByUniqueValues()
    {
      R result = null;

      for (int index = 0; index < _uniqueColumns.size(); index++)
      {
        Object id = uniqueValues.get(index);
        if (id == null || id instanceof NoValue)
        {
          continue;
        }

        ColumnBinding<R, G> col = _uniqueColumns.get(index);
        Optional<R> builderById = col.getWhere(_tableAdapter.getWhere(),
            CastUtil.cast(id, col.getDataType()));
        if (!builderById.isPresent())
        {
          continue;
        }

        if (result == null)
        {
          result = builderById.get();
        }

        if (builderById.get() != result)
        {
          throw new RuntimeException("Unique values differ in table data");
        }
      }

      return result;
    }

    private R getBuilderByReference()
    {
      if (ref == null)
      {
        return null;
      }
      return _tableAdapter.getRowByReference(ref);
    }

    private R createRowBuilder()
    {
      return _tableAdapter.insertRow();
    }

    private void setUniqueValuesOnRowBuilder()
    {
      for (int index = 0; index < _uniqueColumns.size(); index++)
      {
        Object value = uniqueValues.get(index);
        if (value == null || value instanceof NoValue)
        {
          continue;
        }

        ColumnBinding<R, G> col = _uniqueColumns.get(index);
        col.set(builder, value);
      }
    }

    private void bindBuilderToScope()
    {
      if (ref != null) {
        _tableAdapter.bindToDataSet(ref, builder);
      }
    }

    R getBuilder()
    {
      return builder;
    }

  }

}