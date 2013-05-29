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
  
  public String getName()
  {
    return name;
  }

  public TableBuilder javaName(String javaName)
  {
    this.javaName = javaName;
    return this;
  }

  public String getJavaName()
  {
    return javaName;
  }

  public TableBuilder description(String description)
  {
    this.description = description;
    return this;
  }

  public String getDescription()
  {
    return description;
  }

  public ColumnBuilder column(String name, DataType dataType)
  {
    return new ColumnBuilder(this, name, dataType);
  }

  void addColumnBuilder(ColumnBuilder columnBuilder)
  {
    columnBuilders.add(columnBuilder);
  }


//  public void addColumn(Column column)
//  {
//    table.addColumn(column);
//  }

//  Table getTable()
//  {
//    return table;
//  }
  }
