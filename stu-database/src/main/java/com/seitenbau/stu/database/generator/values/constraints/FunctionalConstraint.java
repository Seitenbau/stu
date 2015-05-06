package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;

public class FunctionalConstraint extends ConstraintBase {

	@Override
	public boolean isValid(EntityBlueprint eb) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ConstraintBase getCopyInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Hint> getHint(Result result) {
		return new ArrayList<Hint>();
	}
}
