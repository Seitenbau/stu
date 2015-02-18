package com.seitenbau.stu.database.generator.values.constraints;

import java.util.HashMap;
import java.util.Map;

import com.seitenbau.stu.database.generator.DataType;


/**
 * Singleton class to register a constraint.
 */
public enum ConstraintRegistry {
		INSTANCE;
		
		private final Map<DataType, Constraint> registry = new HashMap<DataType, Constraint>();
		
		public void register(Constraint constraint){
			registry.put(DataType.BIGINT, constraint);
		}
		
		public void reduce(String columnName, Integer[] range){
			
		}
		
		public void reduce(String columnName, Double[] range){
			
		}
		
		public void reduce(String columnName, Boolean[] range){
			
		}
		
		public void reduce(String columnName, ConstraintsData data){
			
		}
}
