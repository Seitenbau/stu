package com.seitenbau.testing.dbunit.generator.types;

public class BitType extends BooleanType
{

  @Override
  public String getDatabaseType()
  {
    return "BIT";
  }

}
