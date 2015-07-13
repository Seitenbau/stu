package com.seitenbau.stu.database.generator.values.valuetypes;

import java.math.BigDecimal;
import java.math.BigInteger;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class BooleanValue extends Value<Boolean> {

	public BooleanValue(Boolean value) {
		super(value);
	}

	@Override
	public int compareTo(Integer o) {
		return value.compareTo(o != 0);
	}

	@Override
	public int compareTo(Byte o) {
		return value.compareTo(o != 0);
	}

	@Override
	public int compareTo(Short o) {
		return value.compareTo(o != 0);
	}

	@Override
	public int compareTo(Boolean o) {
		return value.compareTo(o);
	}

	@Override
	public int compareTo(BigDecimal o) {
		return value.compareTo(o != new BigDecimal(0));
	}

	@Override
	public int compareTo(BigInteger o) {
		return value.compareTo(o != BigInteger.valueOf(0));
	}

	@Override
	public int compareTo(Double o) {
		return value.compareTo(o != 0);
	}

	@Override
	public int compareTo(Float o) {
		return value.compareTo(o != 0);
	}

	@Override
	public int compareTo(Long o) {
		return value.compareTo(o != 0);
	}

	@Override
	public int compareTo(String o) {
		return value.toString().compareTo(o);
	}

	@Override
	public int toInt() {
		return (value) ? 1 : 0;
	}

	@Override
	public Value<?> add(Value<?> value) {
		throw new NotImplementedException();
	}

	@Override
	public Value<?> sub(Value<?> value) {
		throw new NotImplementedException();
	}

	@Override
	public Value<?> multi(Value<?> value) {
		throw new NotImplementedException();
	}

	@Override
	public Value<?> div(Value<?> value) {
		throw new NotImplementedException();
	}

	@Override
	public Value<?> mod(Value<?> value) {
		throw new NotImplementedException();
	}

	@Override
	public Value<Boolean> clone() {
		return new BooleanValue(value);
	}

	@Override
	public Value<?> neg() {
		return new BooleanValue((value) ? false : true);
	}
}