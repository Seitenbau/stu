package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.hints.RangeHint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.valuetypes.IntValue;

public class RangeConstraint extends ConstraintBase {

	private Integer min;
	private Integer max;

	public RangeConstraint(String column, Integer min, Integer max) {
		this.modelRef = column;
		this.setMin(min);
		this.setMax(max);
		
		this.sourceNames = new String[]{column};		
		this.scope = Scope.Cell;
		this.priory = 1;
	}

	@Override
	public boolean isValid(EntityBlueprint eb) {

		Result result = sources.get(0).getResults().get(0);
		// TODO: All types
		if(result == null || result.getValue() == null || result.getValue().getValue() == null)
			return false;
		
		Integer value = (Integer) result.getValue().getValue();

		if (value >= min && value <= max) {
			return true;
		}
		
		return false;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	@Override
	public ConstraintBase getCopyInstance(){	
		RangeConstraint newInstance = new RangeConstraint(modelRef, min, max);
		newInstance.fab = fab;
		newInstance.setScope(this.scope);
		return newInstance;
	}

	@Override
	public ArrayList<Hint> getHint(Result result) {
		ArrayList<Hint> hints = new ArrayList<Hint>();
		hints.add(new RangeHint(this, new IntValue(max), new IntValue(min)));
		return hints;
	}
}
