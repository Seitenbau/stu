package com.seitenbau.testing.dbunit.generator.builder;

import com.seitenbau.testing.dbunit.generator.DataType;

public class ColumnBuilder
{
  private final TableBuilder tableBuilder;

  private final String name;

  private final DataType dataType;

  private boolean isIdentifierColumn;

  private boolean enableAutoIdHandling;

  public ColumnBuilder(TableBuilder tableBuilder, String name, DataType dataType)
  {
    this.tableBuilder = tableBuilder;
    this.name = name;
    this.dataType = dataType;
    this.enableAutoIdHandling = false;
    this.isIdentifierColumn = false;
  }

  public ColumnBuilder column(String name, DataType dataType)
  {
    buildColumn();
    return new ColumnBuilder(tableBuilder, name, dataType);
  }

  public ColumnBuilder autoIdHandling()
  {
    enableAutoIdHandling = true;
    return this;
  }

  public ColumnBuilder identifierColumn()
  {
    isIdentifierColumn = true;
    return this;
  }

  public Table build()
  {
    buildColumn();
    return tableBuilder.build();
  }

  private void buildColumn()
  {
    Column column = new Column(name, dataType);
    column.setIdentifier(isIdentifierColumn);
    column.setAutoIdHandling(enableAutoIdHandling);
    column.setTable(tableBuilder.build());

    tableBuilder.addColumn(column);
  }

}
