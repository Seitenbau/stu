package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.DomainSpecificDataHint;
import com.seitenbau.stu.database.generator.hints.Hint;

public class DataGenerator extends ValueGenerator {

	private DomainSpecificDataBuilder ConstraintsData;
	private ArrayList<DomainSpecificDataHint> valueList;

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
	public Result nextValue() {
		return nextValue(0);
	}

	@Override
	public Result nextValue(Integer index) {
		Random rand = new Random(index);

		// TODO: Workaround: Fix and remove
		 if(getKey() == "geschlecht"){
			 rand.nextInt(); rand.nextInt(); rand.nextInt(); rand.nextInt(); rand.nextInt();
		 }

		walkthroughHints();

		Result result = new Result(null, false, false);

		if (valueList.size() > 0) {

			int i = rand.nextInt(valueList.size());
			DomainSpecificDataHint value = valueList.get(i);
			result.setValue(value.getValue());
			result.setGenerated(true);
			result.setFinal(true);
		}

		return result;
	}

	@Override
	public void walkthroughHints() {
		super.walkthroughHints();

		ArrayList<DomainSpecificDataHint> al = ConstraintsData.data.get(getKey());
		valueList = new ArrayList<DomainSpecificDataHint>();
		for (DomainSpecificDataHint entry : al) {
			valueList.add(entry);
		}

		if (this.getKey().equals("sprache"))
			System.out.println("sprache");

		for (Hint hint : getHints()) {
			if (DomainSpecificDataHint.class.isInstance(hint)) {
				DomainSpecificDataHint dsdh = ((DomainSpecificDataHint) hint);

				String key = dsdh.getKey();
				Comparable<?> value = dsdh.getValue();

				if (value != null) {
					Iterator<Entry<String, ArrayList<DomainSpecificDataHint>>> it = ConstraintsData.data.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pairs = (Map.Entry) it.next();
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
					
					if (valueList.size() == 0) {
						System.out.println("Null");
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
