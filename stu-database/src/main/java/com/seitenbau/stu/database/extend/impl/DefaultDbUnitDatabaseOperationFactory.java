package com.seitenbau.stu.database.extend.impl;

import org.dbunit.operation.DatabaseOperation;

import com.seitenbau.stu.database.extend.DatabaseOperationFactory;

/**
 * Factory which just creates the default DBUnit methods.
 */
public class DefaultDbUnitDatabaseOperationFactory implements DatabaseOperationFactory
{
  /** {@inheritDoc} */
  public DatabaseOperation truncateOperation()
  {
    return DatabaseOperation.TRUNCATE_TABLE;
  }

  /** {@inheritDoc} */
  public DatabaseOperation deleteOperation()
  {
    return DatabaseOperation.DELETE_ALL;
  }

  /** {@inheritDoc} */
  public DatabaseOperation insertOperation()
  {
    return DatabaseOperation.INSERT;
  }

  /** {@inheritDoc} */
  public DatabaseOperation cleanInsertOperation()
  {
    return DatabaseOperation.CLEAN_INSERT;
  }

}
