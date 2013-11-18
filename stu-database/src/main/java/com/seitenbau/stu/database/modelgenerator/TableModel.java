package com.seitenbau.stu.database.modelgenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TableModel
{
  private final String name;

  private final String javaVariableName;

  private final List<ColumnModel> columns;

  private boolean hasBeenCreated;

  private boolean isAboutToGenerate;

  public TableModel(String name)
  {
    this.name = name;
    this.javaVariableName = name.toLowerCase();
    hasBeenCreated = false;
    isAboutToGenerate = false;
    columns = new ArrayList<ColumnModel>();
  }

  public String getName()
  {
    return name;
  }

  public String createJavaSource()
  {
    StringBuilder result = new StringBuilder();
    isAboutToGenerate = true;
    String tableType = getTableType();

    // table order
    for (TableModel other : getTableDependencies())
    {
      if (other.isAboutToGenerate)
      {
        //throw new RuntimeException("Cyclomatic dependency handling " + this.name);
        // TODO NM fix this
        continue;
      }
      if (other.hasBeenCreated)
      {
        continue;
      }

      result.append(other.createJavaSource());
    }

    result.append("    ");

    // TODO NM check if other tables depend on this table
    result.append("Table " + javaVariableName + " = ");

    result.append(tableType + "(\"" + name + "\")\n");
    // result.append("        // .description(\"\");\n");

    for (ColumnModel column : columns)
    {
      result.append(column.getJavaCode());
    }

    result.append("      .build();\n");

    // .column("id", DataType.BIGINT) //
    // .defaultIdentifier() //
    // .autoInvokeNext() //
    // // .autoIncrement() // requires DBUnit 2.4.3 or later
    result.append("\n");

    hasBeenCreated = true;
    isAboutToGenerate = false;

    return result.toString();
  }

  private String getTableType()
  {
    if (getForeignKeyCount() == 2)
    {
      return "associativeTable";
    }
    return "table";
  }

  private int getForeignKeyCount()
  {
    int result = 0;
    for (ColumnModel column : columns)
    {
      if (column.hasForeignKey())
      {
        result++;
      }
    }
    return result;
  }

  public void addColumn(ColumnModel columnModel)
  {
    columns.add(columnModel);
  }

  public Set<TableModel> getTableDependencies()
  {
    Set<TableModel> result = new HashSet<TableModel>();
    for (ColumnModel column : columns)
    {
      if (column.hasForeignKey())
      {
        result.add(column.getForeignKey().getPkTable());
      }
    }

    return result;
  }

  public void addForeignKey(String columnName, TableModel pkTable, String pkColumnName)
  {
    for (ColumnModel column : columns)
    {
      if (column.getName().equals(columnName))
      {
        column.addForeignKey(pkTable, pkColumnName);
      }
    }
  }

  public boolean isCreated()
  {
    return hasBeenCreated;
  }
}
