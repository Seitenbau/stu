package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;

public class BooleanGenerator extends ValueGenerator {

	private Random random;

	private final String[] values = { "true", "false" };

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
	}

	@Override
	public String nextValue() {
		return values[random.nextInt(values.length)];
	}

	public static class Factory implements ValueGeneratorFactory {

    @Override
    public ValueGenerator createGenerator()
    {
      return new BooleanGenerator();
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
