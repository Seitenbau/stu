package com.seitenbau.testing.dbunit.dsl;

import java.math.BigDecimal;

import com.seitenbau.testing.dbunit.generator.DataType;

public class CastUtil
{

  public static Object cast(Object value, DataType type)
  {
    if (type == null || value == null || type.getJavaTypeClass().isInstance(value))
    {
      return value;
    }

    switch (type)
    {
    case UNKNOWN:
      return value;

    case VARCHAR:
    case CHAR:
    case LONGVARCHAR:
    case CLOB:
      return castToString(value);

    case NUMERIC:
    case DECIMAL:
      return castToBigDecimal(value);

    case BOOLEAN:
    case BIT:
      return castToBoolean(value);

    case INTEGER:
    case TINYINT:
    case SMALLINT:
      return castToInteger(value);

    case BIGINT:
      return castToLong(value);

    case REAL:
      return castToFloat(value);

    case DOUBLE:
    case FLOAT:
      return castToDouble(value);

    case DATE:
    case TIME:
    case TIMESTAMP:
      return castToDate(value);

    case VARBINARY:
    case BINARY:
    case LONGVARBINARY:
    case BLOB:    
      return castToByteArray(value);

    default:
      throw new RuntimeException("Unhandled DataType in CastUtil: " + type.toString());
    }
  }
  
  static Object castToString(Object value)
  {
    // TODO NM implement
    return value;
  }

  static Object castToBigDecimal(Object value)
  {
    // TODO NM implement
    return value;
  }

  static Object castToBoolean(Object value)
  {
    // TODO NM implement
    return value;
  }

  static Object castToInteger(Object value)
  {
    if (value instanceof Long)
    {
      return ((Long) value).intValue();
    }
    if (value instanceof BigDecimal)
    {
      BigDecimal v = (BigDecimal) value;
      return v.intValueExact();
    }    
    return value;
  }

  static Object castToLong(Object value)
  {
    if (value instanceof Integer)
    {
      return Long.valueOf((Integer) value);
    }
    if (value instanceof BigDecimal)
    {
      BigDecimal v = (BigDecimal) value;
      return v.longValueExact();
    }
    return value;
  }

  static Object castToFloat(Object value)
  {
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
    return value;
  }

  static Object castToDouble(Object value)
  {
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
    
    return value;
  }

  static Object castToDate(Object value)
  {
    // TODO NM implement
    return value;
  }
  
  static Object castToByteArray(Object value)
  {
    // TODO NM implement
    return value;
  }

}
