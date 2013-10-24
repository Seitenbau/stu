package com.seitenbau.stu.dbunit.util;

import java.math.BigDecimal;

public final class NullCompatibleEquivalence
{
  private NullCompatibleEquivalence()
  {
  }

  public static boolean equals(Object value1, Object value2)
  {
    if (value1 == null && value2 == null)
    {
      return true;
    }

    if (value1 == null || value2 == null)
    {
      return false;
    }

    // both aren't null here, build generalized values
    Object generalizedValue1 = generalize(value1);
    Object generalizedValue2 = generalize(value2);
    if (generalizedValue1 != null && generalizedValue2 != null)
    {
      return generalizedValue1.equals(generalizedValue2);
    }

    return value1.equals(value2);
  }

  /**
   * Brings an object to a more general representation of the object to be able to compare it with
   * another value.
   * An Long and an Integer value should be equal, even their type does not match.
   * @param value The value which should be brought in a more general type
   * @return The value in the same type as given and a more general if possible
   */
  static Object generalize(Object value)
  {
    if (value instanceof String)
    {
      return value;
    }

    if (value instanceof Long)
    {
      return BigDecimal.valueOf((Long)value);
    }
    if (value instanceof Integer)
    {
      return BigDecimal.valueOf((Integer)value);
    }
    if (value instanceof Double)
    {
      return BigDecimal.valueOf((Double)value);
    }
    if (value instanceof Float)
    {
      return BigDecimal.valueOf((Float)value);
    }
    if (value instanceof Short)
    {
      return BigDecimal.valueOf((Short)value);
    }
    if (value instanceof Byte)
    {
      return BigDecimal.valueOf((Byte)value);
    }

    return value;
  }
}
