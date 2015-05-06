package com.seitenbau.stu.database.generator.values.constraints.logicalconstraints;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.EqualHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.constraints.CompareConstraint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.constraints.Source;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class EqualConstraint extends CompareConstraint {

	public EqualConstraint(String sourceName1, String sourceName2) {
		super(sourceName1, sourceName2);
	}

	public EqualConstraint(String sourceName, Value<?> value) {
		super(sourceName, value);
	}

	public EqualConstraint(Value<?> value, String sourceName) {
		super(value, sourceName);
	}

	@Override
	public boolean isValid(EntityBlueprint eb) {
		Value<?> v1 = getSources().get(0).getResults().get(0).getValue();
		Value<?> v2 = null;

		if (sourceNames.length == 2)
			v2 = getSources().get(1).getResults().get(0).getValue();
		else
			v2 = getValue();		

		try {
			return v1.compareTo(v2) == 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public ConstraintBase getCopyInstance() {
		EqualConstraint eqConstraint;
		if (sourceNames.length == 2)
			eqConstraint = new EqualConstraint(sourceNames[0], sourceNames[1]);
		else
			eqConstraint = new EqualConstraint(getValue(), sourceNames[0]);

		eqConstraint.fab = this.fab;

		return eqConstraint;
	}

	@Override
	public ArrayList<Hint> getHint(Result result) {
		ArrayList<Hint> hints = new ArrayList<Hint>();
		
		if (this.getValue() != null) {
			EqualHint hint = new EqualHint(this);
			hint.setValue(this.getValue());
			hints.add(hint);
		} else {
			for (Source source : sources) {
				for (Result r : source.getResults()) {
					if (r != result) {
						if (r.isGenerated()) {
							EqualHint hint = new EqualHint(this);
							hint.setValue(r.getValue());
							hints.add(hint);
							return hints;
						}
					}
				}
			}
		}

		return hints;
	}

}
