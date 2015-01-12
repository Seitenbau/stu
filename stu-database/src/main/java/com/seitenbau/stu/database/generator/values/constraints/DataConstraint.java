package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class DataConstraint extends Constraint {
	protected String key;
	public HashMap<String, ArrayList<DataConstraint>> constraints = new HashMap<String, ArrayList<DataConstraint>>();

	public DataConstraint() {
		super();
	}

	public DataConstraint(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public DataConstraint(String key, Comparable value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public Constraint appliesTo(Constraint constraint) {
		DataConstraint dc = (DataConstraint) constraint;

		Iterator<Entry<String, ArrayList<DataConstraint>>> it = constraints.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			if (pairs.getKey() == dc.getKey()) {

				ArrayList<DataConstraint> al = (ArrayList<DataConstraint>) pairs.getValue();

				if (al.contains(constraint))
					return constraint;
			}
		}

		return null;
	}

	@Override
	public boolean equals(Object object) {
		boolean sameSame = false;

		if (object != null && object instanceof DataConstraint) {
			sameSame = this.value == ((DataConstraint) object).value;
		}

		return sameSame;
	}
}
