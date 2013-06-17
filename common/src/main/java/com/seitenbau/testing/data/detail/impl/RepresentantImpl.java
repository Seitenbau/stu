package com.seitenbau.testing.data.detail.impl;

import com.seitenbau.testing.data.detail.Representant;

public class RepresentantImpl<T> implements Representant<T>
{
  private final RepresentantType _type;

  private final T _value;

  public RepresentantImpl(RepresentantType type, T value)
  {
    _type = type;
    _value = value;
  }

  @Override
  public T getValue()
  {
    return _value;
  }

  @Override
  public RepresentantType getType()
  {
    return _type;
  }

  @Override
  public String toString()
  {
    return "type=" + _type + ",  value=" + _value;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_type == null) ? 0 : _type.hashCode());
    result = prime * result + ((_value == null) ? 0 : _value.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    @SuppressWarnings("rawtypes")
    RepresentantImpl other = (RepresentantImpl) obj;
    if (_type != other._type)
    {
      return false;
    }
    if (_value == null)
    {
      if (other._value != null)
      {
        return false;
      }
    }
    else if (!_value.equals(other._value))
    {
      return false;
    }
    return true;
  }

}
