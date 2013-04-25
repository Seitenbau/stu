package com.seitenbau.testing.dbunit.incubation;

public class DbFKey
{

  public String getColumn()
  {
    return fColumn;
  }

  public void setColumn(String column)
  {
    fColumn = column;
  }

  private String fColumn;

  private DbTable fTable;

  public DbFKey(DbTable table, String fkKey)
  {
    fTable = table;
    fColumn = fkKey;
  }

  public DbTable getTable()
  {
    return fTable;
  }

  public void setTable(DbTable table)
  {
    fTable = table;
  }

}
