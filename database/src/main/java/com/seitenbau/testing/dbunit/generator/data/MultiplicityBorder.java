package com.seitenbau.testing.dbunit.generator.data;

public class MultiplicityBorder
{
  private final boolean infinite;

  private final int value;

  private final int hashCode;

  MultiplicityBorder(String value)
  {
    this.infinite = isValueInfinite(value);
    this.value = parseValue(value);

    this.hashCode = 17 * this.value + (this.infinite ? 1 : 0);
  }

  public int getValue()
  {
    return value;
  }

  public boolean isInfinite()
  {
    return infinite;
  }

  @Override
  public int hashCode()
  {
    return hashCode;
  }

  @Override
  public boolean equals(Object o)
  {
    if (!(o instanceof MultiplicityBorder)) {
      return false;
    }

    MultiplicityBorder b = (MultiplicityBorder)o;
    return (b.infinite == infinite) && (b.value == value);
  }

  private static boolean isValueInfinite(String value)
  {
    return "*".equals(value);
  }

  private static int parseValue(String value)
  {
    if (isValueInfinite(value)) {
      return -1;
    }

    return Integer.parseInt(value);
  }

}
