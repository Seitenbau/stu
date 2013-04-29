package com.seitenbau.testing.testdata;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class BooleanParameterDescriptor extends AbstractParameterDescriptor
{

  BooleanParameterDescriptor(Field field)
  {
    super(field);
  }

  @Override
  void doInjectValue(Object target, Object value) throws IllegalAccessException
  {
    boolean booleanValue = ((Boolean) value).booleanValue();
    javaField.setBoolean(target, booleanValue);
  }

  public List<Object> getValidValues()
  {
    return Arrays.asList(new Object[] {true, false});
  }

  public List<Object> getInvalidValues()
  {
    return Arrays.asList(new Object[] {});
  }

  public boolean isPrimitiv()
  {
    return true;
  }

}
