package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;

public class StringGenerator extends ValueGenerator {

	private Random random;

	private final String[] values = { "\"Hund\"", "\"Katze\"", "\"Maus\"", "\"Alpha\"", "\"Beta\"", "\"Gamma\"", "\"Delta\"", "\"Lorem\"",
			"\"ipsum\"", "\"dolor\"", "\"sit\"", "\"amet\"" };

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
	}

	@Override
	public Result nextValue(EntityBlueprint eb) {
		if(allowNull)
			return new Result(null, true);
		
		if (set != null && set.length > 0) {
				return new Result(set[0].toString(), true);
		}

		return new Result(values[random.nextInt(values.length)], true);
	}

	public static class Factory implements ValueGeneratorFactory {

		@Override
		public ValueGenerator createGenerator() {
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
