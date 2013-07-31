package com.seitenbau.testing.dbunit.extend.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.dbunit.extend.DatabaseTesterCleanAction;
import com.seitenbau.testing.dbunit.tester.DatabaseTesterBase;

/**
 * 
 * Helper action to dis- and enable the foreign key checks before
 * and after making changes to the database
 * 
 */
public class MySQLDisableAllConstraints implements DatabaseTesterCleanAction
{
  PreparedStatement setForeignKeyChecks = null;

  /** query string for prepared statement */
  final String setFKChecksString = "set foreign_key_checks=?";

  final int disable = 0;

  final int enable = 1;

  public void doCleanDatabase(DatabaseTesterBase<?> tester, IDataSet dataset) throws Exception
  {
    executeSetForeignKeyChecksStatement(tester, disable);
  }

  public void doPrepareDatabase(DatabaseTesterBase<?> tester, IDataSet dataset) throws Exception
  {
    executeSetForeignKeyChecksStatement(tester, enable);
  }

  /**
   * 
   * @param tester DatabaseTester to execute statement on
   * @param value prepared statement parameter value
   * @throws SQLException
   * @throws ClassNotFoundException
   * @throws DatabaseUnitException
   */
  protected void executeSetForeignKeyChecksStatement(DatabaseTesterBase<?> tester, int value) throws SQLException,
      ClassNotFoundException, DatabaseUnitException
  {
    Connection connection = getConnection(tester);
    connection.setAutoCommit(false);
    try
    {
      setForeignKeyChecks = connection.prepareStatement(setFKChecksString);
      setForeignKeyChecks.setInt(1, value);
      try
      {
        setForeignKeyChecks.execute();
      }
      finally
      {
        if (setForeignKeyChecks != null)
        {
          setForeignKeyChecks.close();
        }
      }
    }
    finally
    {
      connection.commit();
      connection.setAutoCommit(true);
    }
  }

  protected Connection getConnection(DatabaseTesterBase<?> tester) throws SQLException, ClassNotFoundException,
      DatabaseUnitException
  {
    return tester.getConnection().getConnection();
  }
}
