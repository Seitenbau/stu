package com.seitenbau.stu.database.generator.values.constraints;

public class CompareValueConstraint extends Constraint {

	public static enum CompareType {
		EQUAL, NOT_EQUAL, GREATER, SMALLER, GREATER_EQUAL, SMALLER_EQUAL;

		int value;
		
		CompareType(){}
		CompareType(int value){
			this.value = value;
		}
		
		public CompareType partner() {
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

	public CompareValueConstraint(CompareType ct) {
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
			return null;
	}

	public Constraint greater(Constraint constraint) {
		if (getValue().compareTo(constraint.getValue()) > 0)
			return constraint;
		else
			return null;
	}

	public Constraint greater_equal(Constraint constraint) {
		if (getValue().compareTo(constraint.getValue()) >= 0)
			return constraint;
		else
			return null;
	}

	public Constraint smaller(Constraint constraint) {
		if (getValue().compareTo(constraint.getValue()) < 0)
			return constraint;
		else
			return null;
	}

	public Constraint smaller_equal(Constraint constraint) {
		if (getValue().compareTo(constraint.getValue()) <= 0)
			return constraint;
		else
			return null;
	}
	
	///////////////////////////////////////////////////////////////
	
	public boolean equal(Comparable value) {
		if (value.compareTo(this.value) == 0)
			return true;
		else
			return false;
	}

	public boolean not_equal(Comparable value) {
		if (value.compareTo(this.value) != 0)
			return true;
		else
			return false;
	}

	public boolean greater(Comparable value) {
		if (value.compareTo(this.value) > 0)
			return true;
		else
			return false;
	}

	public boolean greater_equal(Comparable value) {
		if (value.compareTo(this.value) >= 0)
			return true;
		else
			return false;
	}

	public boolean smaller(Comparable value) {
		if (value.compareTo(this.value) < 0)
			return true;
		else
			return false;
	}

	public boolean smaller_equal(Comparable value) {
		if (value.compareTo(this.value) <= 0)
			return true;
		else
			return false;
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

	@Override
	public boolean isValid(Comparable value) {

		switch (getCompareType()) {
		case EQUAL:
			return !equal(value);
		case NOT_EQUAL:
			return !not_equal(value);
		case GREATER:
			return !greater(value);
		case GREATER_EQUAL:
			return !greater_equal(value);
		case SMALLER:
			return !smaller(value);
		case SMALLER_EQUAL:
			return !smaller_equal(value);
		}
			return true;
	}

	@Override
	public Constraint reduce() {
		// TODO Auto-generated method stub
		return null;
	}	 
}
