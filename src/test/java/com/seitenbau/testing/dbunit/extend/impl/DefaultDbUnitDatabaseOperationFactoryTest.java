package com.seitenbau.testing.dbunit.extend.impl;

import static org.fest.assertions.Assertions.*;

import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

public class DefaultDbUnitDatabaseOperationFactoryTest
{

  @Test
  public void testTruncateOperation()
      throws Exception
  {
    assertThat(new DefaultDbUnitDatabaseOperationFactory().truncateOperation())
        .isSameAs(DatabaseOperation.TRUNCATE_TABLE);
  }

  @Test
  public void testDeleteOperation()
      throws Exception
  {
    assertThat(new DefaultDbUnitDatabaseOperationFactory().deleteOperation())
        .isSameAs(DatabaseOperation.DELETE_ALL);
  }

  @Test
  public void testInsertOperation()
      throws Exception
  {
    assertThat(new DefaultDbUnitDatabaseOperationFactory().insertOperation())
        .isSameAs(DatabaseOperation.INSERT);
  }

  @Test
  public void testCleanInsertOperation()
      throws Exception
  {
    assertThat(new DefaultDbUnitDatabaseOperationFactory().cleanInsertOperation())
        .isSameAs(DatabaseOperation.CLEAN_INSERT);
  }

}
