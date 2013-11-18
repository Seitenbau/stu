package com.seitenbau.stu.database.modelgenerator;

public class ForeignKeyModel
{
  TableModel pkTable;

  String pkColumnName;

  public ForeignKeyModel(TableModel pkTable, String pkColumnName)
  {
    this.pkTable = pkTable;
    this.pkColumnName = pkColumnName;
  }

  public String getPkTableName()
  {
    return pkTable.getName();
  }

  public String getPkColumnName()
  {
    return pkColumnName;
  }

  @Override
  public String toString()
  {
    return getPkTableName() + ".ref(\"" + pkColumnName + "\")";
  }

  public TableModel getPkTable()
  {
    return pkTable;
  }
}
