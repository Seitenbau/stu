package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.values.valuetypes.IntValue;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class IntegerGenerator extends ValueGenerator {

	private int min;
	private int max;
	
	private int initMin;
	private int initMax;

	public IntegerGenerator(int min, int max) {
		this.setMin(min);
		this.setMax(max);
		
		this.initMin = min;
		this.initMax = max;
	}

	@Override
	public void initialize(long seed) {
		setRandom(new Random(seed));
	}

	@Override
	public Result nextValue(Integer index) {
		handleHints();
		this.lastSeed = index;
		
		if(upperLimit != null){
			if(upperLimit.compareTo(getMax()) < 0)
				setMax(upperLimit.toInt());
		}
		
		if(lowerLimit != null){
			if(lowerLimit.compareTo(getMin()) > 0)
				setMin(lowerLimit.toInt());
		}
		
		try {
			if (returnValue != null && returnValue.compareTo(getMin()) >= 0 && returnValue.compareTo(getMax()) <= 0)
				return new Result(returnValue, true, true);
		} catch (Exception e) {
			log.error(e.getMessage());
		}	


		if(getMax() - getMin() < 1){
			return null;
		}

		Random rand = new Random(index);
		rand.nextInt(); rand.nextInt(); rand.nextInt();
		Result result;
		
		int maxMin = getMax() - getMin();
		if(maxMin < Integer.MAX_VALUE)
			maxMin += 1;
		
		boolean flag = true;	
		int counter = 0;
		do{		
			counter++;
			result = new Result(new IntValue(rand.nextInt(maxMin) + getMin()), true, true);

			boolean internflag = true;
			// Check notAllowedValues
			for (Value<?> value : notAllowedValues) {
				try {
					if (value.compareTo(result.getValue()) == 0) {
						internflag = false;
						break;
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
			
			if(internflag)
				flag = false;
		}while(flag && counter < maxMin);
		
		if(counter == maxMin)
			return null;
		
		return result;
	}

	@Override
	public void handleHints() {
		super.handleHints();
	}
	
	public static class Factory implements ValueGeneratorFactory {

		private final int min;
		private final int max;

		public Factory(int min, int max) {
			this.min = min;
			this.max = max;
		}

		@Override
		public ValueGenerator createGenerator() {
			return new IntegerGenerator(min, max);
		}
	}

	@Override
	public Integer getMaxIndex() {
		return Integer.MAX_VALUE - 1;
	}
	
	@Override
	public void clearHints(){
		super.clearHints();
		setMin(initMin);
		setMax(initMax);
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
		this.lowerLimit = new IntValue(min);
	}

	public int getMax() {
		return max;		
	}

	public void setMax(int max) {
		this.max = max;
		this.upperLimit = new IntValue(max);

	}	
}
