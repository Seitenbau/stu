package com.seitenbau.stu.dbunit.generator;

import java.util.LinkedList;
import java.util.List;

public class TableBuilder
{

  protected final DatabaseModel model;

  protected final String name;

  protected String javaName;

  protected String description;

  protected final List<ColumnBuilder> columnBuilders;

  protected long seed;

  protected int minEntities;

  protected Integer infinite;

  TableBuilder(DatabaseModel model, String name)
  {
    this.model = model;
    this.name = name;
    seed = 0;
    minEntities = 1;
    javaName = DataSet.makeNiceJavaName(name);
    columnBuilders = new LinkedList<ColumnBuilder>();
    infinite = null;
  }

  /**
   * Finalizes the creation of the table.
   * @return The created table
   */
  public Table build()
  {
    final Table result = new Table(name, javaName, getTableDescription(), seed, infinite,
        minEntities, columnBuilders);
    model.addTable(result);
    return result;
  }

  protected String getTableDescription()
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


  public TableBuilder seed(long seed)
  {
    this.seed = seed;
    return this;
  }

  public TableBuilder minEntities(int count)
  {
    this.minEntities = count;
    return this;
  }

  public TableBuilder infinite(int infinite)
  {
    this.infinite = Integer.valueOf(infinite);
    return this;
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
