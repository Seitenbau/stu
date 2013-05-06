package com.seitenbau.testing.dbunit.dao;

public class Lecture
{
  int id;
  
  int givenBy;

  String title;

  int weeklyHours;

  int semesterCredits;

  public int getGivenBy()
  {
    return givenBy;
  }

  public void setGivenBy(int givenBy)
  {
    this.givenBy = givenBy;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public int getWeeklyHours()
  {
    return weeklyHours;
  }

  public void setWeeklyHours(int weeklyHours)
  {
    this.weeklyHours = weeklyHours;
  }

  public int getSemesterCredits()
  {
    return semesterCredits;
  }

  public void setSemesterCredits(int semesterCredits)
  {
    this.semesterCredits = semesterCredits;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }
}
