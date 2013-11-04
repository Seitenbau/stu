package com.seitenbau.stu.testdata;

import static org.junit.Assert.*;

import org.junit.Test;

import com.seitenbau.stu.testdata.NullPartitionValue;

public class NullPartitionValueTest
{

  NullPartitionValue nullPartitionValue = new NullPartitionValue();
  
  @Test
  public void testInjectValue()
  {
    nullPartitionValue.injectValue(null);
  }
  
  @Test
  public void testGetValue()
  {
    assertEquals(null, nullPartitionValue.getValue());
  }
  
  @Test
  public void testGetPartition()
  {
    assertEquals(null, nullPartitionValue.getPartition());
  }

}
