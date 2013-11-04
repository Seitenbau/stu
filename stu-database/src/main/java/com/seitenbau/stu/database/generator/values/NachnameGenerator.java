package com.seitenbau.stu.database.generator.values;

import java.util.Random;

public class NachnameGenerator implements ValueGenerator
{

  private Random random;

  private final String[] values = { "\"M�ller\"", "\"Mustermann\"", "\"Schmidt\"", "\"Schneider\"", "\"Fischer\"", "\"Meyer\"" };

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
      return new NachnameGenerator();
    }

  }
}
