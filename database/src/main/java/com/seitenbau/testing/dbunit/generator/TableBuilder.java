package com.seitenbau.testing.dbunit.generator;

import java.util.LinkedList;
import java.util.List;

import com.seitenbau.testing.util.CamelCase;

public class TableBuilder
{
  
  private final DatabaseModel model;
  
  private final String name;

  private String javaName;
  
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
    final Table result = new Table(name, javaName, columnBuilders);
    model.addTable(result);
    return result;
  }
  
  public String getName()
  {
    return name;
  }

  public void setJavaName(String javaName)
  {
    this.javaName = javaName;
  }

  public String getJavaName()
  {
    return javaName;
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
