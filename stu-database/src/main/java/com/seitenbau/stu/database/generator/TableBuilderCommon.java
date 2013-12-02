package com.seitenbau.stu.database.generator;

public interface TableBuilderCommon
{
  Table build();
  
  /**
   * Adds a column to the table.
   * @param name The database name of the column.
   * @param dataType The column's data type.
   * @return A builder to configure the column
   */
  ColumnBuilder column(String name, DataType dataType);

}
