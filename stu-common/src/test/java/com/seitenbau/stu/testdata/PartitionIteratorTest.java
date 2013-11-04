package com.seitenbau.stu.testdata;

import org.junit.Test;

import com.seitenbau.stu.testdata.PartitionIterator;

public class PartitionIteratorTest
{

  @Test(expected = IllegalArgumentException.class)
  public void testPartitionIterator()
  {
    new PartitionIterator(null, null);
  }

}
