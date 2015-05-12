package com.seitenbau.stu.database.generator.values.valuetypes;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;

public abstract class Value<T> implements Comparable<T>, Cloneable {

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
	public boolean equals(Object o){
		if(this.getClass().isInstance(o)){
			try {
				if(this.compareTo((Value<?>)o) == 0){
					return true;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		return value.toString();
	}
	
	@Override
	public abstract Value<T> clone();

	public abstract Value<?> add(Value<?> value);
	public abstract Value<?> sub(Value<?> value);
	public abstract Value<?> multi(Value<?> value);
	public abstract Value<?> div(Value<?> value);
	public abstract Value<?> mod(Value<?> value);
	public abstract Value<?> neg();
}
