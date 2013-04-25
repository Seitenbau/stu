package com.seitenbau.testing.dbunit.types;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.junit.Test;
import static org.fest.assertions.Assertions.*;

public class ApacheDerbyDataTypeFactoryTest
{
  ApacheDerbyDataTypeFactory sut = new ApacheDerbyDataTypeFactory();

  @Test
  public void test3_is_Decimal() throws DataTypeException
  {
    DataType result = sut.createDataType(3, "sqlTypeName", "tableName", "columnName");
    assertThat(result).isEqualTo(DataType.DECIMAL);
  }

  @Test
  public void test5_is_Boolean() throws DataTypeException
  {
    DataType result = sut.createDataType(5, "sqlTypeName", "tableName", "columnName");
    assertThat(result).isEqualTo(DataType.BOOLEAN);
  }
}
