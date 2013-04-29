package com.seitenbau.testing.dbunit.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.seitenbau.testing.dbunit.modifier.IDataSetOverwriteCompare;

public class ValueNotEqualsTest
{

  private Object equalObject;

  /**
   * Test of constructor
   * 
   * @throws Exception
   */
  @Test
  public void testConstructor() throws Exception
  {
    Object equalObject = new Object();
    ValueNotEquals sut = new ValueNotEquals("myMarkerId", equalObject);
    assertEquals("myMarkerId", sut.getMarkerString());
    assertTrue(sut.getReplacementObject() instanceof IDataSetOverwriteCompare);
  }

  /**
   * Test compareOtherString method
   * @throws Exception
   */
  @Test
  public void testCompareOtherString() throws Exception
  {
    IDataSetOverwriteCompare compare = createNewSutAndGetCompare();

    // Test, none of this should cause a Assertion
    compare.compareDataSetElementTo("Anything");
    compare.compareDataSetElementTo(123);
    compare.compareDataSetElementTo(null);
  }

  @Test(expected = AssertionError.class)
  public void testCompareOtherType() throws Exception
  {
    IDataSetOverwriteCompare compare = createNewSutAndGetCompare();

    compare.compareDataSetElementTo(equalObject);
  }

  private IDataSetOverwriteCompare createNewSutAndGetCompare()
  {
    // Prepare
    equalObject = new Object();
    ValueNotEquals sut = new ValueNotEquals("myMarkerId", equalObject);
    assertTrue(sut.getReplacementObject() instanceof IDataSetOverwriteCompare);

    // Get Compare Instance
    IDataSetOverwriteCompare compare = (IDataSetOverwriteCompare) sut.getReplacementObject();
    return compare;
  }
}
