package com.seitenbau.testing.dbunit.dsl;

import com.seitenbau.testing.dbunit.generator.DataType;

public class RefColumnBinding<R, G> extends ColumnBinding<R, G>
{

  @Override
  public void set(R row, Object value)
  {
    throw new RuntimeException("Setting values in a REF column is not allowed");
  }

  @Override
  public boolean isRefColumn()
  {
    return true;
  }

  @Override
  public DataType getDataType()
  {
    return null;
  }

}
