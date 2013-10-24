package com.seitenbau.stu.dbunit.generator.values;

public interface ValueGenerator
{
  void initialize(long seed);

  String nextValue();
}
