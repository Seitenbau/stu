package com.seitenbau.stu.database.generator.values;

public class Result {
	private Object value;
	private boolean isGenerated;

	public Result(Object value, boolean isGenerated) {
		this.value = value;
		this.isGenerated = isGenerated;
	}

	public void setValue(Object value) {
		this.value = value;
		isGenerated = true;
	}

	public Object getValue() {
		return value;
	}

	public boolean isGenerated() {
		return isGenerated;
	}
}
