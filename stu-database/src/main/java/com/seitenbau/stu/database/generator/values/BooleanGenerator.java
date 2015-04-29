package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.values.valuetypes.StringValue;

public class BooleanGenerator extends ValueGenerator {

	private Random random; 

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
		
		values = new String[]{ "true", "false" };
	}
	
	@Override
	public Result nextValue(){		
		return new Result(new StringValue(values[random.nextInt(values.length)]), true, true);
	}
	
	@Override
	public Result nextValue(Integer index) {
		Random rand = new Random(index);		
		return new Result(new StringValue(values[rand.nextInt(values.length)]), true, true);
	}

	public static class Factory implements ValueGeneratorFactory {

		@Override
		public ValueGenerator createGenerator() {
			return new BooleanGenerator();
		}
	}

	@Override
	public Integer getMaxIndex() {
		return values.length;
	}
}
