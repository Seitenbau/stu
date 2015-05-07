package com.seitenbau.stu.database.generator.hints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

/*
 * DomainSpecificData 
 */
public class DomainSpecificDataHint extends Hint {
	private String key;

	public HashMap<String, ArrayList<DomainSpecificDataHint>> dataHashMap = new HashMap<String, ArrayList<DomainSpecificDataHint>>();

	public DomainSpecificDataHint(ConstraintBase constraint) {
		super(constraint);
	}

	public DomainSpecificDataHint(ConstraintBase constraint, String key) {
		super(constraint);
		this.key = key;
	}

	public DomainSpecificDataHint(ConstraintBase constraint, String key, Value<?> value) {
		super(constraint);
		this.key = key;
		setValue(value);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean notAppliesTo(DomainSpecificDataHint domainSpecifData) {

		Iterator<Entry<String, ArrayList<DomainSpecificDataHint>>> it = dataHashMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, ArrayList<DomainSpecificDataHint>> pairs = it.next();
			if (pairs.getKey().equals(domainSpecifData.getKey())) {

				ArrayList<DomainSpecificDataHint> al = (ArrayList<DomainSpecificDataHint>) pairs.getValue();
				if (!al.contains(domainSpecifData))
					return true;
			}
		}

		return false;
	}

	@Override
	public boolean equals(Object object) {
		boolean sameSame = false;

		if (object != null && object instanceof DomainSpecificDataHint) {
			sameSame = getValue() == ((DomainSpecificDataHint) object).getValue();
		}

		return sameSame;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName().toString() + ": Key => " + ((getKey() == null) ? "null" : getKey().toString()) + ", Value => "
				+ ((getValue() == null) ? "null" : getValue().toString());
	}
}
