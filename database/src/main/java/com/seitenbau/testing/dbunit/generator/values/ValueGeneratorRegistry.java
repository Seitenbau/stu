package com.seitenbau.testing.dbunit.generator.values;

import java.util.HashMap;
import java.util.Map;

import com.seitenbau.testing.dbunit.generator.DataType;

/**
 * Singleton class to register a value generator for a data type. 
 */
public enum ValueGeneratorRegistry {

	INSTANCE;
	
	private final Map<DataType, ValueGenerator> registry = new HashMap<DataType, ValueGenerator>();
	
	private ValueGenerator valueGeneratorFor = new StringGenerator();
	
	/**
	 *  add some default value generators for the standard data typs.
	 */
	{
		register(new IntegerGenerator(Integer.MIN_VALUE, Integer.MAX_VALUE))	.forType(DataType.INTEGER);
		register(new StringGenerator())											.forType(DataType.VARCHAR);
		register(new BooleanGenerator())										.forType(DataType.BOOLEAN);
		register(new DateGenerator())											.forType(DataType.DATE);
	}
	
	public class ForDataType {
		
		private final ValueGenerator generator;
		
		private ForDataType(ValueGenerator generator) {
			this.generator = generator;
		}
		
		public void forType(DataType type) {
			registry.put(type, generator);
		}
		
	}
	
	public ForDataType register(ValueGenerator generator) {
		return new ForDataType(generator);
	}
	
	public ValueGenerator getValueGeneratorFor(DataType type) {
		ValueGenerator generator = registry.get(type);
		if(generator != null) {
			return generator;
		}
		return valueGeneratorFor;
	}

	public void setDefaultValueGenerator(ValueGenerator defaultValueGenerator) {
		this.valueGeneratorFor = defaultValueGenerator;
	}
	
}
