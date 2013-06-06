package com.seitenbau.testing.dbunit.dsl;

public interface IScope
{
  
  Object getScopeInstance();
  
  String getScopeIdentifier();
  
  <T> T getTable(Class<T> tableClass);
  
}
