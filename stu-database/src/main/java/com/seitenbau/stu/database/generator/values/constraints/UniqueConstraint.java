package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.ValueGenerator;

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
	public boolean isValid(EntityBlueprint eb) {
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
	public ConstraintBase getCopyInstance(){	
		UniqueConstraint newInstance = new UniqueConstraint(sourceNames);
		newInstance.fab = fab;
		newInstance.setScope(this.scope);
		return newInstance;
	}

	@Override
	public Hint getHint(ValueGenerator generator, Comparable<?> value) {
		// TODO Auto-generated method stub
		return null;
	}
}
