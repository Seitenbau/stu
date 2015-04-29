package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.Random;

import com.seitenbau.stu.database.generator.hints.EqualHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.hints.NotEqualHint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public abstract class ValueGenerator {
	public Random random; // TODO private
	protected ArrayList<ConstraintBase> scs;
	private String key;
	protected String[] set;
	protected boolean allowNull;
	protected ArrayList<Hint> hints = new ArrayList<Hint>();
	
	
	protected String[] values;				// TODO: Change to list of Comparable; List of possible result values
	protected Value<?> returnValue;	// TODO: Maybe change to result? If this value is set, the result value has to be this value
	protected ArrayList<Comparable<?>> notAllowedValues = new ArrayList<Comparable<?>>(); // List of values that can't be set

	// TODO: Manage possible ranges
	// TODO: Additional...
	
	public abstract Integer getMaxIndex();

	public abstract Result nextValue();

	public abstract Result nextValue(Integer index);

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void addHint(Hint hint) {
		hints.add(hint);
	}

	public ArrayList<Hint> getHints() {
		return hints;
	}

	public void initialize(long seed) {
		setRandom(new Random(seed));
	}

	public void setConstraints(ArrayList<ConstraintBase> arrayList) {
		this.scs = arrayList;
	}

	public void setSet(String[] set) {
		this.set = set;
	}

	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public void walkthroughHints() {
		for (Hint hint : getHints()) {
			if (EqualHint.class.isInstance(hint)) {
				Value<?> cv = hint.getValue();
				if (cv != null)
					returnValue = cv;
			}
			
			if(NotEqualHint.class.isInstance(hint)){
				Comparable<?> cv = hint.getValue();
				if(cv != null){
					if(!notAllowedValues.contains(cv))
						notAllowedValues.add(cv);
				}
			}
		}
	}

	public void clearHints() {
		hints.clear();
	}
}
