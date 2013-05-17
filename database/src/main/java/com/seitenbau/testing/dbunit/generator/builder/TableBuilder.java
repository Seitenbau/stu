package com.seitenbau.testing.dbunit.generator.builder;

import com.seitenbau.testing.dbunit.generator.DataType;

public class TableBuilder
{
  private final String name;

  private Table table;

  public TableBuilder(String name)
  {
    this.name = name;
    this.table = new Table(name);
  }

  public ColumnBuilder column(String name, DataType dataType)
  {
    return new ColumnBuilder(this, name, dataType);
  }

  public Table build()
  {
    return table;
  }

  public void addColumn(Column column)
  {
    table.addColumn(column);
  }

}
