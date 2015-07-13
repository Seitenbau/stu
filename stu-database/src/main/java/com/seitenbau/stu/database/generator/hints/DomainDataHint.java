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
public class DomainDataHint extends Hint {
	private String key;

	public HashMap<String, ArrayList<DomainDataHint>> dataHashMap = new HashMap<String, ArrayList<DomainDataHint>>();

	public DomainDataHint(ConstraintBase constraint) {
		super(constraint);
	}

	public DomainDataHint(ConstraintBase constraint, String key) {
		super(constraint);
		this.key = key;
	}

	public DomainDataHint(ConstraintBase constraint, String key, Value<?> value) {
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

	public boolean notAppliesTo(DomainDataHint domainSpecifData) {

		Iterator<Entry<String, ArrayList<DomainDataHint>>> it = dataHashMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, ArrayList<DomainDataHint>> pairs = it.next();
			if (pairs.getKey().equals(domainSpecifData.getKey())) {

				ArrayList<DomainDataHint> al = (ArrayList<DomainDataHint>) pairs.getValue();
				if (!al.contains(domainSpecifData))
					return true;
			}
		}

		return false;
	}

	@Override
	public boolean equals(Object object) {
		boolean sameSame = false;

		if (object != null && object instanceof DomainDataHint) {
			sameSame = getValue() == ((DomainDataHint) object).getValue();
		}

		return sameSame;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName().toString() + ": Key => " + ((getKey() == null) ? "null" : getKey().toString()) + ", Value => "
				+ ((getValue() == null) ? "null" : getValue().toString());
	}
}
