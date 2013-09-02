package com.seitenbau.testing.dbunit.generator.data;


public class MultiplicityParser
{
  public static final int INFINITE = -1;

  private final MultiplicityBorder min;

  private final MultiplicityBorder max;

  private final int hashCode;

  public MultiplicityParser(String multiplicity)
  {
    if (multiplicity.contains(".."))
    {
      String[] minmax = multiplicity.split("\\.\\.", 2);
      min = new MultiplicityBorder(minmax[0]);
      max = new MultiplicityBorder(minmax[1]);
    }
    else
    {
      min = new MultiplicityBorder(multiplicity);
      max = min;
    }

    hashCode = min.hashCode() * 17 + max.hashCode();
  }

  public MultiplicityBorder getMin()
  {
    return min;
  }

  public MultiplicityBorder getMax()
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
    if (!(o instanceof MultiplicityParser)) {
      return false;
    }

    MultiplicityParser p = (MultiplicityParser)o;
    return (p.min.equals(min)) && (p.max.equals(max));
  }

}