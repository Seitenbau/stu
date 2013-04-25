package com.seitenbau.testing.dbunit.extend.impl;

import static org.fest.assertions.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.junit.Test;

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
