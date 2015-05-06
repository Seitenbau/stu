package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.seitenbau.stu.database.generator.values.valuetypes.IntValue;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class IntegerGenerator extends ValueGenerator {

	private int min;
	private int max;
	
	private int initMin;
	private int initMax;

	private final long module;

	private final Strategy strategy;

	public IntegerGenerator(int min, int max) {
		this.min = min;
		this.max = max;
		
		this.initMin = min;
		this.initMax = max;
		
		module = (long) max - min;
		if (module < Integer.MAX_VALUE) {
			this.strategy = new IntRange();
		} else {
			this.strategy = new LongRange();
		}
	}

	@Override
	public void initialize(long seed) {
		setRandom(new Random(seed));
	}

	@Override
	public Result nextValue() {
		// TODO Remove
		return null;
	}

	@Override
	public Result nextValue(Integer index) {
		walkthroughHints();

		if (returnValue != null)
			return new Result(returnValue, true, true);
		
		if(upperLimit != null){
			if(upperLimit.compareTo(max) < 0)
				max = Integer.valueOf(upperLimit.toInt());
		}
		
		if(lowerLimit != null){
			if(lowerLimit.compareTo(min) > 0)
				min = Integer.valueOf(lowerLimit.toInt());
		}


		// TODO: Check if there are enough possible values
		if(max - min < 1){
			return null;
		}

		Random rand = new Random(index);
		Result result;
		
		int maxMin = max - min;
		if(maxMin < Integer.MAX_VALUE)
			maxMin += 1;
		
		boolean flag = true;	
		int counter = 0;
		do{		
			counter++;
			result = new Result(new IntValue(rand.nextInt(maxMin) + min), true, true);

			boolean internflag = true;
			// Check notAllowedValues
			for (Value<?> value : notAllowedValues) {
				try {
					if (value.compareTo(result.getValue()) == 0) {
						internflag = false;
						break;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(internflag)
				flag = false;
		}while(flag && counter < maxMin);
		
		if(counter == maxMin)
			return null;
		
		return result;
	}

	private interface Strategy {
		Comparable<?> nextValue();

		Comparable<?> nextValue(Integer index);

		void AddRange(Comparable<?> min, Comparable<?> max);
	}

	@Override
	public void walkthroughHints() {
		super.walkthroughHints();

		// Implement the walkthrough for Integer specific new hints here
	}

	private class LongRange implements Strategy {
		@Override
		public Comparable<?> nextValue() {
			long value = (Math.abs(getRandom().nextLong()) % module) + min;
			return value;
		}

		@Override
		public Comparable<?> nextValue(Integer index) {
			Long value = (long) (min + index);
			if (value <= max)
				return value;
			else
				return null;
		}

		@Override
		public void AddRange(Comparable<?> min, Comparable<?> max) {
			// TODO Auto-generated method stub
		}
	}

	private class IntRange implements Strategy {
		@Override
		public Comparable<?> nextValue() {
			int value = getRandom().nextInt(1 + max - min) + min;
			return value;
		}

		@Override
		public Comparable<?> nextValue(Integer index) {
			Integer value = min + index;
			if (value <= max)
				return value;
			else
				return null;
		}

		@Override
		public void AddRange(Comparable<?> min, Comparable<?> max) {

		}
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

	public class RandomList {
		private ArrayList<Range> ranges = new ArrayList<Range>();

		public void add(Range range) {
			boolean modified = false;
			for (Range r : ranges) {

				if (range.min < r.min) {
					if (range.max > r.min && range.max <= r.max) {
						r.max = range.max;
						modified = true;
						continue;
					}
				}

				if (range.min > r.min) {
					if (range.min <= r.max) {
						if (range.max <= r.max) {
							r.min = range.min;
							r.max = range.max;
							modified = true;
							continue;
						} else {
							r.min = range.min;
							modified = true;
							continue;
						}
					}
				}
			}
			if (!modified)
				ranges.add(range);
		}

		public Integer getRangeCount() {
			Integer count = 0;
			for (Range r : ranges) {
				count += r.getCount();
			}
			return count;
		}

		public Integer randomValue() {
			HashMap<Double, Range> map = new HashMap<Double, Range>();
			Integer rangeCount = getRangeCount();
			Double key = 0.0;
			for (Range r : ranges) {
				map.put(key, r);
				key += ((double) r.getCount()) / rangeCount;
			}

			Range selectedRange = null;
			Double randomKey = getRandom().nextDouble();
			Set<Entry<Double, Range>> set = map.entrySet();
			for (Entry<Double, Range> e : set) {
				if (e.getKey() < randomKey) {
					selectedRange = e.getValue();
				}
			}

			return getRandom().nextInt(1 + selectedRange.max - selectedRange.min) + selectedRange.min;
		}
	}

	public class Range {
		int min;
		int max;

		public Range(int min, int max) {
			this.min = min;
			this.max = max;
		}

		public Integer getCount() {
			return max - min;
		}
	}

	@Override
	public Integer getMaxIndex() {
		return Integer.MAX_VALUE - 1;
	}
	
	@Override
	public void clearHints(){
		super.clearHints();
		min = initMin;
		max = initMax;
	}
}
