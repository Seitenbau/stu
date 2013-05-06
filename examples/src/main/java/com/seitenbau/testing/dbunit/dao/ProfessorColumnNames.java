package com.seitenbau.testing.dbunit.dao;

public enum ProfessorColumnNames
{
  ID("id"), //
  NAME("name"), //
  FIRST_NAME("first_name"), //
  TITLE("title"), //
  FACULTY("faculty");

  private String columName;

  private ProfessorColumnNames(String columName)
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
