package com.seitenbau.stu.database.generator.values.constraints;

public class Combination implements Cloneable {
	
	private Integer[] indices;

	public Combination(Integer[] indices){
		this.indices = indices;
	}
	
	public Integer get(int index){
		if(index < 0 || index > indices.length -1)
			return -1;
		
		return indices[index];
	}
	
	public void set(int pos, int value) {
		indices[pos] = value;
	}
	
	public Integer length(){
		return indices.length;
	}
	
	public Integer[] getIndices() {
		return indices;
	}

	public void setIndices(Integer[] indices) {
		this.indices = indices;
	}
	
	@Override
	public boolean equals(Object o){
		if(!this.getClass().isInstance(o))
			return false;
		
		Integer[] oIndices = ((Combination) o).getIndices();
		
		if(indices.length != oIndices.length)
			return false;
		
		for(int i = 0; i < indices.length; i++){
			if(indices[i] != oIndices[i])
				return false;
		}
		
		return true;
	}
	
	 @Override
	 public Combination clone(){
		 return new Combination(indices.clone());		 
	 }
}
