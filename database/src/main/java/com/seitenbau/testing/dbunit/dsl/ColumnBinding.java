package com.seitenbau.testing.dbunit.dsl;

public abstract class ColumnBinding<R, F>
{
  
  public abstract void set(R row, Object value);
  
  public boolean isRefColumn() {
    return false;
  }
  
  public boolean isIdColumn() {
    return false;
  }
  
  public abstract R query(F findWhere, Object value);

}
