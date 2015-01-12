package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintsData;
import com.seitenbau.stu.database.generator.values.constraints.DataConstraint;

public abstract class ValueGenerator {
	protected Random random;
	protected ArrayList<ConstraintPair> constraintPairs = new ArrayList<ConstraintPair>();	
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void initialize(long seed) {
		random = new Random();
	}

	public abstract String nextValue();

	public void addConstraint(ConstraintPair constraintPair) {
		constraintPairs.add(constraintPair);
	}

	public void clearConstraints() {
		constraintPairs.clear();
	}
}
