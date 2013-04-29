package com.seitenbau.testing.data.detail;

public interface Representant<T>
{
  public static enum RepresentantType
  {
    VALID, INVALID
  }

  RepresentantType getType();

  T getValue();
}