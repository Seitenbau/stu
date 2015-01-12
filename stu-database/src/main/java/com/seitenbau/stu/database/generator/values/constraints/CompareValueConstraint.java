package com.seitenbau.stu.database.generator.values.constraints;

public class CompareValueConstraint extends Constraint {
	
	public static enum CompareType {
		EQUAL, NOT_EQUAL, GREATER, SMALLER, GREATER_EQUAL, SMALLER_EQUAL;
		
		public CompareType partner(){
			switch (this) {
			case EQUAL:
				return EQUAL;
			case NOT_EQUAL:
				return NOT_EQUAL;
			case GREATER:
				return SMALLER;
			case SMALLER:
				return GREATER;
			case GREATER_EQUAL:
				return SMALLER_EQUAL;
			case SMALLER_EQUAL:
				return GREATER_EQUAL;
			}
			return null;	
		}
	}
	
	private CompareType compareType;

	public CompareValueConstraint() {
	}
	
	public CompareValueConstraint(CompareType ct){
		compareType = ct;
	}	

	public CompareType getCompareType() {
		return compareType;
	}

	public void setCompareType(CompareType compareType) {
		this.compareType = compareType;
	}

	public Constraint equal(Constraint constraint) {
		this.setValue(constraint.getValue());
		return this;
	}

	public Constraint not_equal(Constraint constraint) {
		if (getValue() != constraint.getValue())
			return constraint;
		else
			return  null;
	}

	public Constraint greater(Constraint constraint) {
		if (((Integer)getValue()) > ((Integer)constraint.getValue()))
			return constraint;
		else
			return null;
	}

	public Constraint greater_equal(Constraint constraint) {
		if (((Integer)getValue()) >= ((Integer)constraint.getValue()))
			return constraint;
		else
			return null;
	}

	public Constraint smaller(Constraint constraint) {
		if (((Integer)getValue()) < ((Integer)constraint.getValue()))
			return constraint;
		else
			return null;
	}

	public Constraint smaller_equal(Constraint constraint) {
		if (((Integer)getValue()) <= ((Integer)constraint.getValue()))
			return constraint;
		else
			return null;
	}

	@Override
	public Constraint appliesTo(Constraint constraint) {		
		switch (((CompareValueConstraint) constraint).getCompareType()) {
		case EQUAL:
			return equal(constraint);
		case NOT_EQUAL:
			return not_equal(constraint);
		case GREATER:
			return greater(constraint);
		case GREATER_EQUAL:
			return greater_equal(constraint);
		case SMALLER:
			return smaller(constraint);
		case SMALLER_EQUAL:
			return smaller_equal(constraint);
		}
		return null;
	}
}
