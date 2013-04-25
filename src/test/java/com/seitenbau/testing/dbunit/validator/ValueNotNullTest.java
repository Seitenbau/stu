package com.seitenbau.testing.dbunit.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.seitenbau.testing.dbunit.modifier.IDataSetOverwriteCompare;

public class ValueNotNullTest
{
  /**
   * Test constructor
   * @throws Exception
   */
  @Test
  public void testConstructor() throws Exception
  {
    ValueNotNull sut = new ValueNotNull("myMarkerId");
    assertEquals("myMarkerId", sut.getMarkerString());
    assertTrue(sut.getReplacementObject() instanceof IDataSetOverwriteCompare);
  }

  /**
   * test compareNotNullValues method
   * 
   * @throws Exception
   */
  @Test
  public void testCompareNotNullValues() throws Exception
  {
    IDataSetOverwriteCompare compare = createNewSutAndGetCompare();

    // Test, none of this should cause a Assertion
    compare.compareDataSetElementTo("Anything");
    compare.compareDataSetElementTo(123);
  }

  @Test(expected = AssertionError.class)
  public void testCompareNull() throws Exception
  {
    IDataSetOverwriteCompare compare = createNewSutAndGetCompare();

    compare.compareDataSetElementTo(null);
  }

  private IDataSetOverwriteCompare createNewSutAndGetCompare()
  {
    // Prepare
    ValueNotNull sut = new ValueNotNull("myMarkerId");
    assertTrue(sut.getReplacementObject() instanceof IDataSetOverwriteCompare);

    // Get Compare Instance
    IDataSetOverwriteCompare compare = (IDataSetOverwriteCompare) sut
        .getReplacementObject();
    return compare;
  }

}
