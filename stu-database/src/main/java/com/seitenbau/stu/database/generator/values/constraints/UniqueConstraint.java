package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.values.Result;

public class UniqueConstraint extends ConstraintBase {
	
	public UniqueConstraint(String modelRef){
		this.modelRef = modelRef;
		this.scope = Scope.Column;
		this.sourceNames =  new String[] {this.modelRef};
	}
	
	public UniqueConstraint(String... modelRef){
		this.modelRef = modelRef[0];
		this.scope = Scope.Column;
		this.sourceNames =  modelRef;
	}
	
	@Override
	public boolean isValid(Comparable<?> value, EntityBlueprint eb) {
		ArrayList<Comparable<?>> values = new ArrayList<Comparable<?>>();
		
		for(Source source: sources){
			for(Result result: source.getResults()){
				if(result.getValue() == null)
					continue;
				
				if(!values.contains(result.getValue())){
					values.add((Comparable<?>) result.getValue());
				}else{
					return false;
				}
			}
		}	

		return true;
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
