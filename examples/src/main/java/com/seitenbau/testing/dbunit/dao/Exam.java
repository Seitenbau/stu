package com.seitenbau.testing.dbunit.dao;

import java.util.Date;

public class Exam
{
  Lecture correspondingLecture;

  String type;

  Date startsAt;

  public Lecture getCorrespondingLecture()
  {
    return correspondingLecture;
  }

  public void setCorrespondingLecture(Lecture correspondingLecture)
  {
    this.correspondingLecture = correspondingLecture;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public Date getStartsAt()
  {
    return startsAt;
  }

  public void setStartsAt(Date startsAt)
  {
    this.startsAt = startsAt;
  }
}
