package com.seitenbau.stu.dbunit.manipulate;

import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITableIterator;

import com.seitenbau.stu.dbunit.modifier.IDataSetFilter;

public class IncludeTable implements IDataSetFilter
{
  private String[] tableNames;

  public IncludeTable(String... includeOnly)
  {
    tableNames = includeOnly;
  }

  /**
   * {@inheritDoc}
   */
  public IDataSet filter(IDataSet current) throws Exception
  {
    return filterInTableRows(current, tableNames);
  }

  /**
   * Deletes the content of the specified tables for the given data
   * set.
   * 
   * @param dataSet The data set the content should be deleted from.
   *        Not {@code null}
   * 
   * @param tableNames Optional list of table names that should be
   *        processed.
   * @return The modified data set with the empty tables. If no list
   *         of table names was provided or list is {@code null}, the
   *         data set is returned without modifications.
   * 
   * @throws Exception If an error occurs while filtering the table
   *         rows.
   */
  public static IDataSet filterInTableRows(IDataSet dataSet, String... tableNames) throws Exception
  {
    if (tableNames == null)
    {
      return dataSet;
    }
    DefaultDataSet ds = new DefaultDataSet();
    for (ITableIterator iter = dataSet.iterator(); iter.next();)
    {
      String tabName = iter.getTable().getTableMetaData().getTableName();
      for (String name : tableNames)
      {
        if (tabName.equalsIgnoreCase(name))
        {
          ds.addTable(iter.getTable());
        }
      }
    }
    return ds;
  }
}
