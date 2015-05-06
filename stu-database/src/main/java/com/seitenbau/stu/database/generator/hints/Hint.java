package com.seitenbau.stu.database.generator.hints;

import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class Hint {
	
	private ConstraintBase constraint;
	private String sourceName;
	private Value<?> value;
	
	public Hint(ConstraintBase constraint){
		this.constraint = constraint;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public Value<?> getValue() {
		return value;
	}

	public void setValue(Value<?> value) {
		this.value = value;
	}

	public ConstraintBase getConstraint() {
		return constraint;
	}

	public void setConstraint(ConstraintBase constraint) {
		this.constraint = constraint;
	}
	
	@Override
	public String toString(){
		return this.getClass().getSimpleName().toString() 
				+ ": Value => " 
				+ ((value == null)? "null" : value.toString());
	}
}
