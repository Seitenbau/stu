package com.seitenbau.stu.database.generator.values.valuetypes;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang.NotImplementedException;

public class ByteValue extends Value<Byte> {

	public ByteValue(Byte value) {
		super(value);
	}
	
	@Override
	public int compareTo(Integer o) {
		return Integer.valueOf(value).compareTo(o);
	}
	
	@Override
	public int compareTo(Byte o) {
		return value.compareTo(o);
	}
	
	@Override
	public int compareTo(Short o) {
		return Short.valueOf(value).compareTo(o);
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

	@Override
	public Value<?> add(Value<?> value) {
		if(ByteValue.class.isInstance(value)){
			return new ByteValue((byte) (this.value + ((ByteValue)value).getValue()));
		}

		return null;		
	}

	@Override
	public Value<?> sub(Value<?> value) {
		if(ByteValue.class.isInstance(value)){
			return new ByteValue((byte) (this.value - ((ByteValue)value).getValue()));
		}

		return null;	
	}

	@Override
	public Value<?> multi(Value<?> value) {
		if(ByteValue.class.isInstance(value)){
			return new ByteValue((byte) (this.value * ((ByteValue)value).getValue()));
		}

		return null;	
	}

	@Override
	public Value<?> div(Value<?> value) {
		if(ByteValue.class.isInstance(value) && ((ByteValue)value).getValue() != 0){
			return new ByteValue((byte) (this.value / ((ByteValue)value).getValue()));
		}

		return null;	
	}

	@Override
	public Value<?> mod(Value<?> value) {
		if(ByteValue.class.isInstance(value)){
			return new ByteValue((byte) (this.value % ((ByteValue)value).getValue()));
		}

		return null;	
	}

	@Override
	public Value<Byte> clone() {
		return new ByteValue(value);
	}

	@Override
	public Value<?> neg() {
		return new ByteValue((byte) -value);
	}	
}
