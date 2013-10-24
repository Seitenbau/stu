package com.seitenbau.stu.testdata;

public interface PartitionValue
{

  void injectValue(Object target);

  Object getValue();
  
  Partition getPartition();

}
