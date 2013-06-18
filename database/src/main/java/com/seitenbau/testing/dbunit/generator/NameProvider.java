package com.seitenbau.testing.dbunit.generator;

public class NameProvider
{

  private final String _name;

  private final String _package;

  public NameProvider(String name, String thePackage)
  {
    _name = name;
    _package = thePackage;

  }

  public String getName()
  {
    return _name;
  }

  public String getPackage()
  {
    return _package;
  }

  public String getDataSetClass()
  {
    return _name + DataSet.NAME_SUFFIX;
  }

  public String getDataSetModifierClass()
  {
    return getDataSetClass() + "Modifier";
  }

  public String getTableVariable(Table table)
  {
    return "table_" + table.getJavaName();
  }

  public String getTableAdapterClass(Table table)
  {
    return table.getJavaName() + "TableAdapter";
  }

  public String getTableAdapterVariable(Table table)
  {
    return table.getJavaNameFirstLower() + "Table";
  }

  public String getBuilderClass()
  {
    return _name + "Builder";
  }

  public String getTableClass(Table table)
  {
    return table.getJavaName() + Table.NAME_SUFFIX;
  }

  public String getRowBuilderClass(Table table)
  {
    return "RowBuilder_" + table.getJavaName();
  }

  public String getRowSettersClass(Table table)
  {
    return "RowSetters_" + table.getJavaName();
  }

  public String getRowGettersClass(Table table)
  {
    return "RowGetters_" + table.getJavaName();
  }

  public String getRowCollectionClass(Table table)
  {
    return "RowCollection_" + table.getJavaName();
  }

  public String getFindWhereClass(Table table)
  {
    return table.getJavaName() + "FindWhere";
  }

  public String getGetWhereClass(Table table)
  {
    return table.getJavaName() + "GetWhere";
  }

  public String getRefClass(Table table)
  {
    return table.getJavaName() + "Ref";
  }

  public String getRefVariable(Table table)
  {
    return table.getJavaNameFirstLower() + "Ref";
  }

  public String getRowModelClass(Table table)
  {
    return table.getJavaName() + "Model";
  }

}
