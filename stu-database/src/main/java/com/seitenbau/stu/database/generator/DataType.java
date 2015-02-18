package com.seitenbau.stu.database.generator;

import java.math.BigDecimal;
import java.util.Date;

public enum DataType
{
  UNKNOWN(Object.class),

  VARCHAR(String.class), CHAR(String.class), LONGVARCHAR(String.class), CLOB(String.class), TEXT(String.class),

  NUMERIC(BigDecimal.class), DECIMAL(BigDecimal.class),

  BOOLEAN(Boolean.class), BIT(Boolean.class),

  INTEGER(Integer.class), INTEGER_UNSIGNED(Integer.class),
  TINYINT(Integer.class), TINYINT_UNSIGNED(Integer.class),
  SMALLINT(Integer.class), SMALLINT_UNSIGNED(Integer.class),
  BIGINT(Long.class), BIGINT_UNSIGNED(Long.class),
  REAL(Float.class), DOUBLE(Double.class), FLOAT(Double.class),

  DATE(Date.class), TIME(Date.class), TIMESTAMP(Date.class),

  VARBINARY(byte[].class), BINARY(byte[].class), LONGVARBINARY(byte[].class), BLOB(byte[].class), LONGBLOB(byte[].class);

  private static final String DATATYPE_CLASS = "DataType";

  private final Class<?> javaTypeClass;

  private DataType(Class<?> javaType)
  {
    javaTypeClass = javaType;
  }

  public Class<?> getJavaTypeClass()
  {
    return javaTypeClass;
  }

  public String getJavaType()
  {
    return javaTypeClass.getCanonicalName();
  }

  public String getDataType()
  {
    return DATATYPE_CLASS + '.' + this.name();
  }
}
