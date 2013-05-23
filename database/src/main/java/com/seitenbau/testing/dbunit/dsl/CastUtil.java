package com.seitenbau.testing.dbunit.dsl;

import com.seitenbau.testing.dbunit.generator.DataType;

public class CastUtil
{
  public static Object cast(Object value, DataType type)
  {
    if (type == null || type.getJavaTypeClass().isInstance(value)) {
      return value;
    }
    
    switch (type) {
    case INTEGER:
    case TINYINT:
    case SMALLINT:
      if (value instanceof Long) {
        return ((Long)value).intValue();
      }
      break;
    case BIGINT:
      if (value instanceof Integer) {
        return Long.valueOf((Integer)value);
      }
      break;
    case REAL:
      break;
    case DOUBLE:
    case FLOAT:
      if (value instanceof Float) {
        return Double.valueOf((Float)value);
      }
      if (value instanceof Integer) {
        return Double.valueOf((Integer)value);
      }
      if (value instanceof Long) {
        return Double.valueOf((Long)value);
      }
      if (value instanceof String) {
        return Double.valueOf((String)value);
      }
      break;
    default:
      return value;
    }

    return value;
  }
}
