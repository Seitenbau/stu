package com.seitenbau.testing.dbunit.incubation;

import java.util.ArrayList;
import java.util.List;

public class DbTable
{
  public DbTable(String schem, String name)
  {
    fSchema = schem;
    fName = name;
  }

  public String getSchema()
  {
    return fSchema;
  }

  public void setSchema(String schema)
  {
    fSchema = schema;
  }

  public String getName()
  {
    return fName;
  }

  public void setName(String name)
  {
    fName = name;
  }

  private String fSchema;

  private String fName;

  private List<DbFKey> fFKeys = new ArrayList<DbFKey>();

  private List<DbTable> fReferencedBy = new ArrayList<DbTable>();

  public List<DbFKey> getFKeys()
  {
    return fFKeys;
  }

  public void addFk(DbTable fkdbTable, String fkColumn)
  {
    fFKeys.add(new DbFKey(fkdbTable, fkColumn));
  }

  public void addReferencedBy(DbTable table)
  {
    fReferencedBy.add(table);
  }

  public List<DbTable> getReferencedBy()
  {
    return fReferencedBy;
  }
}
