package com.seitenbau.testing.dbunit.generator.values;

public interface ValueGenerator
{
  void initialize(int seed);

  String nextValue();
}
