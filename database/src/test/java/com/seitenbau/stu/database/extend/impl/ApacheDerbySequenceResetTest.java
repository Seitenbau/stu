package com.seitenbau.stu.database.extend.impl;

import org.junit.Test;

import com.seitenbau.stu.database.extend.impl.ApacheDerbySequenceReset;

import static com.seitenbau.stu.asserts.fest.Assertions.*;

public class ApacheDerbySequenceResetTest
{
  @Test
  public void checkCreateCommand()
  {
    // prepare
    ApacheDerbySequenceReset sut = new ApacheDerbySequenceReset();
    // execute
    String sql = sut.getSqlCreateSequence("name_seq", 123);
    // verify
    assertThat(sql).isEqualTo("CREATE SEQUENCE name_seq START WITH 123");
  }

  @Test
  public void checkDropCommand()
  {
    // prepare
    ApacheDerbySequenceReset sut = new ApacheDerbySequenceReset();
    // execute
    String sql = sut.getSqlDropSequence("name_seq");
    // verify
    assertThat(sql).isEqualTo("DROP SEQUENCE name_seq RESTRICT");
  }
}
