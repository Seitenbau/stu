package com.seitenbau.stu.testdata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegerParameterDescriptor extends AbstractParameterDescriptor
{

  IntegerParameterDescriptor(Field field)
  {
    super(field);
  }

  @Override
  void doInjectValue(Object target, Object value) throws IllegalAccessException
  {
    int intValue = Integer.class.cast(value).intValue();
    javaField.setInt(target, intValue);
  }

  public List<Object> getValidValues()
  {
    ValidValues validValues = javaField.getAnnotation(ValidValues.class);
    if (validValues != null)
    {
      String[] strValues = validValues.value();
      return Arrays.asList(valueOfStringArray(strValues));
    }
    else
    {
      return Arrays.asList(new Object[] {Integer.MIN_VALUE, 0,
          Integer.MAX_VALUE});
    }
  }

  public List<Object> getInvalidValues()
  {
    InvalidValues invalidValues = javaField.getAnnotation(InvalidValues.class);
    if (invalidValues != null)
    {
      ArrayList<Object> result = new ArrayList<Object>();
      String[] strValues = invalidValues.value();
      Value[] values = invalidValues.values();
      for (Value value : values)
      {
        Integer dataValue = Integer.valueOf(value.value());
        result.add(dataValue);
      }
      result.addAll(Arrays.asList(valueOfStringArray(strValues)));
      return result;
    }
    else
    {
      return Arrays.asList(new Object[] {});
    }
  }

  private Object[] valueOfStringArray(String[] strValues)
  {
    Integer[] intValues = new Integer[strValues.length];
    for (int i = 0; i < intValues.length; i++)
    {
      intValues[i] = Integer.valueOf(strValues[i]);
    }
    return intValues;
  }

  public boolean isPrimitiv()
  {
    return true;
  }
  
  @Override
  public List<String> getMetadata(String metaType, Object value)
  {
    return super.getMetadata(metaType, value);
  }

}
