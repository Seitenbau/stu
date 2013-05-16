package com.seitenbau.testing.dbunit.dao;

public enum PersonColumns
{
  ID("id"), //
  FIRST_NAME("first_name"), //
  NAME("name"), //
  JOB_ID("job_id"), //
  TEAM_ID("team_id");

  private String columName;

  private PersonColumns(String columName)
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
