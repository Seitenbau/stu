package com.seitenbau.stu.database.generator.values.constraints.functional;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.EqualHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.constraints.FunctionalConstraint;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class SubConstraint extends FunctionalConstraint {	

	public SubConstraint(String sourceName1, String sourceName2, String resultSource) {
		super(sourceName1, sourceName2, resultSource);
	}

	public SubConstraint(String sourceName1, String sourceName2, Value<?> value) {
		super(sourceName1, sourceName2, value);
	}

	public SubConstraint(String sourceName1, Value<?> value, String resultSource) {
		super(sourceName1, value, resultSource);
	}

	public SubConstraint(Value<?> value, String sourceName1, String resultSource) {
		super(value, sourceName1, resultSource);
	}

	public SubConstraint(String sourceName1, Value<?> value1, Value<?> value2) {
		super(sourceName1, value1, value2);
	}

	public SubConstraint(Value<?> value1, String sourceName1, Value<?> value2) {
		super(value1, sourceName1, value2);
	}

	public SubConstraint(Value<?> value1, Value<?> value2, String resultSource) {
		super(value1, value2, resultSource);
	}

	public boolean isValid(EntityBlueprint eb) {
		
		Value<?>[] values = resolveValues();

		try {
			return values[0].sub(values[1]).equals(values[2]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public ArrayList<Hint> getHint(Result result) {
		ArrayList<Hint> hints = new ArrayList<Hint>();
		Value<?>[] values = resolveValues();

		int resultPos = getResultPos(result);
		
		// 0 - 1 = 2
		
		
		// 0 = 2 + 1
		if(resultPos == 0){	 		
			if (values[1] != null && values[2] != null) {
				EqualHint eqHint = new EqualHint(this);
				eqHint.setValue(values[2].add(values[1]));
				hints.add(eqHint);
			}	
		// 1 = -(2 + 0)	
		}else if(resultPos == 1){ 
			if (values[0] != null && values[2] != null) {
				EqualHint eqHint = new EqualHint(this);
				eqHint.setValue((values[2].add(values[0])).neg());
				hints.add(eqHint);
			}
		// 2 = 0 - 1
		}else if(resultPos == 2){	
			if (values[0] != null && values[1] != null) {
				EqualHint eqHint = new EqualHint(this);
				eqHint.setValue(values[0].sub(values[1]));
				hints.add(eqHint);
			}
		}
		

		return hints;
	}
}
