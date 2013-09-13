package com.seitenbau.testing.dbunit.generator.types;

public class BooleanType implements IDataType
{

  @Override
  public Class<?> getJavaTypeClass()
  {
    return BooleanType.class;
  }

  @Override
  public String getDatabaseType()
  {
    return "BOOLEAN";
  }

  @Override
  public Object convert(Object o)
  {
    if (o instanceof BooleanType) {
      return o;
    }

    throw new RuntimeException("Not fully implemented yet");
  }

}
