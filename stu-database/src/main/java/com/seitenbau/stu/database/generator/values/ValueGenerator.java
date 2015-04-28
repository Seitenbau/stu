package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.Random;

import org.apache.tools.ant.types.resources.selectors.Compare;

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
	protected Comparable<?> returnValue;
	
	public abstract Integer getMaxIndex();
	public abstract Result nextValue();
	public abstract Result nextValue(Integer index);
	
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
		for(Hint hint: getHints()){
			if(Hint.class.isInstance(hint)){
				returnValue = hint.getValue();
			}
		}
	}

	public void clearHints() {
		hints.clear();		
	}
}
