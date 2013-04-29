package com.seitenbau.testing.data;

import java.util.ArrayList;
import java.util.List;

import com.seitenbau.testing.data.impl.NameAndSpecGlue;

public class Specs
{

  private List<NameAndSpecGlue<?>> propertySpecifications = new ArrayList<NameAndSpecGlue<?>>();

  private Class<?> _targetType;

  public Class<?> getTargetType()
  {
    return _targetType;
  }

  public Specs(Class<?> target)
  {
    _targetType = target;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public PropertySpecification<?> property(PropertyName enumElement,
      PropertySpecification<?> propertyDescription)
  {
    if (enumElement.getClass().isEnum())
    {
      propertySpecifications.add(new NameAndSpecGlue(enumElement.getPropertyName(),
          (Enum) enumElement,
          propertyDescription));
    }
    else
    {
      propertySpecifications.add(new NameAndSpecGlue(enumElement.getPropertyName(),
          propertyDescription));
    }
    return propertyDescription;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public PropertySpecification<?> property(String name, PropertySpecification<?> propertyDescription)
  {
    propertySpecifications.add(new NameAndSpecGlue(name, propertyDescription));
    return propertyDescription;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public <E extends Enum<E>> PropertySpecification<?> property(String name, E marker,
      PropertySpecification<?> propertyDescription)
  {
    propertySpecifications.add(new NameAndSpecGlue(name, marker, propertyDescription));
    return propertyDescription;
  }

  public List<NameAndSpecGlue<?>> getAllPropertySpecifications()
  {
    return propertySpecifications;
  }

}
