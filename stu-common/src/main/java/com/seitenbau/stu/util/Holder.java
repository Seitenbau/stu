package com.seitenbau.stu.util;

import com.seitenbau.stu.config.OptionalProperty;

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

  public boolean isSet()
  {
    return getValue() != null;
  }

  /**
   * Returns the value or the defaultValue, in case the property was marked as {@link OptionalProperty} 
   * @param defaultValue
   * @return
   */
  public T getValue(T defaultValue)
  {
    if (isSet())
    {
      return getValue();
    }
    return defaultValue;
  }
}
