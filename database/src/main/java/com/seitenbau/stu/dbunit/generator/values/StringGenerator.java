package com.seitenbau.stu.dbunit.generator.values;

import java.util.Random;

public class StringGenerator implements ValueGenerator
{

  private Random random;

  private final String[] values = { "\"Hund\"", "\"Katze\"", "\"Maus\"", "\"Alpha\"", "\"Beta\"", "\"Gamma\"", "\"Delta\"", "\"Lorem\"", "\"ipsum\"", "\"dolor\"", "\"sit\"", "\"amet\"" };

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

  public static class Factory implements ValueGeneratorFactory {

    @Override
    public ValueGenerator createGenerator()
    {
      return new StringGenerator();
    }

  }
}
