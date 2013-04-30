package dao;

public class Lecture
{
  Professor givenBy;

  String title;

  int weeklyHours;

  int semesterCredits;

  public Professor getHoldBy()
  {
    return givenBy;
  }

  public void setHoldBy(Professor holdBy)
  {
    this.givenBy = holdBy;
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

}
