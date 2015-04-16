package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;

public class BooleanGenerator extends ValueGenerator {

	private Random random; 

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
		
		values = new String[]{ "true", "false" };
	}
	
	@Override
	public Result nextValue(){		
		return new Result(values[random.nextInt(values.length)], true);
	}

	@Override
	public Result nextValue(EntityBlueprint eb) {
		return new Result(values[random.nextInt(values.length)], true);
	}

	@Override
	public Result nextValue(Integer index, EntityBlueprint eb) {
		return new Result(values[random.nextInt(values.length)], true);
	}

	public static class Factory implements ValueGeneratorFactory {

		@Override
		public ValueGenerator createGenerator() {
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

	@Override
	public Integer getMaxIndex() {
		return values.length;
	}
}
