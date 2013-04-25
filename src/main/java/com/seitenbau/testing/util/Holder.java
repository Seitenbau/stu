package com.seitenbau.testing.util;

public class Holder<T>
{
  T value;

  public T getValue()
  {
    return value;
  }

  public void setValue(T value)
  {
    this.value = value;
  }
}
