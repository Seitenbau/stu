package com.seitenbau.testing.dbunit.generator.builder;

import com.seitenbau.testing.dbunit.generator.DataType;

public class Column
{
  private final String name;

  private final DataType dataType;

  private boolean identifier;

  private boolean autoIdHandling;

  private Table table;

  public Column(String name, DataType dataType)
  {
    this.name = name;
    this.dataType = dataType;
    this.identifier = false;
    this.autoIdHandling = false;
    this.table = null;
  }

  public void setIdentifier(boolean identifier)
  {
    this.identifier = identifier;
  }

  public void setAutoIdHandling(boolean autoIdHandling)
  {
    this.autoIdHandling = autoIdHandling;
  }

  public void setTable(Table table)
  {
    this.table = table;
  }

  @Override
  public String toString()
  {
    return "Column [name=" + name + ", type=" + dataType + ", identifier=" + identifier + ", autoIdHandling="
        + autoIdHandling + ", parentTableName=" + table.getName() + "]";
  }
}
