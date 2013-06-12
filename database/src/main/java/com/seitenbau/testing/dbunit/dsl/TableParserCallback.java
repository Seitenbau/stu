package com.seitenbau.testing.dbunit.dsl;

import groovy.lang.Closure;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Optional;
import com.seitenbau.testing.dbunit.dsl.TableParserContext.ParsedRowCallback;

public class TableParserCallback<R, G, D extends DatabaseReference> implements ParsedRowCallback
{

  private final TableParserAdapter<R, G, D> _tableAdapter;

  private TableRowModel _head;

  private int _columns;

  private int _lineNr;

  private ColumnBinding<R, G> _refColumn;

  private ColumnBinding<R, G> _idColumn;
  
  private List<Integer> _traversedColumns;

  public TableParserCallback(TableParserAdapter<R, G, D> tableAdapter)
  {
    _tableAdapter = tableAdapter;
    _head = null;
    _columns = 0;
    _lineNr = 0;
    _refColumn = null;
    _idColumn = null;
    _traversedColumns = Collections.<Integer>emptyList();
  }

  @Override
  public void parsedRow(TableRowModel row)
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

    if (value instanceof DatabaseReference)
    {
      DatabaseReference ref = (DatabaseReference) value;
      column.setReference(rowbuilder, ref);
    }
    else if (value instanceof Closure)
    {
      // call closure values after row has been built and registered
      LazyValueClosure lazyValue = new LazyValueClosure((Closure<?>)value);
      column.setLazyValue(rowbuilder, lazyValue);
    }
    else
    {
      column.set(rowbuilder, value);
    }
  }
  
  @SuppressWarnings("unchecked")
  private void readHeadRow(TableRowModel row)
  {
    _head = row;
    _columns = _head.getColumnCount();
    _traversedColumns = _head.getTraversableColumns();
    _refColumn = null;
    _idColumn = null;

    if (_head.getIdentifierColumnIndex() != -1)
    {
      _idColumn = (ColumnBinding<R, G>) _head.getValue(_head.getIdentifierColumnIndex());
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

    private final Object id;

    private R builder;

    public BuilderFinderOrCreator(TableRowModel row)
    {
      this.row = row;
      ref = getRefValue();
      id = getIdValue();

      R builderByReference = getBuilderByReference();
      R builderById = getBuilderById();
      builder = getBuilderFromTwo(builderByReference, builderById);
      if (builder == null)
      {
        builder = createRowBuilder();
      }

      setManualIdOnRowBuilder();
      bindBuilderToScope();
      if (builderByReference == null && ref != null)
      {
        updateExternalReferences();
      }
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
    
    private Object getIdValue()
    {
      if (_idColumn != null)
      {
        Object value = row.getValue(_head.getIdentifierColumnIndex());
        if (!(value instanceof NoValue))
        {
          return value;
        }
      }
      return null;
    }

    private R getBuilderById()
    {
      if (id == null)
      {
        return null;
      }

      Optional<R> builderById = _idColumn.getWhere(_tableAdapter.getWhere(), 
          CastUtil.cast(id, _idColumn.getDataType()));
      if (builderById.isPresent())
      {
        return builderById.get();
      }
      return null;
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

    private void setManualIdOnRowBuilder()
    {
      if (id != null)
      {
        _idColumn.set(builder, id);
      }
    }

    private void updateExternalReferences()
    {
      _tableAdapter.handleReferences(ref, builder);
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