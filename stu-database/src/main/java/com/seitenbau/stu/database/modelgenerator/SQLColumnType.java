package com.seitenbau.stu.database.modelgenerator;

import com.seitenbau.stu.database.generator.DataType;

public enum SQLColumnType
{
  TYPE_BIT(-7, DataType.BIT),
  TYPE_TINYINT(-6, DataType.TINYINT),
  TYPE_BIGINT(-5, DataType.BIGINT),
  TYPE_LONGVARBINARY(-4, DataType.LONGVARBINARY),
  TYPE_VARBINARY(-3, DataType.VARBINARY),
  TYPE_BINARY(-2, DataType.BINARY),
  TYPE_LONGVARCHAR(-1, DataType.LONGVARCHAR),
  TYPE_NULL(0, DataType.UNKNOWN),
  TYPE_CHAR(1, DataType.CHAR),
  TYPE_NUMERIC(2, DataType.NUMERIC),
  TYPE_DECIMAL(3, DataType.DECIMAL),
  TYPE_INTEGER(4, DataType.INTEGER),
  TYPE_SMALLINT(5, DataType.SMALLINT),
  TYPE_FLOAT(6, DataType.FLOAT),
  TYPE_REAL(7, DataType.REAL),
  TYPE_DOUBLE(8, DataType.DOUBLE),
  TYPE_VARCHAR(12, DataType.VARCHAR),
  TYPE_DATE(91, DataType.DATE),
  TYPE_TIME(92, DataType.TIME),
  TYPE_TIMESTAMP(93, DataType.TIMESTAMP),
  TYPE_OTHER(1111, DataType.UNKNOWN);

  private final int value;

  private final DataType stuDataType;

  private SQLColumnType(int value, DataType stuDataType)
  {
    this.value = value;
    this.stuDataType = stuDataType;
  }

  public int getValue()
  {
    return value;
  }

  public DataType getDataType()
  {
    return stuDataType;
  }

  public static SQLColumnType valueToType(int value)
  {
    for (SQLColumnType t : SQLColumnType.values())
    {
      if (t.value == value)
      {
        return t;
      }
    }

    throw new RuntimeException("Unkown type: " + value);
  }

}
