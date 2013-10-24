package com.seitenbau.stu.data.impl;

import com.seitenbau.stu.data.PropertySpecification;

/**
 * Helper to associate a Name to a Property Specification.
 */
public class NameAndSpecGlue<T>
{
  private String _name;

  public String getName()
  {
    return _name;
  }

  public PropertySpecification<T> getSpec()
  {
    return _spec;
  }

  private PropertySpecification<T> _spec;

  private Enum<?> _marker;

  public Enum<?> getMarker()
  {
    return _marker;
  }

  public NameAndSpecGlue(String name, PropertySpecification<T> propertyDescription)
  {
    _name = name;
    _spec = propertyDescription;
  }

  public <E extends Enum<E>> NameAndSpecGlue(String name, E marker,
      PropertySpecification<T> propertyDescription)
  {
    _name = name;
    _spec = propertyDescription;
    _marker = marker;
  }

}
