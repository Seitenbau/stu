package com.seitenbau.stu.database.generator.values.constraints.logical;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.EqualHint;
import com.seitenbau.stu.database.generator.hints.GreaterEqualHint;
import com.seitenbau.stu.database.generator.hints.GreaterHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.hints.SmallerEqualHint;
import com.seitenbau.stu.database.generator.hints.SmallerHint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.constraints.CompareConstraint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.constraints.Source;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class GreaterEqualConstraint extends CompareConstraint {

	public GreaterEqualConstraint(String sourceName1, String sourceName2) {
		super(sourceName1, sourceName2);
	}

	public GreaterEqualConstraint(String sourceName, Value<?> value) {
		super(sourceName, value);
	}

	public GreaterEqualConstraint(Value<?> value, String sourceName) {
		super(value, sourceName);
	}

//	@Override
//	public boolean isValid(EntityBlueprint eb) {
//
//		try {
//			if (sourceNames.length == 2)
//				return getSources().get(0).getResults().get(0).getValue().compareTo(getSources().get(1).getResults().get(0).getValue()) >= 0;
//
//			return getSources().get(0).getResults().get(0).getValue().compareTo(getValue(0)) >= 0;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return false;
//	}

//	@Override
//	public ConstraintBase getCopyInstance() {
//		GreaterEqualConstraint eqConstraint;
//		if (sourceNames.length == 2)
//			eqConstraint = new GreaterEqualConstraint(sourceNames[0], sourceNames[1]);
//		else
//			eqConstraint = new GreaterEqualConstraint(getValue(0), sourceNames[0]);
//
//		eqConstraint.fab = this.fab;
//
//		return eqConstraint;
//	}
//
//	@Override
//	public ArrayList<Hint> getHint(Result result) {
//		ArrayList<Hint> hints = new ArrayList<Hint>();
//
//		// TODO andere Richtung auch...
//		if (getValue(0) != null) {
//			GreaterEqualHint hint = new GreaterEqualHint(this);
//			hint.setValue(this.getValue(0));
//			hints.add(hint);
//		} else {
//			for (Source source : sources) {
//				for (Result r : source.getResults()) {
//					if (r != result) {
//						if (r.isGenerated()) {
//							Hint hint;
//							if (r.getSourceName().compareTo(sourceNames[0]) == 0 && result.getSourceName().compareTo(sourceNames[1]) == 0) {
//								hint = new GreaterEqualHint(this);
//							} else if (r.getSourceName().compareTo(sourceNames[1]) == 0 && result.getSourceName().compareTo(sourceNames[0]) == 0) {
//								hint = new SmallerEqualHint(this);
//							} else{
//								continue;
//							}
//
//							if (r.getValue() != null) {
//								hint.setSourceName(source.getName());
//								hint.setValue(r.getValue());
//								hints.add(hint);
//							}
//						}
//					}
//				}
//			}
//		}
//
//		return hints;
//	}
	
	public boolean isValid(EntityBlueprint eb) {

		Value<?>[] values = resolveValues();

		try {
			return values[0].compareTo(values[1]) >= 0;
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
		
		if(resultPos == 0){			
			if (values[1] != null) {
				GreaterEqualHint eqHint = new GreaterEqualHint(this);
				eqHint.setValue(values[1]);
				hints.add(eqHint);
			}	
		}else if(resultPos == 1){
			if (values[0] != null) {
				SmallerEqualHint eqHint = new SmallerEqualHint(this);
				eqHint.setValue(values[0]);
				hints.add(eqHint);
			}
		}		

		return hints;
	}
}
