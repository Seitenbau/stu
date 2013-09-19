package com.seitenbau.testing.dbunit.generator.data;


public class Multiplicity
{
  public static final int INFINITE = -1;

  private final Border min;

  private final Border max;

  private final int hashCode;

  private Multiplicity(Border min, Border max)
  {
    this.min = min;
    this.max = max;
    hashCode = min.hashCode() * 17 + max.hashCode();
  }

  public static Multiplicity parse(String multiplicity)
  {
    if (multiplicity.contains(".."))
    {
      String[] minmax = multiplicity.split("\\.\\.", 2);
      return new Multiplicity(Border.parse(minmax[0]),
                              Border.parse(minmax[1]));
    }
    else
    {
      Border min = Border.parse(multiplicity);
      return new Multiplicity(min, min);
    }
  }

  public Border getMin()
  {
    return min;
  }

  public Border getMax()
  {
    return max;
  }

  @Override
  public int hashCode()
  {
    return hashCode;
  }

  @Override
  public boolean equals(Object o)
  {
    if (!(o instanceof Multiplicity)) {
      return false;
    }

    Multiplicity p = (Multiplicity)o;
    return (p.min.equals(min)) && (p.max.equals(max));
  }

  public static class Border
  {
    private final boolean infinite;

    private final int value;

    private Border(int value, boolean infinite)
    {
      this.value = value;
      this.infinite = infinite;
    }

    static Border parse(String value)
    {
      return new Border(parseValue(value), isValueInfinite(value));
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
      return value;
    }

    @Override
    public boolean equals(Object o)
    {
      if (!(o instanceof Border)) {
        return false;
      }

      Border b = (Border)o;
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

      int result = Integer.parseInt(value);
      if (result < 0)
      {
        throw new RuntimeException("Illegal Multiplicity border: " + value);
      }
      return result;
    }

  }

}
