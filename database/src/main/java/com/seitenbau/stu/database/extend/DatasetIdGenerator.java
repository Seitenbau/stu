package com.seitenbau.stu.database.extend;

public interface DatasetIdGenerator
{
  Long nextId(String table, String column);
}
