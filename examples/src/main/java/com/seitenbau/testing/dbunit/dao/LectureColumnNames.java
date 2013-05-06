package com.seitenbau.testing.dbunit.dao;

public enum LectureColumnNames
{
  ID("id"), //
  PROFESSOR_ID("professor_id"), //
  NAME("name"), //
  SWS("sws"), //
  ECTS("ects");

  private String columName;

  private LectureColumnNames(String columName)
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
