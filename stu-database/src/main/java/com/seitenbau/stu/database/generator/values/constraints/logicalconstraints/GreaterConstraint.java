package com.seitenbau.stu.database.generator.values.constraints.logicalconstraints;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.GreaterHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.hints.SmallerHint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.constraints.CompareConstraint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.constraints.Source;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class GreaterConstraint extends CompareConstraint {

	/**
	 * The parameter sourceName1 has to be greater than the parameter sourceName2
	 * 
	 * @param sourceName1
	 *            "tableName.columnName"
	 * @param sourceName2
	 *            "tableName.columnName"
	 */
	public GreaterConstraint(String sourceName1, String sourceName2) {
		super(sourceName1, sourceName2);
	}

	/**
	 * The parameter sourceName1 has to be greater than the value
	 * 
	 * @param sourceName
	 * @param value
	 */
	public GreaterConstraint(String sourceName, Value<?> value) {
		super(sourceName, value);
	}

	/**
	 * @param value
	 * @param sourceName
	 */
	public GreaterConstraint(Value<?> value, String sourceName) {
		super(value, sourceName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.seitenbau.stu.database.generator.values.constraints.ConstraintBase#isValid(com.seitenbau.stu.database.generator.data.EntityBlueprint)
	 */
	@Override
	public boolean isValid(EntityBlueprint eb) {

		try {
			if (sourceNames.length == 2)
				return getSources().get(0).getResults().get(0).getValue().compareTo(getSources().get(1).getResults().get(0).getValue()) > 0;

			return getSources().get(0).getResults().get(0).getValue().compareTo(getValue()) > 0;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public ConstraintBase getCopyInstance() {
		GreaterConstraint eqConstraint;
		if (sourceNames.length == 2)
			eqConstraint = new GreaterConstraint(sourceNames[0], sourceNames[1]);
		else
			eqConstraint = new GreaterConstraint(getValue(), sourceNames[0]); // TODO: Direction....

		eqConstraint.fab = this.fab;

		return eqConstraint;
	}

	@Override
	public ArrayList<Hint> getHint(Result result) {
		ArrayList<Hint> hints = new ArrayList<Hint>();

		if (this.getValue() != null) {
			GreaterHint hint = new GreaterHint(this);
			hint.setValue(this.getValue());
			hints.add(hint);
		} else {
			for (Source source : sources) {
				for (Result r : source.getResults()) {
					if (r != result) {
						if (r.isGenerated()) {
							Hint hint;
							if (source.getName().compareTo(sourceNames[0]) != 0) {
								hint = new GreaterHint(this);
								// else
								// hint = new SmallerHint(this);

								hint.setSourceName(source.getName());
								hint.setValue(r.getValue());
								hints.add(hint);
								return hints;
							}
						}
					}
				}
			}
		}

		return hints;
	}
}
