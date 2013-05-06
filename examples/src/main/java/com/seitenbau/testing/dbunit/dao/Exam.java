package com.seitenbau.testing.dbunit.dao;

import java.util.Date;

public class Exam
{
  int correspondingLectureId;

  String type;

  Date startsAt;

  public int getCorrespondingLectureId()
  {
    return correspondingLectureId;
  }

  public void setCorrespondingLectureId(int correspondingLectureId)
  {
    this.correspondingLectureId = correspondingLectureId;
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
