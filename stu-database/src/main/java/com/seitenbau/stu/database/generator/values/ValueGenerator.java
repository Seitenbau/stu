package com.seitenbau.stu.database.generator.values;

public interface ValueGenerator
{
  void initialize(long seed);

  String nextValue();
}
