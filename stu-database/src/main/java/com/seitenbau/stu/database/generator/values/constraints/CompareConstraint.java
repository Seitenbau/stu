package com.seitenbau.stu.database.generator.values.constraints;

import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public abstract class CompareConstraint extends ConstraintBase {

	private Value<?> value;

	public CompareConstraint(){
		this.scope = Scope.Cell;
		this.priory = 5;
	}
	
	public CompareConstraint(String sourceName1, String sourceName2) {
		this.scope = Scope.Cell;
		this.priory = 5;
		this.modelRef = sourceName1;
		this.sourceNames = new String[] { sourceName1, sourceName2 };
	}

	public CompareConstraint(String sourceName, Value<?> value) {
		this.priory = 1;
		this.scope = Scope.Cell;
		this.modelRef = sourceName;
		this.sourceNames = new String[] { sourceName };
		this.value = value;
	}

	public CompareConstraint(Value<?> value, String sourceName) {
		this.priory = 1;
		this.scope = Scope.Cell;
		this.modelRef = sourceName;
		this.sourceNames = new String[] { sourceName };
		this.value = value;
	}

	public Value<?> getValue() {
		return value;
	}

	public void setValue(Value<?> value) {
		this.value = value;
	}

	
	// TODO this.getClass().newInstance(); or in subclasses
//	@Override
//	public ConstraintBase getCopyInstance() {
//		CompareConstraint compareConstraint = null;
//		try {
//			compareConstraint = this.getClass().newInstance();
//
//			compareConstraint.priory = 2;
//			if (sourceNames.length < 2){
//				compareConstraint.setValue(value);
//				compareConstraint.priory = 1;
//			}				
//
//			compareConstraint.sourceNames = sourceNames;
//			compareConstraint.fab = this.fab;
//			
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return compareConstraint;
//	}
}
