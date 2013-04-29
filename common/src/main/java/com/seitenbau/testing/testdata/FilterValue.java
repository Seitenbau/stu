package com.seitenbau.testing.testdata;

/**
 * Interface for a filter for a test data value.
 */
public interface FilterValue extends Filter
{

  /**
   * 
   * @param partitionValue
   * @return
   */
  boolean filter(PartitionValue partitionValue);

}
