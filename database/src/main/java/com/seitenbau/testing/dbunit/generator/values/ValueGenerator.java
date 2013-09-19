package com.seitenbau.testing.dbunit.generator.values;

public interface ValueGenerator
{
  void initialize(long seed);

  String nextValue();
}
