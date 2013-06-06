package com.seitenbau.testing.dbunit.dsl;

import java.math.BigDecimal;

import com.seitenbau.testing.dbunit.generator.DataType;

public class CastUtil
{
  public static Object cast(Object value, DataType type)
  {
    if (type == null || type.getJavaTypeClass().isInstance(value))
    {
      return value;
    }

    switch (type)
    {
    case INTEGER:
    case TINYINT:
    case SMALLINT:
      if (value instanceof Long)
      {
        return ((Long) value).intValue();
      }
      if (value instanceof BigDecimal)
      {
        BigDecimal v = (BigDecimal) value;
        return v.intValueExact();
      }
      break;
    case BIGINT:
      if (value instanceof Integer)
      {
        return Long.valueOf((Integer) value);
      }
      if (value instanceof BigDecimal)
      {
        BigDecimal v = (BigDecimal) value;
        return v.longValueExact();
      }
      break;
    case REAL:
      if (value instanceof Double)
      {
        Double d = (Double)value;
        return d.floatValue();
      }
      if (value instanceof BigDecimal)
      {
        BigDecimal v = (BigDecimal) value;
        return v.floatValue();
      }
      break;
    case DOUBLE:
    case FLOAT:
      if (value instanceof Float)
      {
        return Double.valueOf((Float) value);
      }
      if (value instanceof Integer)
      {
        return Double.valueOf((Integer) value);
      }
      if (value instanceof Long)
      {
        return Double.valueOf((Long) value);
      }
      if (value instanceof BigDecimal)
      {
        BigDecimal v = (BigDecimal) value;
        return v.doubleValue();
      }
      if (value instanceof String)
      {
        return Double.valueOf((String) value);
      }
      break;
    default:
      return value;
    }

    return value;
  }
}
