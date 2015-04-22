package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/*
 * DomainSpecificData 
 */
public class DomainSpecificData {
	private String key;
	private Comparable<?> value;
	public HashMap<String, ArrayList<DomainSpecificData>> dataHashMap = new HashMap<String, ArrayList<DomainSpecificData>>();

	public DomainSpecificData() {
		super();
	}

	public DomainSpecificData(String key) {
		this.key = key;
	}
	
	public DomainSpecificData(String key, Comparable<?> value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public void setValue(Comparable<?> value) {
		this.value = value;
	}

	public Comparable<?> getValue() {
		return value;
	}	

	public DomainSpecificData appliesTo(DomainSpecificData domainSpecifData) {

		Iterator<Entry<String, ArrayList<DomainSpecificData>>> it = dataHashMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			if (pairs.getKey().equals(domainSpecifData.getKey())) {

				ArrayList<DomainSpecificData> al = (ArrayList<DomainSpecificData>) pairs.getValue();
				if (al.contains(domainSpecifData))
					return domainSpecifData;
			}
		}

		return null;
	}
	
	@Override
	public boolean equals(Object object) {
		boolean sameSame = false;

		if (object != null && object instanceof DomainSpecificData) {
			sameSame = this.value == ((DomainSpecificData) object).getValue();
		}

		return sameSame;
	}	
}
