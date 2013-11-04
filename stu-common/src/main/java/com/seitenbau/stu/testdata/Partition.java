package com.seitenbau.stu.testdata;

public interface Partition
{

  int count();

  PartitionIterator iterator();

  ParameterDescriptor getParameterDescriptor();
  
  void generateValues(Filter... filters);
  
  void addValues(Object ... values);

}
