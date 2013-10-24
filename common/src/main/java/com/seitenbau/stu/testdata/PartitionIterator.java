package com.seitenbau.stu.testdata;

import java.util.Iterator;

class PartitionIterator
{

  final PartitionValue defaultValue;

  final Iterator<PartitionValue> valueIterator;

  public PartitionIterator(PartitionValue defaultValue,
      Iterator<PartitionValue> valueIterator)
  {
    if(defaultValue == null)
    {
      throw new IllegalArgumentException("Parameter defaultValue is Null.");
    }
    this.defaultValue = defaultValue;
    this.valueIterator = valueIterator;
  }

  public PartitionValue next()
  {
    if (valueIterator.hasNext())
    {
      return valueIterator.next();
    }
    else
    {
      return defaultValue;
    }
  }
}
