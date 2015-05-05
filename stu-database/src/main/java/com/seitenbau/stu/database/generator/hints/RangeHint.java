package com.seitenbau.stu.database.generator.hints;

import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class RangeHint extends Hint {

	private Value<?> upperLimit;
	private Value<?> lowerLimit;
	
	public RangeHint(ConstraintBase constraint) {
		super(constraint);
	}
	
	public RangeHint(ConstraintBase constraint, Value<?> upperLimit, Value<?> lowerLimit) {
		super(constraint);
		this.upperLimit = upperLimit;
		this.lowerLimit = lowerLimit;
	}
	
	public Value<?> getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(Value<?> upperLimit) {
		this.upperLimit = upperLimit;
	}

	public Value<?> getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(Value<?> lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
}
