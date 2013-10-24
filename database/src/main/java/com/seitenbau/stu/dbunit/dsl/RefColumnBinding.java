package com.seitenbau.stu.dbunit.dsl;

import com.seitenbau.stu.dbunit.generator.DataType;

/**
 * ColumnBinding for REF column
 */
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
