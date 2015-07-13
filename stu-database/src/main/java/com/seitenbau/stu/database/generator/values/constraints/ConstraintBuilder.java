package com.seitenbau.stu.database.generator.values.constraints;

import com.seitenbau.stu.database.generator.ColumnBuilder;
import com.seitenbau.stu.database.generator.TableBuilder;
import com.seitenbau.stu.database.generator.values.constraints.logical.EqualConstraint;
import com.seitenbau.stu.database.generator.values.constraints.logical.GreaterConstraint;
import com.seitenbau.stu.database.generator.values.constraints.logical.GreaterEqualConstraint;
import com.seitenbau.stu.database.generator.values.constraints.logical.NotEqualConstraint;
import com.seitenbau.stu.database.generator.values.constraints.logical.SmallerConstraint;
import com.seitenbau.stu.database.generator.values.constraints.logical.SmallerEqualConstraint;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class ConstraintBuilder {

	private ColumnBuilder columnBuilder;
	
	public ConstraintBuilder(ColumnBuilder columnBuilder) {
		this.columnBuilder = columnBuilder;
	}
	
	public ColumnBuilder range(Value<?> value1, Value<?> value2){
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new RangeConstraint(columnBuilder.getTableColumnString(), value1, value2));
		return columnBuilder;
	}
	
	public ColumnBuilder range(String value1, Value<?> value2){
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new RangeConstraint(columnBuilder.getTableColumnString(), value1, value2));
		return columnBuilder;
	}
	
	public ColumnBuilder range(Value<?> value1, String value2){
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new RangeConstraint(columnBuilder.getTableColumnString(), value1, value2));
		return columnBuilder;
	}
	
	public ColumnBuilder range(String value1, String value2){
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new RangeConstraint(columnBuilder.getTableColumnString(), value1, value2));
		return columnBuilder;
	}	
	
	public ColumnBuilder unique(){
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new UniqueConstraint(columnBuilder.getTableColumnString()));
		return columnBuilder;
	}

	public ColumnBuilder domain(String string) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new DomainConstraint(columnBuilder.getTableColumnString(), string));
		return columnBuilder;
	}

	public ColumnBuilder greater(String string) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new GreaterConstraint(columnBuilder.getTableColumnString(), string));
		return columnBuilder;
	}
	
	public ColumnBuilder greater(Value<?> value) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new GreaterConstraint(columnBuilder.getTableColumnString(), value));
		return columnBuilder;
	}
	
	public ColumnBuilder smaller(String string) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new SmallerConstraint(columnBuilder.getTableColumnString(), string));
		return columnBuilder;
	}
	
	public ColumnBuilder smaller(Value<?> value) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new SmallerConstraint(columnBuilder.getTableColumnString(), value));
		return columnBuilder;
	}
	
	public ColumnBuilder greaterEqual(String string) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new GreaterEqualConstraint(columnBuilder.getTableColumnString(), string));
		return columnBuilder;
	}
	
	public ColumnBuilder greaterEqual(Value<?> value) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new GreaterEqualConstraint(columnBuilder.getTableColumnString(), value));
		return columnBuilder;
	}
	
	public ColumnBuilder smallerEqual(String string) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new SmallerEqualConstraint(columnBuilder.getTableColumnString(), string));
		return columnBuilder;
	}
	
	public ColumnBuilder smallerEqual(Value<?> value) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new SmallerEqualConstraint(columnBuilder.getTableColumnString(), value));
		return columnBuilder;
	}
	
	public ColumnBuilder equal(String string) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new EqualConstraint(columnBuilder.getTableColumnString(), string));
		return columnBuilder;
	}
	
	public ColumnBuilder equal(Value<?> value) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new EqualConstraint(columnBuilder.getTableColumnString(), value));
		return columnBuilder;
	}
	
	public ColumnBuilder notEqual(String string) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new NotEqualConstraint(columnBuilder.getTableColumnString(), string));
		return columnBuilder;
	}
	
	public ColumnBuilder notEqual(Value<?> value) {
		columnBuilder.getTableBuilder().getDatabaseModel().constraint(new NotEqualConstraint(columnBuilder.getTableColumnString(), value));
		return columnBuilder;
	}

	public ColumnBuilder count(String string) {
		// TODO Auto-generated method stub
		return columnBuilder;
	}
}
