package com.seitenbau.stu.database.generator.values;

import java.util.HashMap;
import java.util.Map;

import com.seitenbau.stu.database.generator.DataType;

/**
 * Singleton class to register a value generator for a data type.
 */
public enum ValueGeneratorRegistry {

	INSTANCE;

	private final Map<DataType, ValueGeneratorFactory> registry = new HashMap<DataType, ValueGeneratorFactory>();

	private ValueGeneratorFactory valueGeneratorFor = new StringGenerator.Factory();

	/**
	 * add some default value generators for the standard data types.
	 */
	{
		register(new IntegerGenerator.Factory(-128, 127)).forType(DataType.TINYINT);
		register(new IntegerGenerator.Factory(0, 255)).forType(DataType.TINYINT_UNSIGNED);
		register(new IntegerGenerator.Factory(-32768, 32767)).forType(DataType.SMALLINT);
		register(new IntegerGenerator.Factory(0, 65535)).forType(DataType.SMALLINT_UNSIGNED);
		register(new IntegerGenerator.Factory(Integer.MIN_VALUE, Integer.MAX_VALUE)).forType(DataType.INTEGER);
		register(new IntegerGenerator.Factory(0, Integer.MAX_VALUE)).forType(DataType.INTEGER_UNSIGNED);
		
		register(new IntegerGenerator.Factory(0, Integer.MAX_VALUE)).forType(DataType.BIGINT);
		
		
		register(new StringGenerator.Factory()).forType(DataType.VARCHAR);
		register(new BooleanGenerator.Factory()).forType(DataType.BOOLEAN);
		register(new DateGenerator.Factory()).forType(DataType.DATE);
		register(new TimestampGenerator.Factory()).forType(DataType.TIMESTAMP);
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
		if (generator != null) {
			return generator.createGenerator();
		}
		return valueGeneratorFor.createGenerator();
	}

	public void setDefaultValueGenerator(ValueGeneratorFactory defaultValueGenerator) {
		this.valueGeneratorFor = defaultValueGenerator;
	}

}
