package com.seitenbau.stu.database.generator.values.constraints.logicalconstraints;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.GreaterHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.constraints.CompareConstraint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.constraints.Source;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class GreaterConstraint extends CompareConstraint {

	public GreaterConstraint(String sourceName1, String sourceName2) {
		super(sourceName1, sourceName2);
	}

	public GreaterConstraint(String sourceName, Value<?> value) {
		super(sourceName, value);
	}

	public GreaterConstraint(Value<?> value, String sourceName) {
		super(value, sourceName);
	}

	@Override
	public boolean isValid(EntityBlueprint eb) {

		try {
			if (sourceNames.length == 2)
				return getSources().get(0).getResults().get(0).getValue().compareTo(getSources().get(1).getResults().get(0).getValue()) > 0;

			return getSources().get(0).getResults().get(0).getValue().compareTo(getValue()) > 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public ConstraintBase getCopyInstance() {
		GreaterConstraint eqConstraint;
		if (sourceNames.length == 2)
			eqConstraint = new GreaterConstraint(sourceNames[0], sourceNames[1]);
		else
			eqConstraint = new GreaterConstraint(getValue(), sourceNames[0]);

		eqConstraint.fab = this.fab;

		return eqConstraint;
	}

	@Override
	public Hint getHint(Result result) {
		if (this.getValue() != null) {
			GreaterHint hint = new GreaterHint(this);
			hint.setValue(this.getValue());
			return hint;
		} else {
			for (Source source : sources) {
				for (Result r : source.getResults()) {
					if (r != result) {
						if (r.isGenerated()) {
							GreaterHint hint = new GreaterHint(this);
							hint.setValue(r.getValue());
							return hint;
						}
					}
				}
			}
		}

		return null;
	}
}
