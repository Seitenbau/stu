package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import com.seitenbau.stu.database.generator.hints.DomainSpecificDataHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.valuetypes.IntValue;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class DataGenerator extends ValueGenerator {

	private DomainSpecificDataBuilder ConstraintsData;
	private ArrayList<DomainSpecificDataHint> valueList;

	public DomainSpecificDataBuilder getConstraintsData() {
		return this.ConstraintsData;
	}

	public void setConstraintsData(DomainSpecificDataBuilder constraintsData) {
		this.ConstraintsData = constraintsData;
	}

	public DataGenerator(String string) {
		setKey(string);
	}

	public DataGenerator() {
	}

	@Override
	public Result nextValue() {
		return nextValue(0);
	}

	@Override
	public Result nextValue(Integer index) {
		Random rand = new Random(index);

		// TODO: Workaround: Fix and remove
		 //if(getKey() == "geschlecht"){
			 rand.nextInt(); rand.nextInt(); rand.nextInt(); rand.nextInt(); rand.nextInt();
		 //}

		walkthroughHints();

		Result result = new Result(null, false, false);

		if (valueList.size() > 0) {
			
			boolean flag = true;	
			int counter = 0;
			do{		
				counter++;
				int i = rand.nextInt(valueList.size());
				DomainSpecificDataHint value = valueList.get(i);
				result.setValue(value.getValue());
				result.setGenerated(true);
				result.setFinal(true);

				boolean internflag = true;
				// Check notAllowedValues
				for (Value<?> v : notAllowedValues) {
					try {
						if (v.compareTo(result.getValue()) == 0) {
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
			}while(flag && counter < valueList.size()*2);
			
			if(counter == valueList.size()*2)
				return null;
		
			return result;
		}

		return result;
	}

	@Override
	public void walkthroughHints() {
		super.walkthroughHints();

		ArrayList<DomainSpecificDataHint> al = ConstraintsData.data.get(getKey());
		valueList = new ArrayList<DomainSpecificDataHint>();
		for (DomainSpecificDataHint entry : al) {
			if(!notAllowedValues.contains(entry.getValue())) // TODO Check KEy and Value
				valueList.add(entry);
		}

		for (Hint hint : getHints()) {
			if (DomainSpecificDataHint.class.isInstance(hint)) {
				DomainSpecificDataHint dsdh = ((DomainSpecificDataHint) hint);

				String key = dsdh.getKey();
				Value<?> value = dsdh.getValue();

				if (key.compareTo(key) == 0 && value != null) {
					Iterator<Entry<String, ArrayList<DomainSpecificDataHint>>> it = ConstraintsData.data.entrySet().iterator();
					while (it.hasNext()) {
						Entry<String, ArrayList<DomainSpecificDataHint>> pairs = it.next();
						if (pairs.getKey() == key) {

							ArrayList<DomainSpecificDataHint> intList = new ArrayList<DomainSpecificDataHint>();
							for (DomainSpecificDataHint c : al) {
								if(c.notAppliesTo(dsdh))
									intList.add(c);
							}

							for (DomainSpecificDataHint c : intList) {
								valueList.remove(c);
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
	
	@Override
	public void clearHints(){
		super.clearHints();
		valueList.clear();
		notAllowedValues.clear();		
	}
}
