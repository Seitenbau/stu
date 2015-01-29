package com.seitenbau.stu.database.extend.impl;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.junit.Before;
import org.junit.Test;

import com.seitenbau.stu.database.extend.impl.MySQLAutoIncrementReset;
import com.seitenbau.stu.database.tester.DatabaseTesterBase;

public class MySQLAutoIncrementResetTest
{
  private static final String TABLE1 = "TaBLe1";

  private static final String TABLE2 = "TaBLe2";

  private List<String> _tables;

  private MySQLAutoIncrementReset sut;

  private IDataSet _dataSet;

  @Before
  public void setUp() throws AmbiguousTableNameException
  {
    _tables = new ArrayList<String>();

    DefaultDataSet ds = new DefaultDataSet();
    ds.addTable(new DefaultTable(TABLE1));
    ds.addTable(new DefaultTable(TABLE2));
    _dataSet = ds;

    sut = new MySQLAutoIncrementReset()
    {
      @Override
      protected void resetTable(DatabaseTesterBase<?> tester, String table)
      {
        _tables.add(table);
      }
    };
  }

  @Test
  public void testEmptyDatabase() throws Exception
  {
    // execute
    sut.doPrepareDatabase(null, new DefaultDataSet());
    // verify
    assertThat(_tables).isEmpty();
  }

  @Test
  public void testDatabaseAllTables() throws Exception
  {
    // execute
    sut.doPrepareDatabase(null, _dataSet);

    // verify
    assertThat(_tables).containsExactly(TABLE1, TABLE2);
  }

  @Test
  public void testDatabaseFixedTables() throws Exception
  {
    // prepare
    sut.reset(TABLE1);

    // execute
    sut.doPrepareDatabase(null, _dataSet);

    // verify
    assertThat(_tables).containsExactly(TABLE1);
  }

  @Test
  public void testDatabaseIgnoredTables() throws Exception
  {
    // prepare
    sut.skip(TABLE1);

    // execute
    sut.doPrepareDatabase(null, _dataSet);

    // verify
    assertThat(_tables).containsExactly(TABLE2);
  }

  @Test
  public void testDatabaseConflict() throws Exception
  {
    // prepare
    sut.skip(TABLE1);
    sut.reset(TABLE1, TABLE2);

    // execute
    sut.doPrepareDatabase(null, _dataSet);

    // verify
    assertThat(_tables).containsExactly(TABLE2);
  }
}
