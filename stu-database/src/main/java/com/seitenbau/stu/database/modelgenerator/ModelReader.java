package com.seitenbau.stu.database.modelgenerator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ModelReader
{
  private static final int TABLE_NAME = 3;

  private final Connection connection;

  private final DatabaseMetaData md;

  private ModelReader(final Connection connection)
  {
    this.connection = connection;
    md = getMetaData(connection);
  }

  private static DatabaseMetaData getMetaData(Connection connection)
  {
    try
    {
      return connection.getMetaData();
    }
    catch (Exception e)
    {
      return null;
    }
  }

  private DatabaseModel read()
  {
    DatabaseModel result = new DatabaseModel();
    ResultSet rs = null;
    try
    {
      Map<String, TableModel> tables = new HashMap<String, TableModel>();

      rs = md.getTables(null, null, "%", null);
      while (rs.next())
      {
        String tableName = rs.getString(TABLE_NAME);
        TableModel tableModel = new TableModel(tableName);
        tables.put(tableName, tableModel);
        result.add(tableModel);
        handleTable(tableModel);
      }
      rs.close();
      rs = null;

      // handle foreign keys
      try
      {
        for (TableModel table : result.getTables())
        {
          ResultSet foreignKeys = connection.getMetaData().getImportedKeys(connection.getCatalog(), null,
              table.getName());
          while (foreignKeys.next())
          {
            String columnName = foreignKeys.getString("FKCOLUMN_NAME");
            TableModel pkTable = tables.get(foreignKeys.getString("PKTABLE_NAME"));
            String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
            table.addForeignKey(columnName, pkTable, pkColumnName);
          }
        }
      }
      catch (Exception e)
      {
      }

      return result;
    }
    catch (Exception e)
    {
      throw new RuntimeException("Error querying database scheme", e);
    }
    finally
    {
      try
      {
        if (rs != null)
        {
          rs.close();
        }
      }
      catch (Exception e)
      {
      }
      try
      {
        connection.close();
      }
      catch (Exception e)
      {
      }
    }
  }

  private void handleTable(TableModel table)
  {
    ResultSet table_rs = null;
    try
    {
      table_rs = md.getColumns(null, null, table.getName(), "%");
      while (table_rs.next())
      {
        ColumnMetaData metaData = new ColumnMetaData(table_rs);
        ColumnModel columnModel = new ColumnModel(metaData);
        table.addColumn(columnModel);
      }
      table_rs.close();
    }
    catch (Exception e)
    {
    }
    finally
    {
      try
      {
        if (table_rs != null)
        {
          table_rs.close();
        }
      }
      catch (Exception e)
      {
      }
    }
  }

  public static DatabaseModel readModel(Connection connection)
  {
    return new ModelReader(connection).read();
  }

}
