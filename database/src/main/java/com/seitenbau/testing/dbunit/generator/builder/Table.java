package com.seitenbau.testing.dbunit.generator.builder;

import java.util.ArrayList;
import java.util.List;

public class Table
{
  private final String name;

  private List<Column> columns = new ArrayList<Column>();

  public Table(String name)
  {
    this.name = name;
  }

  public void addColumn(Column column)
  {
    this.columns.add(column);
  }

  public String getName()
  {
    return name;
  }

  @Override
  public String toString()
  {
    return "Table [name=" + name + ", " + columns.toString();
  }
}
