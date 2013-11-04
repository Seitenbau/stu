package com.seitenbau.stu.testdata;

import java.util.Collections;
import java.util.List;

public class NullParameterDescriptor implements ParameterDescriptor
{

  static final String NAME = "IGNORE";

  public String getName()
  {
    return NAME;
  }

  public Class<?> getType()
  {
    return NullParameterDescriptor.class;
  }

  public void injectValue(Object target, Object value)
  {
  }

  public List<Object> getValidValues()
  {
    return Collections.emptyList();
  }

  public List<Object> getInvalidValues()
  {
    return Collections.emptyList();
  }

  public boolean isOptional()
  {
    return true;
  }

  public boolean isPrimitiv()
  {
    return false;
  }

  public void setFilters(Filter... filters)
  {
  }

  public List<String> getMetadata(String metaType, Object value)
  {
    return Collections.emptyList();
  }

  public boolean isEmptyable()
  {
    return false;
  }

}
