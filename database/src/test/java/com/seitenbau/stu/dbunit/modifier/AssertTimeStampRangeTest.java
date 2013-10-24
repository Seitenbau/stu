package com.seitenbau.stu.dbunit.modifier;

import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.seitenbau.stu.dbunit.modifier.ReplacerTimeStampUnsharpCompare;

public class AssertTimeStampRangeTest
{

  @Before
  public void setUp() throws Exception
  {
  }

  /**
   * Test the compareDataSetElementTo Method
   */
  @Test
  public void testCompareDataSetElementTo()
  {
    Date now = new Date();
    long nowTicks = now.getTime();

    ReplacerTimeStampUnsharpCompare testInstance = new ReplacerTimeStampUnsharpCompare("", now, 5);

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
