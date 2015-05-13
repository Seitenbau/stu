package com.seitenbau.stu.database.generator.values.valuetypes;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DoubleValue extends Value<Double> {

	public DoubleValue(Double value) {
		super(value);
	}
	
	@Override
	public int compareTo(Double o) {
		return value.compareTo(o);
	}
	
	@Override
	public int compareTo(Integer o) {
		return value.compareTo(Double.valueOf(o));
	}
	
	@Override
	public int compareTo(Byte o) {
		return value.compareTo(Double.valueOf(o));
	}
	
	@Override
	public int compareTo(Short o) {
		return value.compareTo(Double.valueOf(o));
	}
	
	@Override
	public int compareTo(BigInteger o) {
		return value.compareTo(Double.valueOf(o.intValue()));
	}
	
	@Override
	public int compareTo(Float o) {
		double result = value - o;

		if (Math.abs(result) < 0.00001)
			return 0;
		else if (result < 0)
			return 1;
		else
			return -1;
	}

	@Override
	public int compareTo(Long o) {
		return value.compareTo(Double.valueOf(o));
	}
	
	//////////////////////////////////////////////
	
	@Override
	public int compareTo(BigDecimal o) {		
		return BigDecimal.valueOf(value).compareTo(o);
	}

	@Override
	public int compareTo(String o) {
		return value.compareTo(Double.valueOf(o));
	}
	
	
	
	/////////////////////////////////////////////

	@Override
	public int toInt() {
		return value.intValue();
	}
	
	
	@Override
	public Value<?> add(Value<?> value) {
		if(IntValue.class.isInstance(value)){
			return new DoubleValue(this.value + ((DoubleValue)value).getValue());
		}

		return null;		
	}

	@Override
	public Value<?> sub(Value<?> value) {
		if(IntValue.class.isInstance(value)){
			return new DoubleValue(this.value - ((DoubleValue)value).getValue());
		}

		return null;	
	}

	@Override
	public Value<?> multi(Value<?> value) {
		if(IntValue.class.isInstance(value)){
			return new DoubleValue(this.value * ((DoubleValue)value).getValue());
		}

		return null;	
	}

	@Override
	public Value<?> div(Value<?> value) {
		if(IntValue.class.isInstance(value) && ((IntValue)value).getValue() != 0){
			return new DoubleValue(this.value / ((DoubleValue)value).getValue());
		}

		return null;	
	}

	@Override
	public Value<?> mod(Value<?> value) {
		if(IntValue.class.isInstance(value)){
			return new DoubleValue(this.value % ((DoubleValue)value).getValue());
		}

		return null;	
	}

	@Override
	public Value<Double> clone() {
		return new DoubleValue(value);
	}

	@Override
	public Value<?> neg() {
		value = -value;
		return this;
	}
}
