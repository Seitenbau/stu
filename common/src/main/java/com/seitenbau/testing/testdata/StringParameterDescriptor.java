package com.seitenbau.testing.testdata;

import com.seitenbau.testing.util.StringGenerator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringParameterDescriptor extends AbstractParameterDescriptor
{

  StringParameterDescriptor(Field javaField)
  {
    super(javaField);
  }

  @Override
  public void doInjectValue(Object target, Object value)
      throws IllegalAccessException
  {
    javaField.set(target, value);
  }

  public List<Object> getValidValues()
  {
    List<Object> result = new ArrayList<Object>();
    ValidValues validValues = javaField.getAnnotation(ValidValues.class);
    if (validValues != null)
    {
      result.addAll(Arrays.asList(validValues.value()));
    }
    TextValue textValue = javaField.getAnnotation(TextValue.class);
    if (textValue != null)
    {
      int maxLength = textValue.maxLength();
      String text = StringGenerator.gen(maxLength);
      result.add(text);
    }
    return result;
  }

  public List<Object> getInvalidValues()
  {
    List<Object> result = new ArrayList<Object>();
    InvalidValues invalidValues = javaField.getAnnotation(InvalidValues.class);
    if (invalidValues != null)
    {
      result.addAll(Arrays.asList(invalidValues.value()));
      Value[] values = invalidValues.values();
      for (Value value : values)
      {
        String dataValue = value.value();
        result.add(dataValue);
      }
    }
    TextValue textValue = javaField.getAnnotation(TextValue.class);
    if (textValue != null)
    {
      int maxLength = textValue.maxLength();
      String text = StringGenerator.gen(maxLength + 1);
      result.add(text);
    }
    return result;
  }

  public boolean isPrimitiv()
  {
    return false;
  }

}
