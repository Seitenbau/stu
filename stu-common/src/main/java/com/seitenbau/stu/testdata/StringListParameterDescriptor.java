package com.seitenbau.stu.testdata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.seitenbau.stu.util.StringGenerator;

public class StringListParameterDescriptor extends AbstractParameterDescriptor
{

  StringListParameterDescriptor(Field field)
  {
    super(field);
  }

  public List<Object> getValidValues()
  {
    List<Object> result = new ArrayList<Object>();
    Optional optional = javaField.getAnnotation(Optional.class);
    if (optional != null)
    {
      result.add(new ArrayList<Object>());
    }
    ValidValues validValues = javaField.getAnnotation(ValidValues.class);
    if (validValues != null)
    {
      String[] values = validValues.value();
      result.addAll(permutations(values));
    }
    TextValue textValue = javaField.getAnnotation(TextValue.class);
    if (textValue != null)
    {
      int maxLength = textValue.maxLength();
      if (textValue.cardinality().equals(Cardinality.ZeroToMany))
      {
        String[] values = new String[] {StringGenerator.gen(maxLength),
            StringGenerator.gen(1), null, ""};
        result.addAll(permutations(values));
      }
      else
      {
        String[] values = new String[] {StringGenerator.gen(maxLength),
            StringGenerator.gen(1)};
        result.addAll(permutations(values));
      }
    }
    return result;
  }

  protected List<List<String>> permutations(String[] elements)
  {
    List<List<String>> permutations = new ArrayList<List<String>>();
    for (int i = 0; i < elements.length; i++)
    {
      permutations.add(new ArrayList<String>());
    }
    for (int i = 0; i < elements.length; i++)
    {
      for (int j = i; j < elements.length; j++)
      {
        permutations.get(j).add(elements[i]);
      }
    }
    return permutations;
  }

  public List<Object> getInvalidValues()
  {
    List<Object> result = new ArrayList<Object>();
    Optional optional = javaField.getAnnotation(Optional.class);
    if (optional == null)
    {
      result.add(new ArrayList<Object>());
    }
    if (hasInvalidValues())
    {
      InvalidValues invalidValues = javaField
          .getAnnotation(InvalidValues.class);
      ArrayList<String> attributes = new ArrayList<String>();
      String[] strValues = invalidValues.value();
      attributes.addAll(Arrays.asList(strValues));
      Value[] values = invalidValues.values();
      for (Value value : values)
      {
        String dataValue = value.value();
        attributes.add(dataValue);
      }
      result.addAll(permutations(attributes.toArray(new String[attributes
          .size()])));
    }
    TextValue textValue = javaField.getAnnotation(TextValue.class);
    if (textValue != null)
    {
      int maxLength = textValue.maxLength();
      if (textValue.cardinality().equals(Cardinality.OneToMany))
      {
        String[] values = new String[] {StringGenerator.gen(maxLength + 1),
            null, ""};
        result.addAll(permutations(values));
      }
      else
      {
        String[] values = new String[] {StringGenerator.gen(maxLength + 1)};
        result.addAll(permutations(values));
      }
    }
    return result;
  }

  @Override
  void doInjectValue(Object target, Object value) throws IllegalAccessException
  {
    javaField.set(target, value);
  }

  public boolean isPrimitiv()
  {
    return false;
  }

  public List<String> getMetadata(String metaType, Object value)
  {
    ArrayList<String> result = new ArrayList<String>();
    if (value instanceof List<?>)
    {
      List<?> valueList = (List<?>) value;
      if (hasInvalidValues())
      {
        if (getInvalidValues().contains(value))
        {
          InvalidValues invalidValues = javaField
              .getAnnotation(InvalidValues.class);
          Value[] values = invalidValues.values();
          for (Value annotationValue : values)
          {
            if (valueList.contains(annotationValue.value()))
            {
              MetaValue[] metadata = annotationValue.metadata();
              for (MetaValue metaValue : metadata)
              {
                if (metaValue.type().equals(metaType))
                {
                  result.add(metaValue.value());
                }
              }
            }
          }
        }
      }
    }
    return result;
  }

}
