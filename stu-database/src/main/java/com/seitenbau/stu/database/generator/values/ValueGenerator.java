package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.Random;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintInterface;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;
import com.seitenbau.stu.database.generator.values.constraints.ExpressionConstraint;

public abstract class ValueGenerator {
	public Random random;
	protected ArrayList<ConstraintPair> constraintPairs = new ArrayList<ConstraintPair>();
	protected ArrayList<ConstraintInterface> scs;
	private String key;
	protected String[] set;
	protected boolean allowNull;
	
	protected String[] values;
	
	public abstract Integer getMaxIndex();	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void initialize(long seed) {
		random = new Random(seed);
	}

	public abstract Result nextValue(EntityBlueprint eb);
	public abstract Result nextValue(Integer index, EntityBlueprint eb);
	public abstract Result nextValue();
	
	public Result nextValue(Result result) {
		if (result == null) {
			result = new Result(null, false);
			result.setValueIndex(0);		
			
			if(values != null)
				result.setMaxIndex(values.length-1);
			else
				result.setMaxIndex(1000);
		} else {
			result.nextValueIndex();
		}

		if(values != null)
			result.setValue(values[result.getValueIndex()]);
		else
			result.setValue(null);
		
		result.setGenerated(true);

		return result;
	}

	protected boolean allTargetsLoaded(EntityBlueprint eb) {
		if (scs != null) {
			for (ConstraintInterface sc : scs) {
				if (!sc.loadSources(eb))
					return false;
			}
		}
		return true;
	}

	// Walk trough all constraints and check if all the targets of each
	// constraint contains values
	protected boolean checkValues(EntityBlueprint eb) {
		if (scs != null) {
			for (ConstraintInterface sc : scs) {
				if (!sc.loadValues(eb))
					return false; // false;
			}
		}
		return true;
	}

	protected boolean checkSuperConstraints(Comparable value, EntityBlueprint eb) {
		if (scs != null) {
			for (ConstraintInterface sc : scs) {
				if (!sc.isValid(value, eb))
					return true;
			}
		}
		return false;
	}

	protected boolean checkConstraints(Comparable value) {
		for (ConstraintPair cp : constraintPairs) {
			if (cp.getDependingConstraint().getValue() != null) {

				if (cp.getDependingConstraint().isValid(value)) {
					return true;
				}
			}
		}
		return false;
	}

	protected void setConstraintValues(Comparable value) {
		for (ConstraintPair cp : constraintPairs) {
			cp.getMyConstraint().setValue(value);
		}
	}

	public void setConstraints(ArrayList<ConstraintInterface> arrayList) {
		this.scs = arrayList;
	}

	public void addConstraint(ConstraintPair constraintPair) {
		constraintPairs.add(constraintPair);
	}

	public void clearConstraints() {
		constraintPairs.clear();
	}

	public void setSet(String[] set) {
		this.set = set;
	}
	
	public void setAllowNull(boolean allowNull){
		this.allowNull = allowNull;
	}
}
