package com.seitenbau.testing.testdata;

import static org.junit.Assert.*;

import org.junit.Test;

public class PartitionIteratorTest
{

  @Test(expected = IllegalArgumentException.class)
  public void testPartitionIterator()
  {
    new PartitionIterator(null, null);
  }

}
