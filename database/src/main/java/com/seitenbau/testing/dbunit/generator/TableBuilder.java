package com.seitenbau.testing.dbunit.generator;

import java.util.LinkedList;
import java.util.List;

public class TableBuilder
{

  private final DatabaseModel model;

  private final String name;

  private String javaName;

  private String description;

  private final List<ColumnBuilder> columnBuilders;

  public TableBuilder(DatabaseModel model, String name)
  {
    this.model = model;
    this.name = name;
    javaName = DataSet.makeNiceJavaName(name);
    columnBuilders = new LinkedList<ColumnBuilder>();
  }

  public Table build()
  {
    final Table result = new Table(name, javaName, getTableDescription(), columnBuilders);
    model.addTable(result);
    return result;
  }

  private String getTableDescription()
  {
    if (description != null)
    {
      return description;
    }

    return "The " + name + " table";
  }

  String getName()
  {
    return name;
  }

  /**
   * Defines how the table is spelled in the Java source code.
   * @param javaName The table  name within the Java sources
   * @return The table builder
   */
  public TableBuilder javaName(String javaName)
  {
    this.javaName = javaName;
    return this;
  }

  String getJavaName()
  {
    return javaName;
  }

  /**
   * Adds a description text to the table used for JavaDoc in the generated Java classes.
   * @param description The description for the table
   * @return The table builder
   */
  public TableBuilder description(String description)
  {
    this.description = description;
    return this;
  }

  String getDescription()
  {
    return description;
  }

  /**
   * Adds a column to the table.
   * @param name The database name of the column.
   * @param dataType The column's data type.
   * @return A builder to configure the column
   */
  public ColumnBuilder column(String name, DataType dataType)
  {
    return new ColumnBuilder(this, name, dataType);
  }

  void addColumnBuilder(ColumnBuilder columnBuilder)
  {
    columnBuilders.add(columnBuilder);
  }

}
