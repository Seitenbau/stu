package com.seitenbau.testing.dbunit.dsl;

import com.seitenbau.testing.dbunit.generator.DataType;

public abstract class ColumnBinding<R, F>
{
  
  public abstract void set(R row, Object value);
  
  public void setReference(R row, DatabaseReference reference)
  {
    throw new RuntimeException("Setting a reference is not supported for this column");
  }

  public boolean isRefColumn() {
    return false;
  }
  
  public boolean isIdentifierColumn() {
    return false;
  }
  
  public R query(F findWhere, Object value) {
    return null;
  }
  
  public abstract DataType getDataType();

}
