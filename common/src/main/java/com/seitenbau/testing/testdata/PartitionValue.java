package com.seitenbau.testing.testdata;

public interface PartitionValue
{

  void injectValue(Object target);

  Object getValue();
  
  Partition getPartition();

}
