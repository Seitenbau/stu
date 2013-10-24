package com.seitenbau.stu.database.types;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;

public class ApacheDerbyDataTypeFactory extends DefaultDataTypeFactory
{

  @Override
  public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException
  {
    DataType result = super.createDataType(sqlType, sqlTypeName);
    if (sqlType == 5)
    {
      return DataType.BOOLEAN;
    }
    return result;
  }

  @Override
  public DataType createDataType(int sqlType, String sqlTypeName, String tableName, String columnName)
      throws DataTypeException
  {
    DataType result = super.createDataType(sqlType, sqlTypeName, tableName, columnName);
    return result;
  }

}
