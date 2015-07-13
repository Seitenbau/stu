package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.values.valuetypes.BooleanValue;
import com.seitenbau.stu.database.generator.values.valuetypes.StringValue;

public class BooleanGenerator extends ValueGenerator {

	private Random random; 

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
	}
	
	@Override
	public Result nextValue(Integer index) {
		Random rand = new Random(index);		
		return new Result(new BooleanValue((random.nextInt(2) == 0)? true : false), true, true);
	}

	public static class Factory implements ValueGeneratorFactory {

		@Override
		public ValueGenerator createGenerator() {
			return new BooleanGenerator();
		}
	}

	@Override
	public Integer getMaxIndex() {
		return 2;
	}
}
