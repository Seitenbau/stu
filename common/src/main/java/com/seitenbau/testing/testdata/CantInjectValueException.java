package com.seitenbau.testing.testdata;

public class CantInjectValueException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public CantInjectValueException(String propertyName)
  {
    super("Can not inject value in property " + propertyName);
  }
}
