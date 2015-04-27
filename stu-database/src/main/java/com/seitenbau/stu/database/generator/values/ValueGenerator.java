package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.Random;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;

public abstract class ValueGenerator {
	public Random random; // TODO private
	protected ArrayList<ConstraintBase> scs;
	private String key;
	protected String[] set;
	protected boolean allowNull;
	protected ArrayList<Hint> hints = new ArrayList<Hint>();
	
	protected String[] values;
	
	public abstract Integer getMaxIndex();	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public void addHint(Hint hint){
		hints.add(hint);
	}
	
	public ArrayList<Hint> getHints(){
		return hints;
	}

	public void initialize(long seed) {
		setRandom(new Random(seed));
	}

	public abstract Result nextValue(EntityBlueprint eb);
	public abstract Result nextValue(Integer index, EntityBlueprint eb);
	public abstract Result nextValue();
	public abstract Result nextValue(Integer index);
	
	public Result nextValue(Result result) {
		// TODO: Remove
//		if (result == null) {		
//			result = new Result(null, false);
//			result.setValueIndex(0);		
//			
//			if(values != null)
//				result.setMaxIndex(values.length-1);
//			else
//				result.setMaxIndex(1000);
//		} else {
//			result.nextValueIndex();
//		}
//
//		if(values != null)
//			result.setValue(values[result.getValueIndex()]);
//		else
//			result.setValue(null);
//		
//		result.setGenerated(true);

		return result;
	}

	protected boolean allTargetsLoaded(EntityBlueprint eb) {
		if (scs != null) {
			for (ConstraintBase sc : scs) {
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
			for (ConstraintBase sc : scs) {
//				if (!sc.loadValues(eb))
//					return false; // false;
			}
		}
		return true;
	}

	protected boolean checkSuperConstraints(Comparable<?> value, EntityBlueprint eb) {
		if (scs != null) {
			for (ConstraintBase sc : scs) {
//				if (!sc.isValid(value, eb))
//					return true;
			}
		}
		return false;
	}

	public void setConstraints(ArrayList<ConstraintBase> arrayList) {
		this.scs = arrayList;
	}

	public void setSet(String[] set) {
		this.set = set;
	}
	
	public void setAllowNull(boolean allowNull){
		this.allowNull = allowNull;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public void walkthroughHints() {
		// TODO Auto-generated method stub
		
	}

	public void clearHints() {
		hints.clear();		
	}
}
