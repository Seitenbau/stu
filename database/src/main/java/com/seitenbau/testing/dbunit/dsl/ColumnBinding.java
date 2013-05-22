package com.seitenbau.testing.dbunit.dsl;

import com.seitenbau.testing.dbunit.generator.DataType;

public abstract class ColumnBinding<R, F>
{
  
  public abstract void set(R row, Object value);
  
  public boolean isRefColumn() {
    return false;
  }
  
  public boolean isIdColumn() {
    return false;
  }
  
  public R query(F findWhere, Object value) {
    return null;
  }
  
  public abstract DataType getDataType();

}
