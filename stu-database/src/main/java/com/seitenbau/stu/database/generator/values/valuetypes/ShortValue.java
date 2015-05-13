package com.seitenbau.stu.database.generator.values.valuetypes;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ShortValue extends Value<Short> {

	public ShortValue(Short value) {
		super(value);
	}
	
	@Override
	public int compareTo(Integer o) {
		return Integer.valueOf(value).compareTo(o);
	}
	
	@Override
	public int compareTo(Byte o) {
		return value.compareTo(Short.valueOf(o));
	}
	
	@Override
	public int compareTo(Short o) {
		return value.compareTo(o);
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
		return value.compareTo(Short.valueOf(o));
	}

	@Override
	public int toInt() {
		return value;
	}

	@Override
	public Value<?> add(Value<?> value) {
		if(ShortValue.class.isInstance(value)){
			return new ShortValue((short) (this.value + ((ShortValue)value).getValue()));
		}

		return null;		
	}

	@Override
	public Value<?> sub(Value<?> value) {
		if(ShortValue.class.isInstance(value)){
			return new ShortValue((short) (this.value - ((ShortValue)value).getValue()));
		}

		return null;	
	}

	@Override
	public Value<?> multi(Value<?> value) {
		if(ShortValue.class.isInstance(value)){
			return new ShortValue((short) (this.value * ((ShortValue)value).getValue()));
		}

		return null;	
	}

	@Override
	public Value<?> div(Value<?> value) {
		if(ShortValue.class.isInstance(value) && ((ShortValue)value).getValue() != 0){
			return new ShortValue((short) (this.value / ((ShortValue)value).getValue()));
		}

		return null;	
	}

	@Override
	public Value<?> mod(Value<?> value) {
		if(ShortValue.class.isInstance(value)){
			return new ShortValue((short) (this.value % ((ShortValue)value).getValue()));
		}

		return null;	
	}

	@Override
	public Value<Short> clone() {
		return new ShortValue(value);
	}

	@Override
	public Value<?> neg() {
		return new ShortValue((short) -value);
	}	
}
