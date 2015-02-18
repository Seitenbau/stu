package com.seitenbau.stu.database.generator.values.constraints;

public class RangeConstraint extends Constraint{

	private int min;
	private int max;
	
	public RangeConstraint(){
		super();
	}
	
	public RangeConstraint(int min, int max) {
		super();
		this.min = min;
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public Constraint appliesTo(Constraint constraint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(Comparable value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Constraint reduce() {
		// TODO Auto-generated method stub
		return null;
	}

}
