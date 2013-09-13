package com.seitenbau.testing.dbunit.generator.types;

public class IntegerType implements IDataType
{

  @Override
  public Class<?> getJavaTypeClass()
  {
    return Integer.class;
  }

  @Override
  public String getDatabaseType()
  {
    return "INTEGER";
  }

  @Override
  public Object convert(Object o)
  {
    // TODO Auto-generated method stub
    return null;
  }

}
