package com.seitenbau.stu.database.modelgenerator;

public enum SQLColumnDescriptions
{
  TABLE_CAT(1, String.class),
  TABLE_SCHEM(2, String.class),
  TABLE_NAME(3, String.class),
  COLUMN_NAME(4, String.class),
  DATA_TYPE(5, Integer.class), // SQL type from java.sql.Types
  TYPE_NAME(6, String.class), // Data source dependent type name, for a UDT the type name is fully qualified
  COLUMN_SIZE(7, Integer.class),
  DECIMAL_DIGITS(9, Integer.class), // the number of fractional digits (if available)
  NUM_PREC_RADIX(10, Integer.class), //Radix (typically either 10 or 2)
  NULLABLE(11, Integer.class), //is NULL allowed.
  REMARKS(12, String.class), //comment describing column (may be null)
  COLUMN_DEF(13, String.class), //default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
  CHAR_OCTET_LENGTH(16, Integer.class), //for char types the maximum number of bytes in the column
  ORDINAL_POSITION(17, Integer.class), //index of column in table (starting at 1)
  IS_NULLABLE(18, String.class), //ISO rules are used to determine the nullability for a column.
  SCOPE_CATALOG(19, String.class), //catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
  SCOPE_SCHEMA(20, String.class), //schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
  SCOPE_TABLE(21, String.class), //table name that this the scope of a reference attribute (null if the DATA_TYPE isn't REF)
  SOURCE_DATA_TYPE(22, Short.class), //source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
  IS_AUTOINCREMENT(23, String.class), //Indicates whether this column is auto incremented
  IS_GENERATEDCOLUMN(24, String.class); //Indicates whether this is a generated column

  private int value;
  private Class<?> clazz;

  private SQLColumnDescriptions(int value, Class<?> clazz)
  {
    this.value = value;
    this.clazz = clazz;
  }

}
