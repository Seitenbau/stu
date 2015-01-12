package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintsData;
import com.seitenbau.stu.database.generator.values.constraints.DataConstraint;
import com.seitenbau.stu.database.generator.values.constraints.UniqueConstraint;

public class DataGenerator extends ValueGenerator {

	private ConstraintsData ConstraintsData;

	public ConstraintsData getConstraintsData() {
		return ConstraintsData;
	}

	public void setConstraintsData(ConstraintsData constraintsData) {
		ConstraintsData = constraintsData;
	}

	public DataGenerator(String string) {
		setKey(string);
	}

	public DataGenerator() {
	}

	@Override
	public String nextValue() {

		ArrayList<DataConstraint> al = ConstraintsData.data.get(getKey());

		if (constraintPairs == null) {
			DataConstraint c = al.get(random.nextInt(al.size()));
			return c.getValue().toString();
		} else {
			ConstraintsData cd = new ConstraintsData();

			reduceList(cd);

			al = cd.data.get(getKey());
			if (al.size() > 0) {
				int randInt = random.nextInt(al.size());
				System.out.println(getKey() + ": " + randInt + " aus " + al.size());
				Object value = al.get(randInt).getValue();
				if (value == null)
					return "";
				for (ConstraintPair cp : constraintPairs) {
					if(DataConstraint.class.isInstance(cp.getMyConstraint())){
						((DataConstraint) cp.getMyConstraint()).setValue(value);
					}else{
						((UniqueConstraint) cp.getMyConstraint()).getValues().add(value);
					}
				}
				return value.toString();
			}

			return null;
		}
	}

	private void reduceList(ConstraintsData cd) {
		// Remove not valid

		// Data Constraints durchlaufen
		for (ConstraintPair cp : constraintPairs) {
			if (UniqueConstraint.class.isInstance(cp.getMyConstraint())) {
				UniqueConstraint uc = (UniqueConstraint) cp.getMyConstraint();

				Iterator<Entry<String, ArrayList<DataConstraint>>> it = cd.data.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry) it.next();
					if (pairs.getKey() == getKey()) {

						ArrayList<DataConstraint> al = (ArrayList<DataConstraint>) pairs.getValue();

						ArrayList<DataConstraint> intList = new ArrayList<DataConstraint>();
						for (DataConstraint c : al) {
							if (uc.getValues().contains(c.getValue())) {
								intList.add(c);
							}
						}

						for (DataConstraint c : intList) {
							al.remove(c);
						}
					}
				}

			} else {

				DataConstraint myConstraint = (DataConstraint) cp.getMyConstraint();
				DataConstraint dependingConstraint = (DataConstraint) cp.getDependingConstraint();
				if (dependingConstraint.getValue() != null) { // Constraint-Wert vorhanden
					Iterator<Entry<String, ArrayList<DataConstraint>>> it = cd.data.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pairs = (Map.Entry) it.next();
						if (pairs.getKey() == myConstraint.getKey()) {

							ArrayList<DataConstraint> al = (ArrayList<DataConstraint>) pairs.getValue();

							ArrayList<DataConstraint> intList = new ArrayList<DataConstraint>();
							for (DataConstraint c : al) {
								if (c.appliesTo(((DataConstraint) cp.getDependingConstraint())) == null) {
									intList.add(c);
								}
							}

							for (DataConstraint c : intList) {
								al.remove(c);
							}
						}
					}
				}
			}
		}
	}

	public static class Factory implements ValueGeneratorFactory {

		@Override
		public ValueGenerator createGenerator() {
			return new DataGenerator();
		}
	}
}
