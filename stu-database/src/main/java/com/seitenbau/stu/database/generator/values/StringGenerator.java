package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;

public class StringGenerator extends ValueGenerator
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

@Override
public void addConstraint(ConstraintPair constraintPair) {
	// TODO Auto-generated method stub
	
}

@Override
public void clearConstraints() {
	// TODO Auto-generated method stub
	
}
}
