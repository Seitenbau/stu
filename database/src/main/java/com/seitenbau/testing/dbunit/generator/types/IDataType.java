package com.seitenbau.testing.dbunit.generator.types;

public interface IDataType
{
  Class<?> getJavaTypeClass();

  String getDatabaseType();

  Object convert(Object o);

}
