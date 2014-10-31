package com.seitenbau.stu.testdata;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardinalityTest
{

  @Test
  public void testCardinality()
  {
    assertEquals(Cardinality.OneToMany, Cardinality.valueOf("OneToMany"));
  }

}
