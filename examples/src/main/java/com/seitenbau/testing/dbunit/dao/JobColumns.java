package com.seitenbau.testing.dbunit.dao;

public enum JobColumns
{
  ID("id"), //
  TITLE("title"), //
  DESCRIPTION("description");

  private String columName;

  private JobColumns(String columName)
  {
    this.columName = columName;
  }

  public String getColumnName()
  {
    return columName;
  }

  @Override
  public String toString()
  {
    return columName;
  }
}
