package com.seitenbau.stu.dbunit.extend;

public interface DatasetIdGenerator
{
  Long nextId(String table, String column);
}
