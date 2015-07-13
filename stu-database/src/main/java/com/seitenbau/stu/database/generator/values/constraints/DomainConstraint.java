package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;
import java.util.HashMap;

import com.seitenbau.stu.database.generator.hints.DomainDataHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;

public class DomainConstraint extends ConstraintBase {

	// Data
	private HashMap<String, ArrayList<DomainDataHint>> data = new HashMap<String, ArrayList<DomainDataHint>>();

	public DomainConstraint(String column1, String column2) {
		this.modelRef = column1;
		this.sourceNames = new String[] { column1, column2 };

		this.scope = Scope.Cell;
		this.priory = 3;
	}

	@Override
	public boolean isValid() {

		Result value1 = sources.get(0).getResults().get(0);
		Result value2 = sources.get(1).getResults().get(0);

		if (value1.getValue() == null || value2.getValue() == null) {
			return false;
		}

		String key1 = value1.getGenerator().getKey();
		String key2 = value2.getGenerator().getKey();

		Object v1 = value1.getValue();
		Object v2 = value2.getValue();

		return fab.model.getDataSource().isValid(key1, (Comparable<?>) v1, key2, (Comparable<?>) v2);
	}

	public HashMap<String, ArrayList<DomainDataHint>> getData() {
		return data;
	}

	public void setData(HashMap<String, ArrayList<DomainDataHint>> data) {
		this.data = data;
	}

	@Override
	public ConstraintBase getCopyInstance() {
		DomainConstraint ec = new DomainConstraint(sourceNames[0], sourceNames[1]);
		ec.setScope(Scope.Cell);
		ec.fab = fab;
		return ec;
	}

	@Override
	public ArrayList<Hint> getHints(Result result) {
		ArrayList<Hint> hints = new ArrayList<Hint>();
		if (result.getValue() != null) {
			hints.add(new DomainDataHint(this, result.getGenerator().getKey(), result.getValue()));
		}
		return hints;
	}
}
