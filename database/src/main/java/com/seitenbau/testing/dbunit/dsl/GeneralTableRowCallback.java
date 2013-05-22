package com.seitenbau.testing.dbunit.dsl;

import java.util.Map;

public class GeneralTableRowCallback<R, F, D extends DatabaseReference> implements IParsedTableRowCallback
{
  private TableRowModel _head;

  private int _columns;

  private int _lineNr;

  private int _colRef;

  private int _colId;

  private final Map<D, R> _usedRefs;

  private final ITableAdapter<R, F> _tableAdapter;

  public GeneralTableRowCallback(ITableAdapter<R, F> tableAdapter, Map<D, R> usedRefs)
  {
    _tableAdapter = tableAdapter;
    _usedRefs = usedRefs;
    _head = null;
    _columns = 0;
    _lineNr = 0;
    _colRef = -1;
    _colId = -1;
  }
  
  R getRowBuilder(TableRowModel row)
  {
    R result = null;
    R builderById = null;
    R builderByRef = null;

    if (_colId != -1)
    {
      @SuppressWarnings("unchecked")
      ColumnBinding<R, F> column = (ColumnBinding<R, F>) _head.getValue(_colId);
      try
      {
        Object id = CastUtil.cast(row.getValue(_colId), column.getDataType());
        builderById = column.query(_tableAdapter.getFindWhere(), id);
      }
      catch (Exception e)
      {
      }
    }

    if (_colRef != -1)
    {
      builderByRef = _usedRefs.get(row.getValue(_colRef));
    }

    if (builderByRef != null || builderById != null)
    {
      if (builderByRef == builderById)
      {
        result = builderByRef;
      }
      else if (builderByRef == null)
      {
        result = builderById;
      }
      else if (builderById == null)
      {
        result = builderByRef;
      }
      else
      {
        // ERROR
        result = builderByRef;
      }
    }
    else
    {
      result = _tableAdapter.insertRow();
    }

    if (_colId != -1)
    {
      @SuppressWarnings("unchecked")
      ColumnBinding<R, F> column = (ColumnBinding<R, F>) _head.getValue(_colId);
      Object value = CastUtil.cast(row.getValue(_colId), column.getDataType());
      column.set(result, value);
    }
    return result;
  }

  public void parsedRow(TableRowModel row)
  {
    _lineNr++;
    if (row.isHeadRow())
    {
      _head = row;
      _colRef = _head.getRefColumn();
      _colId = _head.getIdColumn();
      _columns = _head.getColumnCount();
      return;
    }

    if (row.getColumnCount() != _columns)
      throwColumnsDoNotMatchException(_lineNr, row);
    R rowbuilder = getRowBuilder(row);

    // def resultRow = new SchreibtTableRow()
    for (int columnIndex = 0; columnIndex < _columns; columnIndex++)
    {
      if (columnIndex == _colRef || columnIndex == _colId)
      {
        continue;
      }

      @SuppressWarnings("unchecked")
      ColumnBinding<R, F> column = (ColumnBinding<R, F>) _head.getValue(columnIndex);
      column.set(rowbuilder, row.getValue(columnIndex));
    }

    if (_colRef != -1 && row.getValue(_colRef) != null && !_usedRefs.keySet().contains(row.getValue(_colRef)))
    {
      @SuppressWarnings("unchecked")
      D ref = (D) row.getValue(_colRef);
      _usedRefs.put(ref, rowbuilder);
      // println "Used ref in Schreibt: " + row.values[colRef]
    }
  }

  private void throwColumnsDoNotMatchException(int lineNr, TableRowModel row)
  {
    throwException("column count does not match", lineNr, row);
  }

  private void throwRedefinedIdException(int lineNr, TableRowModel row)
  {
    throwException("Id redefined", lineNr, row);
  }

  private void throwException(String message, int lineNr, TableRowModel row)
  {
    StringBuilder builder = new StringBuilder();
    builder.append("Error in Schreibt, line " + lineNr + ": " + message);
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
}