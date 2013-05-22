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
    default:
      return value;
    }

    return value;
  }
}
