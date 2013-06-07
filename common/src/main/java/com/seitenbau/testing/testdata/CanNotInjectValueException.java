package com.seitenbau.testing.testdata;

public class CanNotInjectValueException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public CanNotInjectValueException(String propertyName)
  {
    super("Can not inject value in property " + propertyName);
  }
}
