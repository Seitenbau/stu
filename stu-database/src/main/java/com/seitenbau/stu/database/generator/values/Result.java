package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.Column;
import com.seitenbau.stu.database.generator.Table;
import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;

public class Result implements Comparable<Object> {
	private Comparable<?> value = null;
	private boolean isGenerated = false;
	private boolean isFinal = false;

	private ArrayList<ConstraintBase> constraints = new ArrayList<ConstraintBase>();

	public Result(Comparable<?> value, boolean isGenerated, boolean isFinal) {
		this.value = value;
		this.isGenerated = isGenerated;
		this.isFinal = isFinal;
	}

	public Result(Table table, EntityBlueprint eb, Column column, Comparable<?> value, boolean isGenerated) {
		this.table = table;
		this.eb = eb;
		this.col = column;

		this.value = value;
		this.isGenerated = isGenerated;
	}

	public void setGenerated(boolean isGenerated) {
		this.isGenerated = isGenerated;
	}

	private Integer valueIndex = 0;
	private Integer maxIndex = 1000;

	private Table table;
	private EntityBlueprint eb;
	private Column col;
	private ValueGenerator generator;

	public Integer getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(Integer maxIndex) {
		this.maxIndex = maxIndex;
	}

	public void addConstraint(ConstraintBase constraint) {
		constraints.add(constraint);
	}

	public ArrayList<ConstraintBase> getConstraints() {
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

	public void setValue(Comparable<?> value) {
		this.value = value;
		isGenerated = true;
	}

	public Comparable<?> getValue() {
		return value;
	}

	public void setGenerator(ValueGenerator generator) {
		this.generator = generator;
	}

	public ValueGenerator getGenerator() {
		return this.generator;
	}

	public boolean isGenerated() {
		return isGenerated;
	}

//	@Override
//	public String toString() {
//		return this.getClass().getSimpleName().toString() 
//				+ ": Cell => " 
//				+ ((getTable() == null)? "null" : getTable().toString()) + "."
//				+ ((getCol() == null)? "null" : getCol().toString()) 
//				+ ": EB => " + ((getEb() == null)? "null" : getEb().toString())
//
//				+ ": Value => " 
//				+ ((value == null)? "null" : value.toString())
//		
//		+ ": ValueGenerator => " 
//		+ ((generator == null)? "null" : generator.getClass().getSimpleName().toString());
//	}
	
	@Override
	public String toString(){
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

	public ArrayList<Hint> getHints() {
		ArrayList<Hint> hints = new ArrayList<Hint>();
		if (isGenerated) {
			for (ConstraintBase constraint : constraints) {
				Hint hint = constraint.getHint(this.getGenerator(), this.getValue());
				hints.add(hint);
			}
		}

		return hints;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
}
