package com.seitenbau.testing.dbunit.dsl;

import com.google.common.base.Optional;
import com.seitenbau.testing.dbunit.generator.DataType;

public abstract class ColumnBinding<R, G>
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
  
  public Optional<R> getWhere(G getWhere, Object value) {
    return Optional.<R> absent();
  }
  
  public abstract DataType getDataType();

}
