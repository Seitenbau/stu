package com.seitenbau.testing.dbunit.dsl;

/**
 * A DataSet value which is evaluated when queried.
 */
public interface LazyValue
{
  
  /**
   * Queries the current value
   * @return The value
   */
  Object getValue();
  
}
