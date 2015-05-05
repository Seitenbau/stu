package com.seitenbau.stu.database.generator.values.valuetypes;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StringValue extends Value<String> {

	public StringValue(String value) {
		super(value);
	}

	@Override
	public int compareTo(String o) {
		return value.compareTo(o);
	}

	@Override
	public int compareTo(BigDecimal o) {
		return compareToValue(o);
	}

	@Override
	public int compareTo(BigInteger o) {
		return compareToValue(o);
	}

	@Override
	public int compareTo(Byte o) {
		return compareToValue(o);
	}

	@Override
	public int compareTo(Double o) {
		return compareToValue(o);
	}

	@Override
	public int compareTo(Float o) {
		return compareToValue(o);
	}

	@Override
	public int compareTo(Integer o) {
		return compareToValue(o);
	}

	@Override
	public int compareTo(Long o) {
		return compareToValue(o);
	}

	@Override
	public int compareTo(Short o) {
		return compareToValue(o);
	}
	

	private int compareToValue(Number o) {
		return value.compareTo(String.valueOf(o));
	}

	@Override
	public int toInt() {
		return Integer.valueOf(value);
	}
}
