package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.hints.RangeHint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

// TODO: Remove RangeConstraint, because Range == GreaterEqual + SmallerEqual
public class RangeConstraint extends ConstraintBase {

	private Value<?> min;
	private Value<?> max;
	
	public RangeConstraint(String sourceName1, String sourceName2, String sourceName3) {
		this.scope = Scope.Cell;
		this.priory = 5;
		this.mode = 1;
		this.modelRef = sourceName1;
		this.sourceNames = new String[] { sourceName1, sourceName2, sourceName3 };		
	}
	
	public RangeConstraint(String sourceName1, String sourceName2, Value<?> value) {
		this.scope = Scope.Cell;
		this.priory = 3;
		this.mode = 2;
		this.modelRef = sourceName1;
		this.sourceNames = new String[] { sourceName1, sourceName2 };
		this.max = value;
	}

	public RangeConstraint(String sourceName1, Value<?> value, String sourceName2) {
		this.priory = 3;
		this.scope = Scope.Cell;
		this.mode = 3;
		this.modelRef = sourceName1;
		this.sourceNames = new String[] { sourceName1, sourceName2 };
		this.min = value;
	}

	public RangeConstraint(String sourceName, Value<?> value1, Value<?> value2) {
		this.priory = 1;
		this.scope = Scope.Cell;
		this.mode = 4;
		this.modelRef = sourceName;
		this.sourceNames = new String[] { sourceName };
		this.min = value1;
		this.max = value2;
	}

	@Override
	public boolean isValid() {

		Result result = sources.get(0).getResults().get(0);
		// TODO: All types
		if(result == null || result.getValue() == null || result.getValue().getValue() == null)
			return false;
		
		Value<?> value = result.getValue();

		try {
			if(value.compareTo(min) >= 0 && value.compareTo(max) <= 0)
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return false;
	}

	@Override
	public ConstraintBase getCopyInstance(){	
		RangeConstraint newInstance = new RangeConstraint(modelRef, min, max);
		newInstance.fab = fab;
		newInstance.setScope(this.scope);
		return newInstance;
	}

	@Override
	public ArrayList<Hint> getHints(Result result) {
		ArrayList<Hint> hints = new ArrayList<Hint>();
		hints.add(new RangeHint(this, min, max));
		return hints;
	}
}
