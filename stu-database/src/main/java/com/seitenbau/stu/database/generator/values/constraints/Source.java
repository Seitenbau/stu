package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;
import java.util.HashMap;

import com.seitenbau.stu.database.generator.Column;
import com.seitenbau.stu.database.generator.Table;
import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.values.Result;

public class Source {
	private String name;
	
	private HashMap<String, Boolean> hasValue = new HashMap<String, Boolean>();
	private boolean hasPath = false;
	
	private HashMap<String, Object> value = new HashMap<String, Object>();
	private Object path;
	
	private ArrayList<Result> results = new ArrayList<Result>(); // Alle Werte auf die diese Source verweist.
	
	String tableString;				// TODO: Make private
	private Integer entityIndex;
	String columnString;
	
	private Table table;
	private EntityBlueprint eb;
	private Column column;
	
	public Source(String name){
		this.setName(name);
	}
	
	public Table getTable() {
		return table;
	}	

	public EntityBlueprint getEb() {
		return eb;
	}

	public void setEb(EntityBlueprint eb) {
		this.eb = eb;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}	

	public void setValue(String index, Object value) {
		this.value.put(index, value);
		this.hasValue.put(index, true);
	}

	public Object getValue(String index) {
		return value.get(index);
	}

	public boolean hasValue(String index) {
		if (hasValue.containsKey(index))
			return hasValue.get(index);
		else
			return false;
	}

	public Object getPath() {
		return path;
	}

	public void setPath(Object path) {
		this.path = path;
		this.hasPath = true;
	}

	public boolean hasPath() {
		return hasPath;
	}

	public Integer getEntityIndex() {
		return entityIndex;
	}

	public void setEntityIndex(Integer entityIndex) {
		this.entityIndex = entityIndex;
	}
	
	public ArrayList<Result> getResults() {
		return results;
	}

	public void addResult(Result result) {
		results.add(result);
	}

	public void setTable(Table table) {
		this.table = table;
	}
	
	public Result getResult(String name){
		for(Result result : results){
			if(result.getEb().getRefName().compareTo(name) == 0){
				return result;
			}
		}
		return null;
	}
	
	
	public ArrayList<Result> getNotGeneratedResults(){
		ArrayList<Result> nullResults = new ArrayList<Result>();
		
		for(Result r: results){
			if(!r.isGenerated()){
				nullResults.add(r);
			}
		}
		
		return nullResults;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
