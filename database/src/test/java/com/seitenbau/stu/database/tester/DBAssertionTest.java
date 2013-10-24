package com.seitenbau.stu.database.tester;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.seitenbau.stu.database.tester.DBAssertion;

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
