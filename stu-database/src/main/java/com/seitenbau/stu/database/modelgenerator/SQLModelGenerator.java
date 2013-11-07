package com.seitenbau.stu.database.modelgenerator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class SQLModelGenerator {

  private static final int TABLE_NAME = 3;

  public static void createModel()
  {
    final Connection connection = createConnection();
    if (connection == null) {
      return;
    }

    ResultSet rs = null;
    ResultSet table_rs = null;
    try {
      final DatabaseMetaData md = connection.getMetaData();

      rs = md.getTables(null, null, "%", null);
      while (rs.next()) {
        String table = rs.getString(TABLE_NAME);
        System.out.println("TABLE: " + table);
        table_rs = md.getColumns(null, null, table, "%");
        while(table_rs.next()) {
          ColumnMetaData metaData = new ColumnMetaData(table_rs);
          final SQLColumnType dataType = metaData.getDataType();
          final String columnName = metaData.getColumnName();
          System.out.println("  COLUMN: " + columnName);
          System.out.println("    dataType:" + dataType);
          System.out.println("    dataTypeName:" + metaData.getDataTypeName());
          System.out.println("    Column Size: " + metaData.getColumnSize());
          System.out.println("    getDecimalDigits: " + metaData.getDecimalDigits());
          System.out.println("    getCharOctedLength: " + metaData.getCharOctedLength());
          System.out.println("    Column Default: " + metaData.getColumnDefault());
          System.out.println("    isAutoIncrement: " + metaData.isAutoIncrement());
          System.out.println("    isGeneradedColumn: " + metaData.isGeneradedColumn());
          System.out.println("    isNullable: " + metaData.isNullable());
          System.out.println("    getNullable: " + metaData.getNullable());

          ResultSet foreignKeys = md.getImportedKeys(connection.getCatalog(), null, table);
          while (foreignKeys.next()) {
              if (columnName.equals(foreignKeys.getString("FKCOLUMN_NAME"))) {
                String pkTableName = foreignKeys.getString("PKTABLE_NAME");
                String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
                System.out.println("    Foreign-Key to " + pkTableName + "." + pkColumnName);
              }
          }
        }
        table_rs.close();
        table_rs = null;

        System.out.println();
      }
      rs.close();
      rs = null;
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
  }

  private static Connection createConnection()
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      return DriverManager.getConnection("jdbc:mysql://localhost/tests?user=root&password=");
    }
    catch (Exception e)
    {
      throw new RuntimeException("Cannot connect", e);
    }
  }

  public static void main(String[] args) {
    SQLModelGenerator.createModel();
  }

}