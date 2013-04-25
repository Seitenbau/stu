package com.seitenbau.testing.dbunit.incubation;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseMeta
{
  private Map<String, DbTable> tableMap = new HashMap<String, DbTable>();

  public DatabaseMeta(Connection conn, String schema) throws SQLException
  {
    DatabaseMetaData meta = conn.getMetaData();

    ResultSet rs = meta.getTables(conn.getCatalog(), schema, null,
        new String[] {"TABLE"});

    while (rs.next())
    {
      // String cat = fRs.getString("TABLE_CAT");
      String schem = rs.getString("TABLE_SCHEM");
      String name = rs.getString("TABLE_NAME");
      // String type = fRs.getString("TABLE_TYPE");
      addTable(schem, name);
    }

    for (DbTable table : metaData)
    {
      ResultSet data = meta.getExportedKeys(conn.getCatalog(), table
          .getSchema(), table.getName());

      while (data.next())
      {
        String fkSchema = data.getString("FKTABLE_SCHEM");
        String fkTable = data.getString("FKTABLE_NAME");
        String fkKey = data.getString("FKCOLUMN_NAME");
        DbTable fkdbTable = tableMap.get(fkSchema + "." + fkTable);
        if (fkdbTable == null)
        {
          System.err.println("fail for table : " + fkSchema + "." + fkTable);
        }
        else
        {
          table.addFk(fkdbTable, fkKey);
          fkdbTable.addReferencedBy(table);
        }
      }
    }
  }

  private void addTable(String schema, String name)
  {
    DbTable table = new DbTable(schema, name);
    metaData.add(table);
    tableMap.put(schema + "." + name, table);
  }

  private List<DbTable> metaData = new ArrayList<DbTable>();

  public List<DbTable> getTables()
  {
    return metaData;
  }

  public void sortByForeignKeys()
  {
    Collections.sort(metaData, new Comparator<DbTable>()
    {
      public int compare(DbTable o1, DbTable o2)
      {
        return o1.getFKeys().size() - o2.getFKeys().size();
      }
    });
  }

  public void sortByReferences()
  {
    Collections.sort(metaData, new Comparator<DbTable>()
    {
      public int compare(DbTable o1, DbTable o2)
      {
        return o1.getReferencedBy().size() - o2.getReferencedBy().size();
      }
    });
  }

  public List<String> getTableNames()
  {
    List<String> result = new ArrayList<String>();
    for (DbTable table : metaData)
    {
      result.add(table.getName());
    }
    return result;
  }

}
