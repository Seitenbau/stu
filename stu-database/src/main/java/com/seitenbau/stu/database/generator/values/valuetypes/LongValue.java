package com.seitenbau.stu.database.generator.values.valuetypes;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LongValue extends Value<Long> {

	public LongValue(Long value) {
		super(value);
	}
	
	@Override
	public int compareTo(Integer o) {
		return value.compareTo(new Long(o));
	}
	
	@Override
	public int compareTo(Byte o) {
		return value.compareTo(Long.valueOf(o));
	}
	
	@Override
	public int compareTo(Short o) {
		return value.compareTo(Long.valueOf(o));
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
		return value.compareTo(Long.valueOf(o));
	}

	@Override
	public int toInt() {
		if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
	        throw new IllegalArgumentException
	            (value + " cannot be cast to int without changing its value.");
	    }
	    return (int) (long) value;
	}

	@Override
	public Value<?> add(Value<?> value) {
		if(LongValue.class.isInstance(value)){
			return new LongValue(this.value + ((LongValue)value).getValue());
		}

		return null;		
	}

	@Override
	public Value<?> sub(Value<?> value) {
		if(LongValue.class.isInstance(value)){
			return new LongValue(this.value - ((LongValue)value).getValue());
		}

		return null;	
	}

	@Override
	public Value<?> multi(Value<?> value) {
		if(LongValue.class.isInstance(value)){
			return new LongValue(this.value * ((LongValue)value).getValue());
		}

		return null;	
	}

	@Override
	public Value<?> div(Value<?> value) {
		if(LongValue.class.isInstance(value) && ((LongValue)value).getValue() != 0){
			return new LongValue(this.value / ((LongValue)value).getValue());
		}

		return null;	
	}

	@Override
	public Value<?> mod(Value<?> value) {
		if(LongValue.class.isInstance(value)){
			return new LongValue(this.value % ((LongValue)value).getValue());
		}

		return null;	
	}

	@Override
	public Value<Long> clone() {
		return new LongValue(value);
	}

	@Override
	public Value<?> neg() {
		value = -value;
		return this;
	}	
}
