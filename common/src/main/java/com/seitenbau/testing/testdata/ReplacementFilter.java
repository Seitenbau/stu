package com.seitenbau.testing.testdata;

public interface ReplacementFilter extends Filter
{
  boolean filter(PartitionValue partitionValue);
  
  Object replace(PartitionValue partitionValue);

}
