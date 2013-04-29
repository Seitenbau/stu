package com.seitenbau.testing.testdata;

import static org.junit.Assert.*;

import org.junit.Test;

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
