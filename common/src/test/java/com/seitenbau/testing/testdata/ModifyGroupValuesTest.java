package com.seitenbau.testing.testdata;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ModifyGroupValuesTest
{

  ModifyGroupValues modifyGroupValues;
  
  static class TestData
  {
  }
  
  @Before
  public void setup()
  {
    modifyGroupValues = new ModifyGroupValues(TestData.class)
    {
      @Override
      void invokeModifier()
      {
      }
    };
  }
  
  @Test(expected = RuntimeException.class)
  public void testCreateModifierInstance()
  {
    ModifyGroupValues.createModifierInstance(null);
  }
  
  @Test(expected = FieldReadException.class)
  public void testParamName() throws Exception
  {
    modifyGroupValues.paramName("foo");
  }

}
