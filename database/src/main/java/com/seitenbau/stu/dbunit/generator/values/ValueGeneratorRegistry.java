package com.seitenbau.stu.dbunit.generator.values;

import java.util.HashMap;
import java.util.Map;

import com.seitenbau.stu.dbunit.generator.DataType;

/**
 * Singleton class to register a value generator for a data type.
 */
public enum ValueGeneratorRegistry {

	INSTANCE;

	private final Map<DataType, ValueGeneratorFactory> registry = new HashMap<DataType, ValueGeneratorFactory>();

	private ValueGeneratorFactory valueGeneratorFor = new StringGenerator.Factory();

	/**
	 *  add some default value generators for the standard data typs.
	 */
	{
		register(new IntegerGenerator.Factory(Integer.MIN_VALUE, Integer.MAX_VALUE))	.forType(DataType.INTEGER);
		register(new StringGenerator.Factory())											.forType(DataType.VARCHAR);
		register(new BooleanGenerator.Factory())										.forType(DataType.BOOLEAN);
		register(new DateGenerator.Factory())											.forType(DataType.DATE);
	}

	public class ForDataType {

		private final ValueGeneratorFactory generator;

		private ForDataType(ValueGeneratorFactory generator) {
			this.generator = generator;
		}

		public void forType(DataType type) {
			registry.put(type, generator);
		}

	}

	public ForDataType register(ValueGeneratorFactory generator) {
		return new ForDataType(generator);
	}

	public ValueGenerator getValueGeneratorFor(DataType type) {
		ValueGeneratorFactory generator = registry.get(type);
		if(generator != null) {
			return generator.createGenerator();
		}
		return valueGeneratorFor.createGenerator();
	}

	public void setDefaultValueGenerator(ValueGeneratorFactory defaultValueGenerator) {
		this.valueGeneratorFor = defaultValueGenerator;
	}

}