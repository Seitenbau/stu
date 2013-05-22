package com.seitenbau.testing.dbunit.util;

public final class NullCompatibleEquivalence
{
  private NullCompatibleEquivalence() {
  }
  
  public static boolean equals(Object value1, Object value2) {
    if (value1 == null && value2 == null) {
      return true;
    }
    
    if (value1 == null || value2 == null) {
      return false;
    }
    
    // both aren't null here
    Long long1 = toLong(value1);
    Long long2 = toLong(value2);
    if (long1 != null && long2 != null) {
      return long1.equals(long2);
    }

    return value1.equals(value2);
  }
  
  private static Long toLong(Object value) {
    if (value instanceof Long) {
      return (Long)value;
    }
    if (value instanceof Integer) {
      return Long.valueOf((Integer)value);
    }
    
    return null;
  }
}
