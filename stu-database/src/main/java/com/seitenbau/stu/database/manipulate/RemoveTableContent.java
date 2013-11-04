package com.seitenbau.stu.database.manipulate;

import org.dbunit.dataset.IDataSet;

import com.seitenbau.stu.database.modifier.IDataSetFilter;
import com.seitenbau.stu.database.tester.DataSetUtil;

public class RemoveTableContent implements IDataSetFilter
{

  private String[] tableName = null;

  /**
   * Remove all rows from the given tables. Convenient class to
   * {@link DataSetUtil#filterOutTableRows(IDataSet, String...)}
   * 
   * @param tablesToMakeEmpty List of all tables that should be
   *        cleared.
   */
  public RemoveTableContent(String... tablesToMakeEmpty)
  {
    tableName = tablesToMakeEmpty;
  }

  /**
   * {@inheritDoc}
   */
  public IDataSet filter(IDataSet current) throws Exception
  {
    return DataSetUtil.filterOutTableRows(current, tableName);
  }

}
