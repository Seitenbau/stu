package com.seitenbau.stu.database.extend.impl;

/**
 * Helper Action to reset Apache Derby Database Sequences.
 * 
 * Either specify sequences by hand . {@code #sequenceName(String...)}
 * or automatically create Sequence names by adding a postfix to all
 * tables in your dataset {@code #autoDerivateFromTablename(String)}.
 */
public class ApacheDerbySequenceReset extends SimpleDatabaseSequenceReset<ApacheDerbySequenceReset>
{

  @Override
  protected String getSqlCreateSequence(String sequenceName, Integer startIndex)
  {
    return "CREATE SEQUENCE " + sequenceName + " START WITH " + startIndex;
  }

  @Override
  protected String getSqlDropSequence(String sequenceName)
  {
    return "DROP SEQUENCE " + sequenceName + " RESTRICT";
  }

}
