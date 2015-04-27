package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;
import java.util.HashMap;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.DomainSpecificDataHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.ValueGenerator;

public class DomainSpecificDataConstraint extends ConstraintBase {

	// Data
	private HashMap<String, ArrayList<DomainSpecificDataHint>> data = new HashMap<String, ArrayList<DomainSpecificDataHint>>();

	public DomainSpecificDataConstraint(String column1, String column2) {
		this.modelRef = column1;
		this.sourceNames = new String[] {column1, column2};
		
		this.scope = Scope.Entity;
	}
	
	@Override
	public boolean isValid(EntityBlueprint eb) {
		
		//TODO: DataSource über diesen Weg besorgen: this.fab.model.dataSource		
		Result value1 = sources.get(0).getResults().get(0);
		Result value2 = sources.get(1).getResults().get(0);		
		
		if(value1.getValue() == null || value2.getValue() == null){
			return false;
		}		
		
		String key1 = value1.getGenerator().getKey();
		String key2 = value2.getGenerator().getKey();
		
		Object v1 = value1.getValue();
		Object v2 = value2.getValue();
		
		return fab.model.getDataSource().isValid(key1, (Comparable<?>)v1, key2, (Comparable<?>)v2);
	}

	public HashMap<String, ArrayList<DomainSpecificDataHint>> getData() {
		return data;
	}

	public void setData(HashMap<String, ArrayList<DomainSpecificDataHint>> data) {
		this.data = data;
	}
	
	@Override
	public ConstraintBase getCopyInstance(){	
		DomainSpecificDataConstraint ec = new DomainSpecificDataConstraint (sourceNames[0], sourceNames[1]);
		ec.fab = fab;		
		return ec;
	}

	@Override
	public Hint getHint(ValueGenerator generator, Comparable<?> value) {
		return new DomainSpecificDataHint(this, generator.getKey(), value);
	}
}
