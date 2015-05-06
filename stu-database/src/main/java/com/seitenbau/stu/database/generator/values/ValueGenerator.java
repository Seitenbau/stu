package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.Random;

import com.seitenbau.stu.database.generator.hints.EqualHint;
import com.seitenbau.stu.database.generator.hints.GreaterEqualHint;
import com.seitenbau.stu.database.generator.hints.GreaterHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.hints.NotEqualHint;
import com.seitenbau.stu.database.generator.hints.RangeHint;
import com.seitenbau.stu.database.generator.hints.SmallerEqualHint;
import com.seitenbau.stu.database.generator.hints.SmallerHint;
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
	protected ArrayList<Value<?>> notAllowedValues = new ArrayList<Value<?>>(); // List of values that can't be set

	protected Value<?> upperLimit;
	protected Value<?> lowerLimit;
	
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
			
			if (EqualHint.class.isInstance(hint))
				handleHint((EqualHint) hint);
			
			if(NotEqualHint.class.isInstance(hint))
				handleHint((NotEqualHint) hint);
			
			if(GreaterHint.class.isInstance(hint))
				handleHint((GreaterHint) hint);
			
			if(SmallerHint.class.isInstance(hint))
				handleHint((SmallerHint) hint);
			
			if(GreaterEqualHint.class.isInstance(hint))
				handleHint((GreaterEqualHint) hint);
			
			if(SmallerEqualHint.class.isInstance(hint))
				handleHint((SmallerEqualHint) hint);
			
			if(RangeHint.class.isInstance(hint))
				handleHint((RangeHint) hint);
		}
	}
	
	protected void handleHint(EqualHint hint){
		Value<?> cv = hint.getValue();
		if (cv != null)
			returnValue = cv;
	}
	
	protected void handleHint(NotEqualHint hint){
		Value<?> cv = hint.getValue();
		if(cv != null){
			if(!notAllowedValues.contains(cv))
				notAllowedValues.add(cv);
		}
	}
	
	protected void handleHint(GreaterHint hint){
		Value<?> cv = hint.getValue();
		if(cv != null){
			try {
				// TODO
				if(lowerLimit == null || lowerLimit.compareTo(cv) < 0)
					lowerLimit = cv;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void handleHint(SmallerHint hint){
		Value<?> cv = hint.getValue();
		if(cv != null){
			try {
				// TODO
				if(upperLimit == null || upperLimit.compareTo(cv) < 0)
					upperLimit = cv;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void handleHint(GreaterEqualHint hint){
		Value<?> cv = hint.getValue();
		if(cv != null){
			try {
				if(lowerLimit == null || lowerLimit.compareTo(cv) < 0)
					lowerLimit = cv;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void handleHint(SmallerEqualHint hint){
		Value<?> cv = hint.getValue();
		if(cv != null){
			try {
				if(upperLimit == null || upperLimit.compareTo(cv) < 0)
					upperLimit = cv;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void handleHint(RangeHint hint){
		Value<?> lowerL = hint.getLowerLimit();
		Value<?> upperL = hint.getUpperLimit();
		
		if(lowerL != null){
			try {
				if(lowerLimit == null || lowerL.compareTo(lowerLimit) > 0)
					lowerLimit = lowerL;	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(upperL != null){
			try {
				if(upperLimit == null || upperL.compareTo(upperLimit) < 0)
					upperLimit = upperL;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void clearHints() {
		hints.clear();
	}
}
