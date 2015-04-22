package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.Random;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;

public class DataGenerator extends ValueGenerator {

	private DomainSpecificDataBuilder ConstraintsData;

	public DomainSpecificDataBuilder getConstraintsData() {
		return ConstraintsData;
	}

	public void setConstraintsData(DomainSpecificDataBuilder constraintsData) {
		ConstraintsData = constraintsData;
	}

	public DataGenerator(String string) {
		setKey(string);
	}

	public DataGenerator() {
	}
	
	@Override
	public Result nextValue(){		
		ArrayList<DomainSpecificData> al = ConstraintsData.data.get(getKey());
		
		Result result = new Result(null, false);
		
		DomainSpecificData value = al.get(result.getValueIndex());
		result.setValue("\"" + value.getValue() + "\"");		
		result.setGenerated(true);

		return result;
	}
	
	@Override
	public Result nextValue(Integer index) {
		Random rand = new Random(index);
		
		// Workaround
		if(getKey() == "geschlecht"){
			rand.nextInt(); rand.nextInt(); rand.nextInt(); rand.nextInt(); rand.nextInt();
		}			
		
		ArrayList<DomainSpecificData> al = ConstraintsData.data.get(getKey());		
		Result result = new Result(null, false);
		
		int i = rand.nextInt(al.size());
		
		DomainSpecificData value = al.get(i);
		result.setValue("\"" + value.getValue() + "\"");		
		result.setGenerated(true);

		return result;
	}
	
	public Result nextValue(Result result) {
		
		ArrayList<DomainSpecificData> al = ConstraintsData.data.get(getKey());
		
		if (result == null) {
			result = new Result(null, false);
			result.setValueIndex(0);
			result.setMaxIndex(al.size()-1);
		} else {
			result.setMaxIndex(al.size()-1);
			result.nextValueIndex();
		}		
		
		DomainSpecificData valu = al.get(result.getValueIndex());
		result.setValue("\"" + valu.getValue() + "\"");		
		result.setGenerated(true);

		return result;
	}
	
	@Override
	public Result nextValue(EntityBlueprint eb) {

		Result result = new Result(null, false);
		
		if (!allTargetsLoaded(eb))
			return result;
		
		// TODO Check if all sources have values...
		if(!checkValues(eb))
			return result;
	

		ArrayList<DomainSpecificData> al = ConstraintsData.data.get(getKey());

		Comparable<?> value = null;

		int i = 0;
		do {
			if (al.size() > 0) {
				int randInt = getRandom().nextInt(al.size());
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
	

		ArrayList<DomainSpecificData> al = ConstraintsData.data.get(getKey());

		Comparable<?> value = null;

		int i = 0;
		do {
			if (al.size() > 0) {
				int randInt = getRandom().nextInt(al.size());
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
