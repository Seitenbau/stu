package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
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
	public Result nextValue(){		
		ArrayList<DataConstraint> al = ConstraintsData.data.get(getKey());
		
		Result result = new Result(null, false);
		
		DataConstraint valu = al.get(result.getValueIndex());
		result.setValue("\"" + valu.getValue() + "\"");		
		result.setGenerated(true);

		return result;
	}
	
	public Result nextValue(Result result) {
		
		ArrayList<DataConstraint> al = ConstraintsData.data.get(getKey());
		
		if (result == null) {
			result = new Result(null, false);
			result.setValueIndex(0);
			result.setMaxIndex(al.size()-1);
		} else {
			result.setMaxIndex(al.size()-1);
			result.nextValueIndex();
		}		
		
		DataConstraint valu = al.get(result.getValueIndex());
		result.setValue("\"" + valu.getValue() + "\"");		
		result.setGenerated(true);

		return result;
	}
	
	@Override
	public Result nextValue(EntityBlueprint eb) {

		Result result = new Result(null, false);
		
		if (!allTargetsLoaded(eb))
			return result;
		
		// TODO Check if all targets have values...
		if(!checkValues(eb))
			return result;
	

		ArrayList<DataConstraint> al = ConstraintsData.data.get(getKey());

		Comparable<?> value = null;

		int i = 0;
		do {
			if (al.size() > 0) {
				int randInt = random.nextInt(al.size());
				//System.out.println(getKey() + ": " + randInt + " aus " + al.size());
				value = al.get(randInt).getValue();				
			}

			i++;
		} while (checkSuperConstraints(value, eb) && i < 100);

		if(i > 99){
			return new Result(null, false);
		}else{
			result.setValue("\"" + value + "\"");
		}
		
		return result;

		// if (constraintPairs == null) {
		// DataConstraint c = al.get(random.nextInt(al.size()));
		// return c.getValue().toString();
		// } else {
		// ConstraintsData cd = new ConstraintsData();
		//
		// reduceList(cd);
		//
		// Comparable value = null;
		//
		// do {
		// al = cd.data.get(getKey());
		// if (al.size() > 0) {
		// int randInt = random.nextInt(al.size());
		// System.out.println(getKey() + ": " + randInt + " aus " + al.size());
		// value = al.get(randInt).getValue();
		// if (value == null)
		// return "";
		// }
		// } while (checkConstraints(value));
		//
		//
		// setConstraintValues(value);
		// return value.toString();
		//
		// // al = cd.data.get(getKey());
		// // if (al.size() > 0) {
		// // int randInt = random.nextInt(al.size());
		// // System.out.println(getKey() + ": " + randInt + " aus " +
		// al.size());
		// // Comparable value = al.get(randInt).getValue();
		// // if (value == null)
		// // return "";
		// // for (ConstraintPair cp : constraintPairs) {
		// // if(DataConstraint.class.isInstance(cp.getMyConstraint())){
		// // ((DataConstraint) cp.getMyConstraint()).setValue(value);
		// // }else if(UniqueConstraint.class.isInstance(cp.getMyConstraint())){
		// // ((UniqueConstraint) cp.getMyConstraint()).getValues().add(value);
		// // }
		// // }
		// // return value.toString();
		// // }
		// //
		// // return null;
		// }
	}

	@Override
	public Result nextValue(Integer index, EntityBlueprint eb) {

		Result result = new Result(null, false);
		
		if (!allTargetsLoaded(eb))
			return result;
		
		// TODO Check if all targets have values...
		if(!checkValues(eb))
			return result;
	

		ArrayList<DataConstraint> al = ConstraintsData.data.get(getKey());

		Comparable<?> value = null;

		int i = 0;
		do {
			if (al.size() > 0) {
				int randInt = random.nextInt(al.size());
				//System.out.println(getKey() + ": " + randInt + " aus " + al.size());
				value = al.get(randInt).getValue();				
			}

			i++;
		} while (checkSuperConstraints(value, eb) && i < 100);

		if(i > 99){
			return new Result(null, false);
		}else{
			result.setValue("\"" + value + "\"");
		}
		
		return result;

		// if (constraintPairs == null) {
		// DataConstraint c = al.get(random.nextInt(al.size()));
		// return c.getValue().toString();
		// } else {
		// ConstraintsData cd = new ConstraintsData();
		//
		// reduceList(cd);
		//
		// Comparable value = null;
		//
		// do {
		// al = cd.data.get(getKey());
		// if (al.size() > 0) {
		// int randInt = random.nextInt(al.size());
		// System.out.println(getKey() + ": " + randInt + " aus " + al.size());
		// value = al.get(randInt).getValue();
		// if (value == null)
		// return "";
		// }
		// } while (checkConstraints(value));
		//
		//
		// setConstraintValues(value);
		// return value.toString();
		//
		// // al = cd.data.get(getKey());
		// // if (al.size() > 0) {
		// // int randInt = random.nextInt(al.size());
		// // System.out.println(getKey() + ": " + randInt + " aus " +
		// al.size());
		// // Comparable value = al.get(randInt).getValue();
		// // if (value == null)
		// // return "";
		// // for (ConstraintPair cp : constraintPairs) {
		// // if(DataConstraint.class.isInstance(cp.getMyConstraint())){
		// // ((DataConstraint) cp.getMyConstraint()).setValue(value);
		// // }else if(UniqueConstraint.class.isInstance(cp.getMyConstraint())){
		// // ((UniqueConstraint) cp.getMyConstraint()).getValues().add(value);
		// // }
		// // }
		// // return value.toString();
		// // }
		// //
		// // return null;
		// }
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

			} else if (DataConstraint.class.isInstance(cp.getMyConstraint())) {

				DataConstraint myConstraint = (DataConstraint) cp.getMyConstraint();
				DataConstraint dependingConstraint = (DataConstraint) cp.getDependingConstraint();
				if (dependingConstraint.getValue() != null) { // Constraint-Wert
																// vorhanden
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
	
	@Override
	public Integer getMaxIndex() {
		return ConstraintsData.data.get(getKey()).size();
	}
}
