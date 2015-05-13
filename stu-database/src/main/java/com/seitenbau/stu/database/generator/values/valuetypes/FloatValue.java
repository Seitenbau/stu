package com.seitenbau.stu.database.generator.values.valuetypes;

import java.math.BigDecimal;
import java.math.BigInteger;

public class FloatValue extends Value<Float> {

	public FloatValue(Float value) {
		super(value);
	}

	@Override
	public int compareTo(Double o) {
		double result = value - o;

		if (Math.abs(result) < 0.00001)
			return 0;
		else if (result < 0)
			return 1;
		else
			return -1;
	}

	@Override
	public int compareTo(Integer o) {
		return value.compareTo(Float.valueOf(o));
	}

	@Override
	public int compareTo(Byte o) {
		return value.compareTo(Float.valueOf(o));
	}

	@Override
	public int compareTo(Short o) {
		return value.compareTo(Float.valueOf(o));
	}

	@Override
	public int compareTo(BigInteger o) {
		return value.compareTo(Float.valueOf(o.intValue()));
	}

	@Override
	public int compareTo(Float o) {
		return value.compareTo(o);
	}

	@Override
	public int compareTo(Long o) {
		return value.compareTo(Float.valueOf(o));
	}

	// ////////////////////////////////////////////

	@Override
	public int compareTo(BigDecimal o) {
		return BigDecimal.valueOf(value).compareTo(o);
	}

	@Override
	public int compareTo(String o) {
		return value.compareTo(Float.valueOf(o));
	}

	@Override
	public int toInt() {
		return value.intValue();
	}

	@Override
	public Value<?> add(Value<?> value) {
		if (IntValue.class.isInstance(value)) {
			return new FloatValue(this.value + ((FloatValue) value).getValue());
		}

		return null;
	}

	@Override
	public Value<?> sub(Value<?> value) {
		if (IntValue.class.isInstance(value)) {
			return new FloatValue(this.value - ((FloatValue) value).getValue());
		}

		return null;
	}

	@Override
	public Value<?> multi(Value<?> value) {
		if (IntValue.class.isInstance(value)) {
			return new FloatValue(this.value * ((FloatValue) value).getValue());
		}

		return null;
	}

	@Override
	public Value<?> div(Value<?> value) {
		if (IntValue.class.isInstance(value) && ((IntValue) value).getValue() != 0) {
			return new FloatValue(this.value / ((FloatValue) value).getValue());
		}

		return null;
	}

	@Override
	public Value<?> mod(Value<?> value) {
		if (IntValue.class.isInstance(value)) {
			return new FloatValue(this.value % ((FloatValue) value).getValue());
		}

		return null;
	}

	@Override
	public Value<Float> clone() {
		return new FloatValue(value);
	}

	@Override
	public Value<?> neg() {
		value = -value;
		return this;
	}
}
