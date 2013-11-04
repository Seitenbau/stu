package com.seitenbau.stu.database.rule;

import org.dbunit.operation.DatabaseOperation;

import com.seitenbau.stu.database.extend.DatabaseOperationFactory;

public class NoneFactory implements DatabaseOperationFactory
{

  public DatabaseOperation truncateOperation()
  {
    return DatabaseOperation.NONE;
  }

  public DatabaseOperation deleteOperation()
  {
    return DatabaseOperation.NONE;
  }

  public DatabaseOperation insertOperation()
  {
    return DatabaseOperation.NONE;
  }

  public DatabaseOperation cleanInsertOperation()
  {
    return DatabaseOperation.NONE;
  }

}
