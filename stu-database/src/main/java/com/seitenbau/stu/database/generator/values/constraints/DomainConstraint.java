package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;
import java.util.HashMap;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.values.DomainSpecificData;

public class DomainConstraint extends ConstraintBase {

	// Data
	public HashMap<String, ArrayList<DomainSpecificData>> data = new HashMap<String, ArrayList<DomainSpecificData>>();
	
	@Override
	public boolean isValid(Comparable<?> value, EntityBlueprint eb) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid(EntityBlueprint eb) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadValues(EntityBlueprint eb) {
		// TODO Auto-generated method stub
		return false;
	}

}
