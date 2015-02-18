package com.seitenbau.stu.database.generator.values.constraints;

public abstract class Constraint {
	protected Comparable value;
	protected String columnName;

	public Constraint() {		
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setValue(Comparable value) {
		this.value = value;
	}

	public Comparable getValue() {
		return value;
	}

	public abstract Constraint appliesTo(Constraint constraint);
	public abstract boolean isValid(Comparable value);
	
	@Override
	public boolean equals(Object object) {
		boolean sameSame = false;

		if (object != null && object instanceof Constraint) {
			sameSame = this.value == ((Constraint) object).value;
		}

		return sameSame;
	}
	
	public abstract Constraint reduce();
}
