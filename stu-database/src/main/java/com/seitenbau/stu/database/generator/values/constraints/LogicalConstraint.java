package com.seitenbau.stu.database.generator.values.constraints;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.ValueGenerator;

public class LogicalConstraint extends ConstraintBase {

	private CompareType compareType;
	private Comparable<?> value;
	
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
	
	public LogicalConstraint(CompareType ct, String... sourceNames){
		this.sourceNames = sourceNames;
		this.compareType = ct;
	}
	
//	public LogicalConstraint(CompareType ct, String sourceName, Comparable<?> value){
//		this.setValue(value);
//		this.sourceNames = new String[]{sourceName};
//		this.compareType = ct;
//	}
//	
//	public LogicalConstraint(CompareType ct, String sourceName, Comparable<?> value){
//		this.setValue(value);
//		this.sourceNames = new String[]{sourceName};
//		this.compareType = ct;
//	}
	
	
	@Override
	public boolean isValid(EntityBlueprint eb) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ConstraintBase getCopyInstance(){	
		LogicalConstraint newInstance = new LogicalConstraint(this.compareType, this.sourceNames);
		newInstance.fab = fab;
		newInstance.setScope(this.scope);
		return newInstance;
	}


	public CompareType getCompareType() {
		return compareType;
	}


	public void setCompareType(CompareType compareType) {
		this.compareType = compareType;
	}


	@Override
	public Hint getHint(Result result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparable<?> getValue() {
		return value;
	}

	public void setValue(Comparable<?> value) {
		this.value = value;
	}
	
	
//	public boolean isValid(Comparable value) {
//
//		switch (getCompareType()) {
//		case EQUAL:
//			return !equal(value);
//		case NOT_EQUAL:
//			return !not_equal(value);
//		case GREATER:
//			return !greater(value);
//		case GREATER_EQUAL:
//			return !greater_equal(value);
//		case SMALLER:
//			return !smaller(value);
//		case SMALLER_EQUAL:
//			return !smaller_equal(value);
//		}
//			return true;
//	}
}
