package com.seitenbau.testing.testdata;

import java.util.List;

public interface ParameterDescriptor
{

  String getName();

  Class<?> getType();

  void injectValue(Object target, Object value);

  List<?> getValidValues();

  List<?> getInvalidValues();

  boolean isOptional();
  
  boolean isPrimitiv();
  
  boolean isEmptyable();
  
  void setFilters(Filter ...filters);
  
  List<String> getMetadata(String metaType, Object value);
  
}
