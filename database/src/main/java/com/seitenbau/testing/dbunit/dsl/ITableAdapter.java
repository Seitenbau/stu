package com.seitenbau.testing.dbunit.dsl;

public interface ITableAdapter<R, F> {

	R insertRow();

	F getFindWhere();
	
}
