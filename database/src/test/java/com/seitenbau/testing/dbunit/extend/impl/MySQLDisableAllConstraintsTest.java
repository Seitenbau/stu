package com.seitenbau.testing.dbunit.extend.impl;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import com.seitenbau.testing.mockito.MockitoRule;

public class MySQLDisableAllConstraintsTest
{
  @Rule
  public MockitoRule mockito = new MockitoRule();

  @Mock
  Connection mock;

  @Mock
  PreparedStatement preparedStatementMock;

  MySQLDisableAllConstraints sut = new MySQLDisableAllConstraints()
  {
    protected java.sql.Connection getConnection(com.seitenbau.testing.dbunit.tester.DatabaseTesterBase<?> tester)
        throws java.sql.SQLException, ClassNotFoundException, org.dbunit.DatabaseUnitException
    {
      return mock;
    };
  };

  @Test
  public void disableChecks() throws Exception
  {
    // prepare
    when(mock.prepareStatement("set foreign_key_checks=?")).thenReturn(preparedStatementMock);

    // execute
    sut.doCleanDatabase(null, null);

    // verify
    verify(preparedStatementMock).setInt(1, 0);
    verify(preparedStatementMock).execute();
    verify(preparedStatementMock).close();
    verifyNoMoreInteractions(preparedStatementMock);
  }

  @Test
  public void enableChecks() throws Exception
  {
    // prepare
    when(mock.prepareStatement("set foreign_key_checks=?")).thenReturn(preparedStatementMock);

    // execute
    sut.doPrepareDatabase(null, null);

    // verify
    verify(preparedStatementMock).setInt(1, 1);
    verify(preparedStatementMock).execute();
    verify(preparedStatementMock).close();
    verifyNoMoreInteractions(preparedStatementMock);
  }
}
