package com.seitenbau.testing.dbunit.extend.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;

public class DefaultIdGenerator implements DatasetIdGenerator
{
  Map<String, AtomicLong> tableIds = new HashMap<String, AtomicLong>();

  public Long nextId(String table, String column)
  {
    return getAtomicLongFor(table).getAndIncrement();
  }

  synchronized AtomicLong getAtomicLongFor(String table)
  {
    AtomicLong atomic = tableIds.get(table);
    if (atomic == null)
    {
      atomic = new AtomicLong(1);
      tableIds.put(table, atomic);
    }
    return atomic;
  }

}
