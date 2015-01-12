package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;

public class IntegerGenerator extends ValueGenerator {

	private Random random;

	private final int min;

	private final int max;

	private final long module;

	private final Strategy strategy;

	public IntegerGenerator(int min, int max) {
		this.min = min;
		this.max = max;
		module = (long) max - min;
		if (module < Integer.MAX_VALUE) {
			this.strategy = new IntRange();
		} else {
			this.strategy = new LongRange();
		}
	}

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
	}

	@Override
	public String nextValue() {
		if (constraintPairs == null || constraintPairs.size() <= 0) {
			return strategy.nextValue().toString();
		} else {
			ConstraintPair cp = constraintPairs.get(0);
			if (cp.getDependingConstraint().getValue() != null) {

				cp.getMyConstraint().setValue(strategy.nextValue());
				while (cp.getMyConstraint().appliesTo(cp.getDependingConstraint()) == null) {
					cp.getMyConstraint().setValue(strategy.nextValue());
				}
				return cp.getMyConstraint().getValue().toString();
			} else {
				Object value = strategy.nextValue();
				cp.getMyConstraint().setValue(value);
				return value.toString();
			}
		}
	}

	private interface Strategy {
		Object nextValue();
	}

	private class LongRange implements Strategy {
		@Override
		public Object nextValue() {
			long value = (Math.abs(random.nextLong()) % module) + min;
			return value;
		}
	}

	private class IntRange implements Strategy {
		@Override
		public Object nextValue() {
			int value = random.nextInt(1 + max - min) + min;
			return value;
		}
	}

	public static class Factory implements ValueGeneratorFactory {

		private final int min;

		private final int max;

		public Factory(int min, int max) {
			this.min = min;
			this.max = max;
		}

		@Override
		public ValueGenerator createGenerator() {
			return new IntegerGenerator(min, max);
		}

	}
}
