package com.seitenbau.stu.database.generator.values.constraints;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;

public class SolverConstraint extends ConstraintInterface {
	
	private Constraint constraint;
	private Integer con;
	private String target;

	public SolverConstraint(Constraint c, String column, Integer con, String target) {
		this.constraint = c;
		this.sourceRef = column;
		this.con = con;
		this.target = target;
	}
	
	public Constraint getConstraint() {
		return constraint;
	}

	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

	public Integer getCon() {
		return con;
	}

	public void setCon(Integer con) {
		this.con = con;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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
	
	public enum Constraint{
		XplusClteqZ, XgteqY
	}
}
