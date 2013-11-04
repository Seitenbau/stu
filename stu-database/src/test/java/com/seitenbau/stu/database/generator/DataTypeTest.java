package com.seitenbau.stu.database.generator;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.seitenbau.stu.database.generator.DataType;

public class DataTypeTest
{
  private static final String JAVA_OBJECT = "java.lang.Object";

  private static final String JAVA_STRING = "java.lang.String";

  private static final String JAVA_BIGDECIMAL = "java.math.BigDecimal";

  private static final String JAVA_BOOLEAN = "java.lang.Boolean";

  private static final String JAVA_INTEGER = "java.lang.Integer";

  private static final String JAVA_LONG = "java.lang.Long";

  private static final String JAVA_FLOAT = "java.lang.Float";

  private static final String JAVA_DOUBLE = "java.lang.Double";

  private static final String JAVA_DATE = "java.util.Date";

  private static final String BYTE_ARRAY = "byte[]";

  @Test
  public void testGetJavaType()
  {
    assertEquals(JAVA_OBJECT, DataType.UNKNOWN.getJavaType());

    assertEquals(JAVA_STRING, DataType.VARCHAR.getJavaType());
    assertEquals(JAVA_STRING, DataType.CHAR.getJavaType());
    assertEquals(JAVA_STRING, DataType.LONGVARCHAR.getJavaType());
    assertEquals(JAVA_STRING, DataType.CLOB.getJavaType());

    assertEquals(JAVA_BIGDECIMAL, DataType.NUMERIC.getJavaType());
    assertEquals(JAVA_BIGDECIMAL, DataType.DECIMAL.getJavaType());

    assertEquals(JAVA_BOOLEAN, DataType.BOOLEAN.getJavaType());
    assertEquals(JAVA_BOOLEAN, DataType.BIT.getJavaType());

    assertEquals(JAVA_INTEGER, DataType.INTEGER.getJavaType());
    assertEquals(JAVA_INTEGER, DataType.TINYINT.getJavaType());
    assertEquals(JAVA_INTEGER, DataType.SMALLINT.getJavaType());

    assertEquals(JAVA_LONG, DataType.BIGINT.getJavaType());

    assertEquals(JAVA_FLOAT, DataType.REAL.getJavaType());

    assertEquals(JAVA_DOUBLE, DataType.DOUBLE.getJavaType());
    assertEquals(JAVA_DOUBLE, DataType.FLOAT.getJavaType());

    assertEquals(JAVA_DATE, DataType.DATE.getJavaType());
    assertEquals(JAVA_DATE, DataType.TIME.getJavaType());
    assertEquals(JAVA_DATE, DataType.TIMESTAMP.getJavaType());

    assertEquals(BYTE_ARRAY, DataType.VARBINARY.getJavaType());
    assertEquals(BYTE_ARRAY, DataType.BINARY.getJavaType());
    assertEquals(BYTE_ARRAY, DataType.LONGVARBINARY.getJavaType());
    assertEquals(BYTE_ARRAY, DataType.BLOB.getJavaType());
  }
}
