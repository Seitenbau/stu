package com.seitenbau.stu.database.generator.values;

import java.util.Random;

public class IntegerGenerator implements ValueGenerator
{

  private Random random;

  private final int min;

  private final int max;

  private final long module;

  private final Strategy strategy;

  public IntegerGenerator(int min, int max)
  {
    this.min = min;
    this.max = max;
    module = (long)max - min;
    if (module < Integer.MAX_VALUE)
    {
      this.strategy = new IntRange();
    } else {
      this.strategy = new LongRange();
    }
  }

  @Override
  public void initialize(long seed)
  {
    random = new Random(seed);
  }

  @Override
  public String nextValue()
  {
    return strategy.nextValue();
  }

  private interface Strategy
  {
    String nextValue();
  }

  private class LongRange implements Strategy
  {
    @Override
    public String nextValue()
    {
      long value = (Math.abs(random.nextLong()) % module) + min;
      return String.valueOf(value);
    }
  }

  private class IntRange implements Strategy
  {
    @Override
    public String nextValue()
    {
      int value = random.nextInt(1 + max - min) + min;
      return String.valueOf(value);
    }
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
