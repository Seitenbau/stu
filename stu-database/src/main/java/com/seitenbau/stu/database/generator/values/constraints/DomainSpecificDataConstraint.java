package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;
import java.util.HashMap;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.values.DomainSpecificData;
import com.seitenbau.stu.database.generator.values.Result;

public class DomainSpecificDataConstraint extends ConstraintBase {

	// Data
	private HashMap<String, ArrayList<DomainSpecificData>> data = new HashMap<String, ArrayList<DomainSpecificData>>();

	public DomainSpecificDataConstraint(String column1, String column2) {
		this.modelRef = column1;
		this.sourceNames = new String[] {column1, column2};
		
		this.scope = Scope.Entity;
	}
	
	@Override
	public boolean isValid(Comparable<?> value, EntityBlueprint eb) {
		
		//TODO: DataSource über diesen Weg besorgen: this.fab.model.dataSource		
		Result value1 = sources.get(0).getResults().get(0);
		Result value2 = sources.get(1).getResults().get(0);
		
		if(value1.getValue() == null || value2.getValue() == null){
			return true;
		}		
		
		String key1 = value1.getGenerator().getKey();
		String key2 = value2.getGenerator().getKey();
		
		Object v1 = value1.getValue();
		Object v2 = value2.getValue();
		
		return fab.model.dataSource.isValid(key1, (Comparable<?>)v1, key2, (Comparable<?>)v2);
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

	public HashMap<String, ArrayList<DomainSpecificData>> getData() {
		return data;
	}

	public void setData(HashMap<String, ArrayList<DomainSpecificData>> data) {
		this.data = data;
	}
	
	@Override
	public ConstraintBase getCopyInstance(){	
		DomainSpecificDataConstraint ec = new DomainSpecificDataConstraint (sourceNames[0], sourceNames[1]);
		ec.fab = fab;		
		return ec;
	}
}
