package com.seitenbau.stu.database.extend.impl;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.fest.assertions.Fail;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import com.seitenbau.stu.database.extend.impl.OracleSequenceFastReset;
import com.seitenbau.stu.database.tester.DatabaseTesterBase;
import com.seitenbau.stu.mockito.MockitoRule;

public class OracleSequenceFastResetTest
{
  @Rule
  public MockitoRule mockito = new MockitoRule();

  @Mock
  DatabaseTesterBase<?> tester;

  @Test
  public void sucess() throws Exception
  {
    // prepare
    OracleSequenceFastReset sut = new OracleSequenceFastReset();
    when(tester.executeSQLandReturnInt("Select sequenceName.nextval from dual")).thenReturn(12);

    // execute
    sut.clearSequence(tester, "sequenceName");

    // verify
    verify(tester).executeSQL("Alter sequence  sequenceName increment by 9988");
    verify(tester).executeSQL("Alter sequence  sequenceName increment by 1");
    verify(tester, times(2)).executeSQLandReturnInt("Select sequenceName.nextval from dual");
    verify(tester).executeSQL("Alter sequence  sequenceName increment by 1");
    verifyNoMoreInteractions(tester);
  }

  @Test
  public void readSequenceError() throws Exception
  {
    // prepare
    OracleSequenceFastReset sut = new OracleSequenceFastReset();
    when(tester.executeSQLandReturnInt("Select sequenceName.nextval from dual")).thenThrow(new Exception());

    // execute
    try
    {
      sut.clearSequence(tester, "sequenceName");
      Fail.fail();
    }
    catch (Exception e)
    {
      assertThat(e).isInstanceOf(RuntimeException.class).hasMessage(
          "While modifying Sequence : sequenceName an error occured : java.lang.Exception");
      verify(tester).executeSQLandReturnInt("Select sequenceName.nextval from dual");
      verifyNoMoreInteractions(tester);
    }
  }

  @Test
  public void readError() throws Exception
  {
    // prepare
    OracleSequenceFastReset sut = new OracleSequenceFastReset();
    when(tester.executeSQLandReturnInt("Select sequenceName.nextval from dual")).thenReturn(100000);
    when(tester.executeSQL("Alter sequence  sequenceName increment by 1")).thenThrow(new Exception());

    // execute
    try
    {
      sut.clearSequence(tester, "sequenceName");
      Fail.fail();
    }
    catch (Exception e)
    {
      assertThat(e).isInstanceOf(RuntimeException.class).hasMessage(
          "While modifying Sequence : sequenceName an error occured : java.lang.Exception");

      // verify
      verify(tester).executeSQL("Alter sequence  sequenceName increment by -90000");

      verify(tester, times(2)).executeSQL("Alter sequence  sequenceName increment by 1");
      verify(tester, times(2)).executeSQLandReturnInt("Select sequenceName.nextval from dual");
      verifyNoMoreInteractions(tester);
    }
  }
}
