package com.seitenbau.stu.database.modelgenerator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLModelGenerator {

  private static final int TABLE_NAME = 3;

  private static class ModelGenerator
  {
    private final Connection connection;
    private final DatabaseMetaData md;

    private ModelGenerator(final Connection connection)
    {
      this.connection = connection;
      md = getMetaData(connection);
    }

    private static DatabaseMetaData getMetaData(Connection connection)
    {
      try {
        return connection.getMetaData();
      }
      catch (Exception e)
      {
        return null;
      }
    }

    private String generate()
    {
      ResultSet rs = null;
      try {
        Map<String, TableModel> tables = new HashMap<String, TableModel>();

        List<TableModel> order = new ArrayList<TableModel>();

        rs = md.getTables(null, null, "%", null);
        while (rs.next()) {
          String tableName = rs.getString(TABLE_NAME);
          TableModel tableModel = new TableModel(tableName);
          tables.put(tableName, tableModel);
          order.add(tableModel);
          handleTable(tableModel);
        }
        rs.close();
        rs = null;

        // handle foreign keys
        try {
          for (TableModel table : order)
          {
            ResultSet foreignKeys = connection.getMetaData().getImportedKeys(connection.getCatalog(), null, table.getName());
            while (foreignKeys.next()) {
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

        StringBuilder result = new StringBuilder();
        for (TableModel table : order)
        {
          if (table.isCreated())
          {
            continue;
          }
          result.append(table.createJavaSource());
        }

        return result.toString();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally {
        try {
          if (rs != null) {
            rs.close();
          }
        }
        catch (Exception e)
        {
        }
        try {
          connection.close();
        }
        catch (Exception e)
        {
        }
      }
      return "";
    }

    private void handleTable(TableModel table)
    {
      ResultSet table_rs = null;
      try {
        table_rs = md.getColumns(null, null, table.getName(), "%");
        while(table_rs.next()) {
          ColumnMetaData metaData = new ColumnMetaData(table_rs);
          ColumnModel columnModel = new ColumnModel(metaData);
          table.addColumn(columnModel);
        }
        table_rs.close();
      }
      catch (Exception e)
      {
      }
      finally {
        try {
          if (table_rs != null) {
            table_rs.close();
          }
        }
        catch (Exception e)
        {
        }
      }
    }
  }

  public static String createModel(final Connection connection)
  {
    return new ModelGenerator(connection).generate();
  }

  public static void main(String[] args) {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/tests?user=root&password=");
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/bigdb?user=root&password=");
      System.out.println(SQLModelGenerator.createModel(connection));
    }
    catch (Exception e)
    {
      throw new RuntimeException("Cannot connect", e);
    }
  }

}