package com.seitenbau.testing.testdata;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public abstract class AbstractParameterDescriptor implements
    ParameterDescriptor
{

  protected final Field javaField;

  protected Filter[] filters;

  public AbstractParameterDescriptor(Field field)
  {
    this.javaField = field;
  }

  public String getName()
  {
    return javaField.getName();
  }

  public Class<?> getType()
  {
    return javaField.getType();
  }

  public boolean isOptional()
  {
    Optional optional = javaField.getAnnotation(Optional.class);
    if (optional != null)
    {
      return true;
    }
    return false;
  }

  public boolean isEmptyable()
  {
    NotEmpty notEmpty = javaField.getAnnotation(NotEmpty.class);
    return notEmpty == null;
  }

  public void injectValue(Object target, Object value)
  {
    try
    {
      this.javaField.setAccessible(true);
      doInjectValue(target, value);
    }
    catch (IllegalAccessException e)
    {
      throw new CantInjectValueException(getName());
    }
  }

  public void setFilters(Filter... filters)
  {
    this.filters = filters;
  }

  public List<String> getMetadata(String metaType, Object value)
  {
    if (hasInvalidValues())
    {
      if (getInvalidValues().contains(value))
      {

        InvalidValues invalidValues = javaField
            .getAnnotation(InvalidValues.class);
        Value[] values = invalidValues.values();
        for (Value annotationValue : values)
        {
          if (annotationValue.value().equals(value.toString()))
          {
            MetaValue[] metadata = annotationValue.metadata();
            for (MetaValue metaValue : metadata)
            {
              if (metaValue.type().equals(metaType))
              {
                return Collections.singletonList(metaValue.value());
              }
            }
          }
        }
      }
    }
    return Collections.emptyList();
  }

  abstract void doInjectValue(Object target, Object value)
      throws IllegalAccessException;

  boolean hasInvalidValues()
  {
    InvalidValues annotation = javaField.getAnnotation(InvalidValues.class);
    return annotation != null;
  }
}
