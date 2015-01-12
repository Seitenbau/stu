package com.seitenbau.stu.database.generator.values.constraints;

public class ConstraintPair {
	private Constraint myConstraint;
	private Constraint dependingConstraint;

	
	public Constraint getMyConstraint() {
		return myConstraint;
	}


	public Constraint getDependingConstraint() {
		return dependingConstraint;
	}


	public ConstraintPair(Constraint myConstraint, Constraint dependingConstraint) {
		this.myConstraint = myConstraint;
		this.dependingConstraint = dependingConstraint;
	}

}
