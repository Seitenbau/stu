package com.seitenbau.testing.dbunit.dsl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TableRowModel
{
  
  private List<Object> values = new ArrayList<Object>();

  private final boolean isHeadRow;

  private int refColumnIndex;

  private int idColumnIndex;

  public TableRowModel(Object value)
  {
    refColumnIndex = -1;
    idColumnIndex = -1;
    isHeadRow = value instanceof ColumnBinding;
    addValue(value);
  }

  public TableRowModel or(Object arg)
  {
    addValue(arg);
    return this;
  }

  private void addValue(Object value)
  {
    if (isHeadRow)
    {
      if (!(value instanceof ColumnBinding))
      {
        throw new RuntimeException("Cannot mix ColumnBindings and values within one row");
      }
      
      if (values.contains(value))
      {
        throw new RuntimeException("Cannot use a column more than once in a table");
      }

      ColumnBinding<?, ?> column = (ColumnBinding<?, ?>) value;
      if (column.isRefColumn()) {
        refColumnIndex = values.size();
      }
      if (column.isIdentifierColumn()) {
        idColumnIndex = values.size();
      }
    }
    values.add(value);
  }

  public List<Object> getValues()
  {
    return values;
  }

  public int getColumnCount()
  {
    return values.size();
  }

  public boolean isHeadRow()
  {
    return isHeadRow;
  }

  public int getRefColumnIndex()
  {
    if (!isHeadRow)
    {
      throw new RuntimeException("Cannot query REF column index from non-head row");
    }
    return refColumnIndex;
  }

  public int getIdentifierColumnIndex()
  {
    if (!isHeadRow)
    {
      throw new RuntimeException("Cannot query ID column index from non-head row");
    }
    return idColumnIndex;
  }

  public Object getValue(int index)
  {
    return values.get(index);
  }
  
  public List<Integer> getTraversableColumns()
  {
    if (!isHeadRow)
    {
      throw new RuntimeException("Cannot query ID column index from non-head row");
    }
    List<Integer> result = new LinkedList<Integer>();
    for (int i = 0; i < values.size(); i++) 
    {
      if (i == refColumnIndex || i == idColumnIndex)
      {
        continue;
      }
      
      result.add(Integer.valueOf(i));
    }
    return result;
  }
}
