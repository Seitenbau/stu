package com.seitenbau.testing.testdata;

import java.lang.reflect.Field;
import java.util.List;

public class ObjectParameterDescriptor extends AbstractParameterDescriptor
{

  ObjectParameterDescriptor(Field field)
  {
    super(field);
  }

  public List<?> getValidValues()
  {
    Class<?> type = javaField.getType();
    TestParameterGenerator generator = new TestParameterGenerator();
    List<?> result = generator.getValidParameters(type, filters);
    return result;
  }

  public List<?> getInvalidValues()
  {
    Class<?> type = javaField.getType();
    TestParameterGenerator generator = new TestParameterGenerator();
    List<?> result = generator.getInvalidParameters(type, filters);
    return result;
  }

  public boolean isPrimitiv()
  {
    return false;
  }

  @Override
  void doInjectValue(Object target, Object value) throws IllegalAccessException
  {
    javaField.set(target, value);
  }

}
