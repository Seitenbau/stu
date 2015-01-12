package com.seitenbau.stu.database.generator;

import com.seitenbau.stu.database.generator.values.constraints.Constraint;

public class ConstraintColumnPair {
	private Constraint constraint1;
	private String name1;
	private Constraint constraint2;
	private String name2;
	
	public ConstraintColumnPair(Constraint constraint1, String name1, Constraint constraint2, String name2) {
		super();
		this.constraint1 = constraint1;		
		this.constraint2 = constraint2;
		this.name1 = name1;
		this.name2 = name2;
	}

	public String getName1() {
		return name1;
	}

	public String getName2() {
		return name2;
	}

	public Constraint getConstraint1() {
		return constraint1;
	}

	public Constraint getConstraint2() {
		return constraint2;
	}
}
