package com.seitenbau.stu.database.modelgenerator;

public enum SQLColumnType
{
  TYPE_BIT(-7),
  TYPE_TINYINT(-6),
  TYPE_BIGINT(-5),
  TYPE_LONGVARBINARY(-4),
  TYPE_VARBINARY(-3),
  TYPE_BINARY(-2),
  TYPE_LONGVARCHAR(-1),
  TYPE_NULL(0),
  TYPE_CHAR(1),
  TYPE_NUMERIC(2),
  TYPE_DECIMAL(3),
  TYPE_INTEGER(4),
  TYPE_SMALLINT(5),
  TYPE_FLOAT(6),
  TYPE_REAL(7),
  TYPE_DOUBLE(8),
  TYPE_VARCHAR(12),
  TYPE_DATE(91),
  TYPE_TIME(92),
  TYPE_TIMESTAMP(93),
  TYPE_OTHER(1111);

  private final int value;

  private SQLColumnType(int value)
  {
    this.value = value;
  }

  public int getValue()
  {
    return value;
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
