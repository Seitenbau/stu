package com.seitenbau.stu.database.generator;

import com.google.common.base.Optional;

public class NameProvider
{

  private static final String TRUNCABLE_ID_SUFFIX = "_id";

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
  {    return getDataSetClass() + "Modifier";
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

  public String getRowModifyClass(Table table)
  {
    return "RowModify_" + table.getJavaName();
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

  public Optional<String> getTruncatedHeaderName(Column column)
  {
    final String headerName = column.getHeaderName();
    if (column.getRelation() == null || !headerName.endsWith(TRUNCABLE_ID_SUFFIX)
        && headerName.length() > TRUNCABLE_ID_SUFFIX.length())
    {
      return Optional.absent();
    }
    
    final String truncatedName = headerName.substring(0, headerName.length() - TRUNCABLE_ID_SUFFIX.length());
    for (Column otherColumn : column.getTable().getColumns())
    {
      if (truncatedName.equals(otherColumn.getHeaderName()))
      {
        // column cannot be truncated
        return Optional.absent();
      }
    }
    

    return Optional.of(truncatedName);
  }

}
