package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.Column;
import com.seitenbau.stu.database.generator.Table;
import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintInterface;

public class Result implements Comparable<Object> {
	private Object value;
	private boolean isGenerated;

	private ArrayList<ConstraintInterface> constraints = new ArrayList<ConstraintInterface>();

	public void setGenerated(boolean isGenerated) {
		this.isGenerated = isGenerated;
	}

	private Integer valueIndex = 0;
	private Integer maxIndex = 1000;

	private Table table;
	private EntityBlueprint eb;
	private Column col;
	private ValueGenerator vg;

	public Integer getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(Integer maxIndex) {
		this.maxIndex = maxIndex;
	}

	public void addConstraint(ConstraintInterface constraint) {
		constraints.add(constraint);
	}

	public ArrayList<ConstraintInterface> getConstraints() {
		return constraints;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public EntityBlueprint getEb() {
		return eb;
	}

	public void setEb(EntityBlueprint eb) {
		this.eb = eb;
	}

	public Column getCol() {
		return col;
	}

	public void setCol(Column col) {
		this.col = col;
	}

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

	@Override
	public String toString() {
		if (isGenerated) {
			if (value != null)
				return value.toString();
		}

		return "null";
	}

	public Integer getValueIndex() {
		return valueIndex;
	}

	public void setValueIndex(Integer valueIndex) {
		this.valueIndex = valueIndex;
	}

	public boolean nextValueIndex() {
		if (valueIndex < maxIndex) {
			valueIndex++;
			return true;
		}

		return false;
	}

	@Override
	public int compareTo(Object o) {
		if (Result.class.isInstance(o)) {

			Result externResult = (Result) o;

			if (externResult.getTable() == this.getTable() && externResult.getEb() == this.getEb() && externResult.getCol() == this.getCol())
				return 0;
		}

		return -1;
	}

	@Override
	public boolean equals(Object v) {
		if (compareTo(v) == 0)
			return true;
		else
			return false;
	}

	public void setGenerator(ValueGenerator g) {
		vg = g;		
	}
	
	public ValueGenerator getGenerator(){
		return vg;
	}
}
