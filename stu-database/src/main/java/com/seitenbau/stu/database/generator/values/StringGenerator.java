package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

import com.seitenbau.stu.database.generator.values.valuetypes.StringValue;

public class StringGenerator extends ValueGenerator {

	private Random random;

	@Override
	public void initialize(long seed) {
		random = new Random(seed);		

		// http://kodejava.org/how-do-i-generate-a-random-alpha-numeric-string/
	}


	@Override
	public Result nextValue(Integer index) {	
		return new Result(new StringValue(RandomStringUtils.randomAlphabetic(5)), true, true);
	}

	public static class Factory implements ValueGeneratorFactory {

		@Override
		public ValueGenerator createGenerator() {
			return new StringGenerator();
		}

	}
	
	@Override
	public Integer getMaxIndex() {
		return 1000;
	}
}
