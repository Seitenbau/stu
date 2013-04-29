package com.seitenbau.testing.testdata;

public class NullPartitionValue implements PartitionValue
{

  public void injectValue(Object target)
  {
  }

  public Object getValue()
  {
    return null;
  }

  public Partition getPartition()
  {
    return null;
  }

}
