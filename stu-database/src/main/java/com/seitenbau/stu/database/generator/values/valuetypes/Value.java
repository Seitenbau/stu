package com.seitenbau.stu.database.generator.values.valuetypes;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class Value<T> implements Comparable<T> {

	protected T value;

	public Value(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public abstract int compareTo(BigDecimal o);

	public abstract int compareTo(BigInteger o);

	public abstract int compareTo(Byte o);

	public abstract int compareTo(Double o);

	public abstract int compareTo(Float o);

	public abstract int compareTo(Integer o);

	public abstract int compareTo(Long o);

	public abstract int compareTo(Short o);

	public abstract int compareTo(String o);
	
	public abstract int toInt();

	public int compareTo(Value<?> value) throws Exception {
		if (IntValue.class.isInstance(value))
			return this.compareTo(((IntValue) value).getValue());
		else if (DoubleValue.class.isInstance(value))
			return this.compareTo(((DoubleValue) value).getValue());
		else if (StringValue.class.isInstance(value))
			return this.compareTo(((StringValue) value).getValue());
		// TODO: Implement for all types

		throw new Exception();
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
