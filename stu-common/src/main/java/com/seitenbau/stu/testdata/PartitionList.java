package com.seitenbau.stu.testdata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class PartitionList implements Iterable<Partition>
{

  private final List<Partition> partitions = new ArrayList<Partition>();

  private int maxTotalCount = 0;

  public void add(Partition partition)
  {
    if (partition.count() > maxTotalCount)
      this.maxTotalCount = partition.count();
    partitions.add(partition);
  }

  public int maxPartitionCount()
  {
    return this.maxTotalCount;
  }

  public Iterator<Partition> iterator()
  {
    return partitions.iterator();
  }
}
