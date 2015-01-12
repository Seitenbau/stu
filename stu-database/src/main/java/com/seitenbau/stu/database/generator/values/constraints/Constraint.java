package com.seitenbau.stu.database.generator.values.constraints;

public abstract class Constraint {
	public enum Mode { ENTITY, TABLE}
	protected Comparable value;

	protected Mode mode = Mode.ENTITY;

	public Constraint() {		
	}

	public void setValue(Comparable value) {
		this.value = value;
	}

	public Comparable getValue() {
		return value;
	}
	
	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public abstract Constraint appliesTo(Constraint constraint);
}
