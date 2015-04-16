package com.seitenbau.stu.database.generator.values;

import java.util.GregorianCalendar;
import java.util.Random;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;

public class DateGenerator extends ValueGenerator {

	private Random random;

	public DateGenerator() {
	}

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
	}		
	
	@Override
	public Result nextValue(Result result){
		GregorianCalendar gc = new GregorianCalendar();

		int year = randBetween(1900, 2010);

		gc.set(GregorianCalendar.YEAR, year);

		int dayOfYear = randBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));

		gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);

		int iYear = gc.get(GregorianCalendar.YEAR);
		int iMonth = gc.get(GregorianCalendar.MONTH);
		int iDay = gc.get(GregorianCalendar.DAY_OF_MONTH);

		result.setValue("asDate(\"" + getString(iDay) + "." + getString(iMonth + 1) + "." + getString(iYear) + "\")");

		
		return result;
	}

	@Override
	public Result nextValue(EntityBlueprint eb) {
		GregorianCalendar gc = new GregorianCalendar();

		int year = randBetween(1900, 2010);

		gc.set(GregorianCalendar.YEAR, year);

		int dayOfYear = randBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));

		gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);

		int iYear = gc.get(GregorianCalendar.YEAR);
		int iMonth = gc.get(GregorianCalendar.MONTH);
		int iDay = gc.get(GregorianCalendar.DAY_OF_MONTH);

		return new Result("asDate(\"" + getString(iDay) + "." + getString(iMonth + 1) + "." + getString(iYear) + "\")", true);
		// return "asDate(\"" + getString(iDay) + "." + getString(iMonth+1) + "." + getString(iYear) + "\")";
	}

	@Override
	public Result nextValue(Integer index, EntityBlueprint eb) {
		GregorianCalendar gc = new GregorianCalendar();

		int year = randBetween(1900, 2010);

		gc.set(GregorianCalendar.YEAR, year);

		int dayOfYear = randBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));

		gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);

		int iYear = gc.get(GregorianCalendar.YEAR);
		int iMonth = gc.get(GregorianCalendar.MONTH);
		int iDay = gc.get(GregorianCalendar.DAY_OF_MONTH);

		return new Result("asDate(\"" + getString(iDay) + "." + getString(iMonth + 1) + "." + getString(iYear) + "\")", true);
		// return "asDate(\"" + getString(iDay) + "." + getString(iMonth+1) + "." + getString(iYear) + "\")";
	}

	public int randBetween(int start, int end) {
		return start + random.nextInt(1 + end - start);
	}

	public String getString(int i) {
		if (i < 10) {
			return "0" + String.valueOf(i);
		}
		return String.valueOf(i);
	}

	public static class Factory implements ValueGeneratorFactory {

		@Override
		public ValueGenerator createGenerator() {
			return new DateGenerator();
		}

	}

	@Override
	public void addConstraint(ConstraintPair constraintPair) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearConstraints() {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getMaxIndex() {
		return 10000;
	}

	@Override
	public Result nextValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
