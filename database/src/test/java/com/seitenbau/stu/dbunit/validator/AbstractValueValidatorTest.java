package com.seitenbau.stu.dbunit.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.seitenbau.stu.dbunit.modifier.IDataSetOverwriteCompare;
import com.seitenbau.stu.dbunit.validator.AbstractValueValidator;

public class AbstractValueValidatorTest
{

  @Test
  public void testAbstractValueValidator()
  {
    String MARKER = "AnyMarker";
    AbstractValueValidator sut = new AbstractValueValidator(MARKER)
    {
      public int compareDataSetElementTo(Object objectToCompareTo)
      {
        return 0;
      }
    };
    assertEquals(MARKER, sut.getMarkerString());
    assertSame(sut, sut.getReplacementObject());
    assertTrue(sut.getReplacementObject() instanceof IDataSetOverwriteCompare);
  }

}
