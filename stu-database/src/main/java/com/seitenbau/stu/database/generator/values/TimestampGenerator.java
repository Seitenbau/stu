package com.seitenbau.stu.database.generator.values;

import java.util.Calendar;
import java.util.Random;

import com.seitenbau.stu.database.generator.values.valuetypes.StringValue;

public class TimestampGenerator extends ValueGenerator {

	private Random random;

	public TimestampGenerator() {
	}

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
	}
	
	// TODO: Implement TimestampValue

	@Override
	public Result nextValue(Integer index) {
		Result result = new Result(null, true, true);
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		result.setValue(new StringValue("asDate(\"" + currentTimestamp.toString().substring(0, 19) + "\")"));
		return result;
	}

	public int randBetween(int start, int end) {
		return start + random.nextInt(1 + end - start);
	}

	public String getString(int i) {
		if (i < 10) {
			return "0" + String.valueOf(i);
		}
		return String.valueOf(i);
	}

	public static class Factory implements ValueGeneratorFactory {

		@Override
		public ValueGenerator createGenerator() {
			return new TimestampGenerator();
		}
	}

	@Override
	public Integer getMaxIndex() {
		return 10000;
	}
}
