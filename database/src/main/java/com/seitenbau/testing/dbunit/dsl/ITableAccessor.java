package com.seitenbau.testing.dbunit.dsl;

public interface ITableAccessor<R, F> {

	R insertRow();

	F getFindWhere();
	
}
