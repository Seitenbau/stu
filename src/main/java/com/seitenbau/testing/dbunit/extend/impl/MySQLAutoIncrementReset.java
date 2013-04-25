package com.seitenbau.testing.dbunit.extend.impl;

import java.util.Arrays;
import java.util.HashSet;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITableIterator;

import com.seitenbau.testing.dbunit.extend.DatabaseTesterCleanAction;
import com.seitenbau.testing.dbunit.tester.DatabaseTesterBase;

/**
 * Helper Action to reset a table's AUTO_INCREMENT in MySQL Database.
 */
public class MySQLAutoIncrementReset implements DatabaseTesterCleanAction
{
  protected int _startIndex = 10000;

  /** List of tables which are explicitly required NOT to be reset. */
  protected HashSet<String> _blacklistedTables = null;

  /** List of tables which are explicitly required to be reset. */
  protected HashSet<String> _tablesToReset = null;

  /**
   * Explicitly define tables to be reset. If a table is also
   * blacklisted, it will NOT be reset.
   * @param tables tables to reset
   * @return this
   */
  public MySQLAutoIncrementReset reset(String... tables)
  {
    if (tables != null && tables.length > 0)
    {
      if (_tablesToReset == null)
      {
        _tablesToReset = new HashSet<String>();
      }
      _tablesToReset.addAll(Arrays.asList(tables));
    }
    return this;
  }

  /**
   * Explicitly define tables NOT to be reset
   * @param tables list of tables NOT to reset.
   * @return this
   */
  public MySQLAutoIncrementReset skip(String... tables)
  {
    if (tables != null && tables.length > 0)
    {
      if (_blacklistedTables == null)
      {
        _blacklistedTables = new HashSet<String>();
      }
      _blacklistedTables.addAll(Arrays.asList(tables));
    }
    return this;
  }

  /**
   * Specify the Start index an AUTO_INCREMENT will get reset to.
   * @param startIndex start index (default 10000)
   * @return this
   */
  public MySQLAutoIncrementReset setStartIndex(int startIndex)
  {
    _startIndex = startIndex;
    return this;
  }

  /**
   * @return this the currently set start index
   */
  public int getStartIndex()
  {
    return _startIndex;
  }

  public void doCleanDatabase(DatabaseTesterBase<?> tester, IDataSet dataset)
      throws Exception
  {
    for (ITableIterator i = dataset.iterator(); i.next();)
    {
      String table = i.getTableMetaData().getTableName();
      if ((_blacklistedTables == null || !_blacklistedTables.contains(table))
          && (_tablesToReset == null || _tablesToReset.contains(table)))
      {
        resetTable(tester, table);
      }
    }
  }

  protected void resetTable(DatabaseTesterBase<?> tester, String table)
      throws Exception
  {
    tester.executeSQL("ALTER TABLE `" + table + "` AUTO_INCREMENT = "
        + _startIndex);
  }

  public void doPrepareDatabase(DatabaseTesterBase<?> tester, IDataSet dataset)
      throws Exception
  {
  }
}
