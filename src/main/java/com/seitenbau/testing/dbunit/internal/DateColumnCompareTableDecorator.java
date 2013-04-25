package com.seitenbau.testing.dbunit.internal;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;



public class DateColumnCompareTableDecorator implements ITable
{

  public ITable table;
  
  private String rangeColumn;

  public DateColumnCompareTableDecorator(String rangeColumn, ITable table)
  {
    this.table = table;
    this.rangeColumn = rangeColumn;
  }

  public int getRowCount()
  {
    return table.getRowCount();
  }

  public ITableMetaData getTableMetaData()
  {
    ITableMetaData tableMetaData = table.getTableMetaData();
    DefaultTableMetaData defaultTableMetaData = null;
    try
    {
      Column[] newColumns = new Column[tableMetaData.getColumns().length];
      Column[] columns = tableMetaData.getColumns();
      for (int i=0; i < columns.length; i++)
      {
        Column column = columns[i];
        if(isRangeColumn(column.getColumnName()))
        {
          newColumns[i] = new Column(column.getColumnName(), new CompareDateRangeDataType());
        }
        else
        {
          newColumns[i] = new Column(column.getColumnName(),column.getDataType());
        }
        defaultTableMetaData = new DefaultTableMetaData(tableMetaData.getTableName(), newColumns);
      }
    }
    catch (DataSetException e)
    {
    }
    return defaultTableMetaData;
  }

  public Object getValue(int row, String column) throws DataSetException
  {
    if (isRangeColumn(column))
    {
      return createCompareModel(row, column);
    }
    else
    {
      return table.getValue(row, column);
    }
  }

  public Object createCompareModel(int row, String column)
      throws DataSetException
  {
    return new DateCompareType(table.getValue(row, column));
  }

  private boolean isRangeColumn(String column)
  {
    if (rangeColumn.equals(column))
    {
      return true;
    }
    return false;
  }

}
