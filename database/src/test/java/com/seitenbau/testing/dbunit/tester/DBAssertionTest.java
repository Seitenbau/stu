package com.seitenbau.testing.dbunit.tester;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.seitenbau.testing.dbunit.tester.DBAssertion;

public class DBAssertionTest
{
  @Test
  public void testCreateSQLFields() throws Exception
  {
    String results = DBAssertion.createSQLFields("NAME", "TITLE");
    assertEquals(results, "NAME,TITLE");
    results = DBAssertion.createSQLFields(null);
    assertEquals(results, "*");
    results = DBAssertion.createSQLFields("NAME");
    assertEquals(results, "NAME");
  }

}
