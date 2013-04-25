package com.seitenbau.testing.dbunit.extend.impl;


/**
 * Helper Action to reset Oralce Database Sequences.
 * 
 * Either specify seqences by hand . {@code #sequenceName(String...)}
 * or automatically create Sequence names by adding a postfix to all
 * tables in your dataset {@code #autoDerivateFromTablename(String)}.
 */
public class OracleSequenceReset extends SimpleDatabaseSequenceReset<OracleSequenceReset>
{
  @Override
  protected String getSqlCreateSequence(String sequenceName, Integer startIndex)
  {
    return "CREATE SEQUENCE " + sequenceName + " START WITH " + startIndex;
  }

  @Override
  protected String getSqlDropSequence(String sequenceName)
  {
    return "DROP SEQUENCE " + sequenceName;
  }
}
