package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.HashMap;

import com.seitenbau.stu.database.generator.hints.DomainDataHint;
import com.seitenbau.stu.database.generator.values.valuetypes.StringValue;

public abstract class DomainData {

	public HashMap<String, ArrayList<DomainDataHint>> data = new HashMap<String, ArrayList<DomainDataHint>>();

	public void combine2Constraints(DomainDataHint c1,
			DomainDataHint c2) {
		if (combine(c1, c2))
			combine(c2, c1);
	}

	private boolean combine(DomainDataHint c1, DomainDataHint c2) {
		if (!data.containsKey(c1.getKey()) || !data.containsKey(c2.getKey()))
			return false;

		int index = data.get(c1.getKey()).indexOf(c1);
		if (index < 0) {
			return false;
		} else {
			if (data.get(c1.getKey()).get(index).dataHashMap.containsKey(c2
					.getKey())) {
				data.get(c1.getKey()).get(index).dataHashMap.get(c2.getKey())
						.add(c2);
			} else {
				ArrayList<DomainDataHint> al = new ArrayList<DomainDataHint>();
				al.add(c2);
				data.get(c1.getKey()).get(index).dataHashMap.put(c2.getKey(),
						al);
			}
			return true;
		}
	}

	public DomainDataHint addData(String key, String value) {
		DomainDataHint domainDataHint = new DomainDataHint(
				null, key, new StringValue(value));

		if (!data.containsKey(key)) {
			ArrayList<DomainDataHint> list = new ArrayList<DomainDataHint>();
			list.add(domainDataHint);
			data.put(key, list);
		} else {
			data.get(key).add(domainDataHint);
		}

		return domainDataHint;
	}

	public boolean isValid(String key1, Comparable<?> value1, String key2,
			Comparable<?> value2) {
		if (!data.containsKey(key1) || !data.containsKey(key2))
			return false;

		if (String.class.isInstance(value1)) {
			value1 = ((String) value1).replaceAll("\"", "");
		}

		if (String.class.isInstance(value2)) {
			value2 = ((String) value2).replaceAll("\"", "");
		}

		ArrayList<DomainDataHint> list = data.get(key1);
		for (DomainDataHint entry : list) {
			if (entry.getValue().equals(value1)) {
				if (entry.dataHashMap.containsKey(key2)) {
					for (DomainDataHint e2 : entry.dataHashMap
							.get(key2)) {
						if (e2.getValue().equals(value2))
							return true;
					}
					return false;
				} else {
					return false;
				}
			}
		}

		return false;
	}

	public DomainData() {
		initData();
	}

	protected abstract void initData();
}
