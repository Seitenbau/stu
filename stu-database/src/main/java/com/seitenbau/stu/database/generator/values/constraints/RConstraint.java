package com.seitenbau.stu.database.generator.values.constraints;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;

public class RConstraint extends ConstraintInterface {

	private Integer min;
	private Integer max;

	public RConstraint(String column, Integer min, Integer max) {
		this.sourceRef = column;
		this.setMin(min);
		this.setMax(max);
	}

	@Override
	public boolean isValid(Comparable<?> value, EntityBlueprint eb) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadValues(EntityBlueprint eb) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadTargets(EntityBlueprint eb) {
		// TODO Auto-generated method stub
		return false;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

}
