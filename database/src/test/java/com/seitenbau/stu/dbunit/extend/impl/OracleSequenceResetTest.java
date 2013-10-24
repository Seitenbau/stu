package com.seitenbau.stu.dbunit.extend.impl;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.seitenbau.stu.dbunit.extend.impl.OracleSequenceReset;

public class OracleSequenceResetTest
{
  @Test
  public void checkCreateCommand()
  {
    // prepare
    OracleSequenceReset sut = new OracleSequenceReset();
    // execute
    String sql = sut.getSqlCreateSequence("name_seq", 123);
    // verify
    assertThat(sql).isEqualTo("CREATE SEQUENCE name_seq START WITH 123");
  }

  @Test
  public void checkDropCommand()
  {
    // prepare
    OracleSequenceReset sut = new OracleSequenceReset();
    // execute
    String sql = sut.getSqlDropSequence("name_seq");
    // verify
    assertThat(sql).isEqualTo("DROP SEQUENCE name_seq");
  }
}
