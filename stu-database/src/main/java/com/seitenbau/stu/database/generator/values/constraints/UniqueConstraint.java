package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;

public class UniqueConstraint extends Constraint {

	protected ArrayList<Object> values = new ArrayList<Object>();
	
	public ArrayList<Object> getValues() {
		return values;
	}

	public void setValues(ArrayList<Object> values) {
		this.values = values;
	}	
	
	@Override
	public Constraint appliesTo(Constraint constraint) {
		// TODO Auto-generated method stub
		return null;
	}

}
