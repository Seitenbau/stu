package com.seitenbau.stu.dbunit.dsl;

import groovy.lang.Closure;
import groovy.lang.GString;

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

  private ColumnBinding<R, G> _refColumn;

  private List<ColumnBinding<R, G>> _identifierColumns;

  private List<Integer> _traversedColumns;

  TableParsedRowHandler(TableParserAdapter<R, G, D> tableAdapter)
  {
    _tableAdapter = tableAdapter;
    _head = null;
    _columns = 0;
    _refColumn = null;
    _identifierColumns = new LinkedList<ColumnBinding<R, G>>();
    _traversedColumns = Collections.<Integer> emptyList();
  }

  public void handleRow(TableRowModel row)
  {
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
      value = new FutureClosure((Closure<?>) value);
    }
    // handle Groovy Strings in the table dsl
    else if(value instanceof GString)
    {
       value = value.toString();
    }
    try
    {
      column.set(rowbuilder, value);
    }
    catch (Exception e)
    {
      throw new TableParserException("Cannot set value <" + value + "> of type " + value.getClass().getName()
          + ", expected class " + column.getDataType().getJavaTypeClass().getName() + " in " + row, row, e);
    }
  }

  @SuppressWarnings("unchecked")
  private void readHeadRow(TableRowModel row)
  {
    _head = row;
    _columns = _head.getColumnCount();
    _traversedColumns = _head.getTraversableColumns();
    _refColumn = null;
    _identifierColumns = new LinkedList<ColumnBinding<R, G>>();

    for (Integer i : _head.getIdentifierColumnIndexes())
    {
      _identifierColumns.add((ColumnBinding<R, G>) _head.getValue(i));
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
      throw new TableParserException("Column count does not match in " + row, row);
    }
  }

  private class BuilderFinderOrCreator
  {

    private final TableRowModel row;

    private final D ref;

    private final List<Object> identifierValues;

    private R builder;

    public BuilderFinderOrCreator(TableRowModel row)
    {
      this.row = row;
      ref = getRefValue();
      identifierValues = getIdentifierValues();

      R builderByReference = getBuilderByReference();
      R builderById = getBuilderByIdentifierValues();
      builder = getBuilderFromTwo(builderByReference, builderById);
      if (builder == null)
      {
        builder = createRowBuilder();
      }

      setIdentifierValuesOnRowBuilder();
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
      throw new TableParserException("Invalid use of identifiers. Might be a trial to use same ID for different Refs?", row);
    }

    @SuppressWarnings("unchecked")
    private D getRefValue()
    {
      if (_refColumn != null)
      {
        Object refValue = row.getValue(_head.getRefColumnIndex());
        if (!(refValue instanceof NoValue))
        {
          return (D) refValue;
        }
      }
      return null;
    }

    private List<Object> getIdentifierValues()
    {
      List<Object> result = new ArrayList<Object>();
      for (Integer i : _head.getIdentifierColumnIndexes())
      {
        result.add(row.getValue(i));
      }
      return result;
    }

    private R getBuilderByIdentifierValues()
    {
      R result = null;

      for (int index = 0; index < _identifierColumns.size(); index++)
      {
        Object id = identifierValues.get(index);
        if (id == null || id instanceof NoValue)
        {
          continue;
        }

        ColumnBinding<R, G> col = _identifierColumns.get(index);
        Optional<R> builderById = col.getWhere(_tableAdapter.getWhere(), CastUtil.cast(id, col.getDataType()));
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
          throw new TableParserException("Identifier values differ in table data", row);
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

    private void setIdentifierValuesOnRowBuilder()
    {
      for (int index = 0; index < _identifierColumns.size(); index++)
      {
        Object value = identifierValues.get(index);
        if (value == null || value instanceof NoValue)
        {
          continue;
        }

        ColumnBinding<R, G> col = _identifierColumns.get(index);
        col.set(builder, value);
      }
    }

    private void bindBuilderToScope()
    {
      if (ref != null)
      {
        _tableAdapter.bindToDataSet(ref, builder);
      }
    }

    R getBuilder()
    {
      return builder;
    }

  }

}