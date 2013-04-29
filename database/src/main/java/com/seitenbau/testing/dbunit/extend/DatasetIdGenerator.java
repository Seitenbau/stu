package com.seitenbau.testing.dbunit.extend;

public interface DatasetIdGenerator
{
  Long nextId(String table, String column);
}
