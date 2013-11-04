package com.seitenbau.stu.database.extend.impl;

import com.seitenbau.stu.database.tester.DatabaseTesterBase;

public abstract class SimpleDatabaseSequenceReset<THAT> extends DatabaseSequenceReset<THAT>
{
  protected void clearSequence(DatabaseTesterBase<?> tester, String sequenceName) throws Exception
  {
    try
    {
      tester.executeSQL(getSqlDropSequence(sequenceName));
    }
    catch (Exception e)
    {
      if (_stopOnDropSequenceException)
      {
        throw e;
      }
    }
    tester.executeSQL(getSqlCreateSequence(sequenceName, _startIndex));
    logAction("Did reset sequence : '" + sequenceName + "' to: " + _startIndex);
  }

  abstract protected String getSqlCreateSequence(String sequenceName, Integer startIndex);

  abstract protected String getSqlDropSequence(String sequenceName);
}
