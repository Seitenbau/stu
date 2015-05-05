package com.seitenbau.stu.database.generator.values.valuetypes;

import java.math.BigDecimal;
import java.math.BigInteger;

public class IntValue extends Value<Integer> {

	public IntValue(Integer value) {
		super(value);
	}
	
	@Override
	public int compareTo(Integer o) {
		return value.compareTo(o);
	}
	
	@Override
	public int compareTo(Byte o) {
		return value.compareTo(Integer.valueOf(o));
	}
	
	@Override
	public int compareTo(Short o) {
		return value.compareTo(Integer.valueOf(o));
	}
	
	//////////////////////////////////////////////
	
	@Override
	public int compareTo(BigDecimal o) {		
		return BigDecimal.valueOf(value).compareTo(o);
	}

	@Override
	public int compareTo(BigInteger o) {
		return BigInteger.valueOf(value).compareTo(o);
	}	

	@Override
	public int compareTo(Double o) {
		return Double.valueOf(value).compareTo(o);
	}

	@Override
	public int compareTo(Float o) {
		return Float.valueOf(value).compareTo(o);
	}

	@Override
	public int compareTo(Long o) {
		return Long.valueOf(value).compareTo(o);
	}

	@Override
	public int compareTo(String o) {
		return value.toString().compareTo(o);
	}

	@Override
	public int toInt() {
		return value;
	}	
}
