package com.seitenbau.stu.database.manipulate;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.IRowValueProvider;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.RowFilterTable;
import org.dbunit.dataset.filter.IRowFilter;

import com.seitenbau.stu.database.modifier.IDataSetFilter;

public class RemoveRowFromTable implements IDataSetFilter
{
  private String fTableName;

  private String fColumn;

  private Object fColumnValue;

  public RemoveRowFromTable(String tableName, String column, Object columnValue)
  {
    fTableName = tableName;
    fColumn = column;
    fColumnValue = columnValue;
  }

  public IDataSet filter(IDataSet current) throws Exception
  {
    return filterOutTableRows(current, fTableName, fColumn, fColumnValue);
  }

  public static IDataSet filterOutTableRows(IDataSet theDataSet, String tableName, final String column,
      final Object columnValue) throws Exception
  {
    if (tableName == null)
    {
      return theDataSet;
    }
    DefaultDataSet newDataSet = new DefaultDataSet();
    for (ITableIterator iter = theDataSet.iterator(); iter.next();)
    {
      String curName = iter.getTable().getTableMetaData().getTableName();
      boolean specialTreated = false;
      if (curName.equals(tableName))
      {
        ITable oldtable = iter.getTable();
        IRowFilter rowFilter = new IRowFilter()
        {
          public boolean accept(IRowValueProvider rowValueProvider)
          {
            try
            {
              return !rowValueProvider.getColumnValue(column).equals(columnValue);
            }
            catch (DataSetException e)
            {
              e.printStackTrace();
              return false;
            }
          }
        };
        newDataSet.addTable(new RowFilterTable(oldtable, rowFilter));
        specialTreated = true;
        break;
      }
      if (!specialTreated)
      {
        newDataSet.addTable(iter.getTable());
      }
    }
    return newDataSet;
  }
}
