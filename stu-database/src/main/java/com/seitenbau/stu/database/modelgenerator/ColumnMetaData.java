package com.seitenbau.stu.database.modelgenerator;

import java.sql.ResultSet;

public class ColumnMetaData
{
  private final ResultSet rs;

  public ColumnMetaData(ResultSet rs)
  {
    this.rs = rs;
  }

  private String getString(int index)
  {
    try {
      return rs.getString(index);
    }
    catch (Exception e)
    {
      return null;
    }
  }

  private Integer getInt(int index)
  {
    try {
      return rs.getInt(index);
    }
    catch (Exception e)
    {
      return null;
    }
  }

  private Short getShort(int index)
  {
    try {
      return rs.getShort(index);
    }
    catch (Exception e)
    {
      return null;
    }
  }

  public String getTableCategory()
  {
    return getString(1);
  }

  public String getTableSchema()
  {
    return getString(2);
  }

  public String getTableName()
  {
    return getString(3);
  }

  public String getColumnName()
  {
    return getString(4);
  }

  public SQLColumnType getDataType()
  {
    return SQLColumnType.valueToType(getInt(5));
  }

  public String getDataTypeName()
  {
    return getString(6);
  }

  public Integer getColumnSize()
  {
    return getInt(7);
  }

  /*
   * the number of fractional digits (if available)
   */
  public Integer getDecimalDigits()
  {
    return getInt(9);
  }

  // Radix (typically either 10 or 2)
  public Integer getNumPrecRadix()
  {
    return getInt(10);
  }

  /*
NULLABLE int => is NULL allowed.
columnNoNulls - might not allow NULL values
columnNullable - definitely allows NULL values
columnNullableUnknown - nullability unknown
   */
  public Integer getNullable()
  {
    return getInt(11);
  }

  // comment describing column (may be null)
  public String getRemarks()
  {
    return getString(12);
  }

  //default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
  public String getColumnDefault()
  {
    return getString(13);
  }

  //for char types the maximum number of bytes in the column
  public Integer getCharOctedLength()
  {
    return getInt(16);
  }

  //index of column in table (starting at 1)
  public Integer getOrdinalPosition()
  {
    return getInt(17);
  }

  //ISO rules are used to determine the nullability for a column.
  public String isNullable()
  {
    return getString(18);
  }

  public String getScopeCatalog()
  {
    return getString(19);
  }

  public String getScopeSchema()
  {
    return getString(20);
  }

  public String getScopeTable()
  {
    return getString(21);
  }

  public Short getScopeDataType()
  {
    return getShort(22);
  }

  public String isAutoIncrement()
  {
    return getString(23);
  }

  public String isGeneradedColumn()
  {
    return getString(24);
  }

}
