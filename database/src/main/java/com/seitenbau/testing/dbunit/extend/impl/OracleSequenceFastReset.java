package com.seitenbau.testing.dbunit.extend.impl;

import com.seitenbau.testing.dbunit.tester.DatabaseTesterBase;

/**
 * Based on an idea by CB: Instead of dropping and recreating the
 * sequence, the 'reset' is achieved by a negative increment. <b>The
 * sequence MUST exist!</b> <br/>
 * (Not sure if this is really any faster at all ... guess not)
 */
public class OracleSequenceFastReset extends DatabaseSequenceReset<OracleSequenceFastReset>
{
  protected void clearSequence(DatabaseTesterBase<?> tester, String sequenceName) throws Exception
  {
    boolean modified = false;
    try
    {
      int count = tester.executeSQLandReturnInt("Select " + sequenceName + ".nextval from dual");
      int diff = _startIndex - count;
      if (diff != 0)
      {
        modified = true;
        tester.executeSQL("Alter sequence  " + sequenceName + " increment by " + diff);
        tester.executeSQLandReturnInt("Select " + sequenceName + ".nextval from dual");
        tester.executeSQL("Alter sequence  " + sequenceName + " increment by 1");
        logAction("Did reset sequence : '" + sequenceName + "' to " + _startIndex);
      }
    }
    catch (Exception e)
    {
      try
      {
        if (modified)
        { // try a reset
          tester.executeSQL("Alter sequence  " + sequenceName + " increment by 1");
        }
      }
      catch (Exception ups)
      {
        // supress
      }
      throw new RuntimeException("While modifying Sequence : " + sequenceName + " an error occured : " + e.toString(),
          e);
    }
  }

}
