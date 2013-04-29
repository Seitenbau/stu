package com.seitenbau.testing.data.specs;

import com.seitenbau.testing.data.detail.Representant;
import com.seitenbau.testing.data.detail.Representant.RepresentantType;
import com.seitenbau.testing.data.detail.impl.RepresentantImpl;

public abstract class OfTypeTestBase
{
  protected <T> Representant<T> valid(T value)
  {
    return (Representant<T>) new RepresentantImpl<T>(RepresentantType.VALID, value);
  }

  protected <T> Representant<T> invalid(T value)
  {
    return (Representant<T>) new RepresentantImpl<T>(RepresentantType.INVALID, value);
  }
}
