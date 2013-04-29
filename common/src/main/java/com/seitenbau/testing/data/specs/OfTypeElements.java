package com.seitenbau.testing.data.specs;

import java.util.ArrayList;
import java.util.List;

import com.seitenbau.testing.data.PropertySpecification;
import com.seitenbau.testing.data.detail.Representant;
import com.seitenbau.testing.data.detail.Representant.RepresentantType;
import com.seitenbau.testing.data.detail.impl.RepresentantImpl;

public class OfTypeElements<T> implements PropertySpecification<T>
{
  List<Representant<T>> _validValues = new ArrayList<Representant<T>>();

  List<Representant<T>> _inValidValues = new ArrayList<Representant<T>>();

  public OfTypeElements<T> valid(T... value)
  {
    for (T val : value)
    {
      _validValues.add(new RepresentantImpl<T>(RepresentantType.VALID, val));
    }
    return this;
  }

  public OfTypeElements<T> inValid(T... value)
  {
    for (T val : value)
    {
      _inValidValues.add(new RepresentantImpl<T>(RepresentantType.INVALID, val));
    }
    return this;
  }

  public List<Representant<T>> getValidRepresentations()
  {
    return _validValues;
  }

  public List<Representant<T>> getInValidRepresentations()
  {
    return _inValidValues;
  }
}
