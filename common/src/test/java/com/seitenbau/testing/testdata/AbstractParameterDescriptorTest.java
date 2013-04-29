package com.seitenbau.testing.testdata;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

public class AbstractParameterDescriptorTest
{
 
  public String field;
  
  @Test(expected=CantInjectValueException.class)
  public void testInjectValue_MapIllegalAccessToCantInjectValueException() throws Exception
  {
    Field javaFiled = getClass().getField("field");
    AbstractParameterDescriptor parameterDescriptor = new AbstractParameterDescriptor(javaFiled)
    {
      
      public List<Object> getValidValues()
      {
        return null;
      }
      
      public List<Object> getInvalidValues()
      {
        return null;
      }
      
      @Override
      void doInjectValue(Object target, Object value) throws IllegalAccessException
      {
        throw new IllegalAccessException();
      }

      public boolean isPrimitiv()
      {
        return false;
      }

    };
    parameterDescriptor.injectValue(null, null);
  }

}
