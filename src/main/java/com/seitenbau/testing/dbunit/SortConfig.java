package com.seitenbau.testing.dbunit;

public class SortConfig
{
  private String tablename;
  public String getTablename()
  {
    return tablename;
  }

  public String[] getColumnOrder()
  {
    return columnOrder;
  }

  private String[] columnOrder;

  public SortConfig(String tableName, String... columnOrders)
  {
    this.tablename = tableName;
    this.columnOrder = columnOrders;
  }
}
