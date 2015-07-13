package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.hints.NotEqualHint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class UniqueConstraint extends ConstraintBase {

	public UniqueConstraint(String... modelRef) {
		this.modelRef = modelRef[0];
		this.scope = Scope.Column;
		this.sourceNames = modelRef;
		this.priory = 2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid() {
		ArrayList<Value<?>> values = new ArrayList<Value<?>>();

		for (Source source : sources) {
			for (Result result : source.getResults()) {
				if (result.getValue() == null)
					return false;
				
				if(values.contains(result.getValue()))
					return false;
				else
					values.add(result.getValue());
			}
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConstraintBase getCopyInstance() {
		UniqueConstraint newInstance = new UniqueConstraint(sourceNames);
		newInstance.fab = fab;
		newInstance.setScope(this.scope);
		newInstance.priory = this.priory;
		return newInstance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override	
	public ArrayList<Hint> getHints(Result result) {

		ArrayList<Hint> hints = new ArrayList<Hint>();

		for (Source source : sources) {
			for (Result r : source.getResults()) {
				if (r.getValue() == null || result == r)
					continue;

				NotEqualHint hint = new NotEqualHint(this);
				hint.setSourceName(r.getSourceName());
				hint.setValue(r.getValue());
				hints.add(hint);
			}
		}

		return hints;
	}
}
