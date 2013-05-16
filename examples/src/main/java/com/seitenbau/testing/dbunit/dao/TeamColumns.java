package com.seitenbau.testing.dbunit.dao;

public enum TeamColumns
{
  ID("id"), //
  TITLE("title"), //
  MEMBERSIZE("membersize"), //
  DESCRIPTION("description");

  private String columName;

  private TeamColumns(String columName)
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
