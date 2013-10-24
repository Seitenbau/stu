package com.seitenbau.stu.dbunit.validator;

import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.seitenbau.stu.dbunit.validator.ValueDateEquals;

public class ValueDateEqualsTest
{

  @Before
  public void setUp() throws Exception
  {
  }

  /**
   * Test the compareDataSetElementTo Method.
   */
  @Test
  public void testCompareDataSetElementTo()
  {
    ValueDateEquals testInstance = new ValueDateEquals("");
    Date now = (Date) testInstance.getReplacementObject();
    long nowTicks = now.getTime();

    // 1a
    testInstance.compareDataSetElementTo(new Date(nowTicks));
    testInstance.compareDataSetElementTo(new Date(nowTicks - 5));
    testInstance.compareDataSetElementTo(new Date(nowTicks + 5));

    // over 1ms
    try
    {
      testInstance.compareDataSetElementTo(new Date(nowTicks - 6));
      fail();
    }
    catch (AssertionError e)
    {
    }
    try
    {
      testInstance.compareDataSetElementTo(new Date(nowTicks + 6));
      fail();
    }
    catch (AssertionError e)
    {
    }
  }
}
