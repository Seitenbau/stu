package com.seitenbau.testing.util;

public class Settable<T>
{

  T _value;

  boolean _wasSet;

  public Settable()
  {
  }

  public Settable(T value)
  {
    setValue(value);
  }

  public void setValue(T value)
  {
    _wasSet = true;
    _value = value;
  }

  public boolean wasSet()
  {
    return _wasSet;
  }

  public T getValue()
  {
    return _value;
  }

}