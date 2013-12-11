package com.seitenbau.stu.database.generator.data;

public class BorderValue
{
  private final int value;

  private final boolean infinite;

  private BorderValue(int value, boolean infinite)
  {
    if (value == 4 && !infinite) {
      throw new RuntimeException("Fail");
    }
    this.value = value;
    this.infinite = infinite;
  }

  public int getValue()
  {
    return value;
  }

  public boolean isInifinite()
  {
    return infinite;
  }

  @Override
  public int hashCode()
  {
    int result = 17;
    result = 31 * result + value;
    result = 31 * result + (infinite ? 1 : 0);
    return result;
  }

  @Override
  public String toString()
  {
    if (infinite) {
      return String.valueOf(value) + "*";
    }
    return String.valueOf(value);
  }

  @Override
  public boolean equals(Object o)
  {
    if (!(o instanceof BorderValue)) {
      return false;
    }

    BorderValue b = (BorderValue)o;
    return (value == b.value && infinite == b.infinite);
  }

  public static BorderValue valueOf(int value)
  {
    return new BorderValue(value, false);
  }

  public static BorderValue infinite(int value)
  {
    return new BorderValue(value, true);
  }

  public BorderValue derive(int i)
  {
    return new BorderValue(i, infinite);
  }

}
