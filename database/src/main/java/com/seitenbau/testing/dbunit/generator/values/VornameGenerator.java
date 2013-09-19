package com.seitenbau.testing.dbunit.generator.values;

import java.util.Random;

public class VornameGenerator implements ValueGenerator
{

  private Random random;

  private final String[] values = { "\"Thomas\"", "\"Thorsten\"", "\"Markus\"", "\"Andreas\"", "\"Stefan\"", "\"Heike\"", "\"Brigitte\"", "\"Elke\"", "\"Angelika\"", "\"Simone\"", "\"Bettina\"", "\"Claudia\"", "\"Manuela\"", "\"Silke\"" };

  @Override
  public void initialize(long seed)
  {
    random = new Random(seed);
  }

  @Override
  public String nextValue()
  {
    return values[random.nextInt(values.length)];
  }

}
