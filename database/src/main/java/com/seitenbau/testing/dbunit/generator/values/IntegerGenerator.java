package com.seitenbau.testing.dbunit.generator.values;

import java.util.Random;

public class IntegerGenerator implements ValueGenerator
{

  private Random random;

  private final int min;

  private final int max;

  public IntegerGenerator(int min, int max)
  {
    this.min = min;
    this.max = max;
  }

  @Override
  public void initialize(long seed)
  {
    random = new Random(seed);
  }

  @Override
  public String nextValue()
  {
    int value = random.nextInt(1 + max - min) + min;
    return String.valueOf(value);
  }

  public static class Factory implements ValueGeneratorFactory {

    private final int min;

    private final int max;

    public Factory(int min, int max)
    {
      this.min = min;
      this.max = max;
    }

    @Override
    public ValueGenerator createGenerator()
    {
      return new IntegerGenerator(min, max);
    }

  }
}
