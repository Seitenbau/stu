package com.seitenbau.stu.database.generator.values.constraints;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.data.EntityFactory;

public abstract class ConstraintInterface {	
	public String sourceRef; // "table.column"

	
	protected EntityFactory fab;
	
	public EntityFactory getFab() {
		return fab;
	}

	public void setFab(EntityFactory fab) {
		this.fab = fab;
	}
	
	public abstract boolean isValid(Comparable<?> value, EntityBlueprint eb);
	public abstract boolean loadValues(EntityBlueprint eb);
	public abstract boolean loadTargets(EntityBlueprint eb);
}
