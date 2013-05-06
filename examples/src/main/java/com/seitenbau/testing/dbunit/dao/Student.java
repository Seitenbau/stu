package com.seitenbau.testing.dbunit.dao;

import java.util.Date;

public class Student
{
  String studentNumber;

  String name;

  String firstName;

  String degreeCourse;

  int semesterCount;

  Date enrolledSince;

  public String getStudentNumber()
  {
    return studentNumber;
  }

  public void setStudentNumber(String studentNumber)
  {
    this.studentNumber = studentNumber;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getDegreeCourse()
  {
    return degreeCourse;
  }

  public void setDegreeCourse(String degreeCourse)
  {
    this.degreeCourse = degreeCourse;
  }

  public int getSemesterCount()
  {
    return semesterCount;
  }

  public void setSemesterCount(int semesterCount)
  {
    this.semesterCount = semesterCount;
  }

  public Date getEnrolledSince()
  {
    return enrolledSince;
  }

  public void setEnrolledSince(Date enrolledSince)
  {
    this.enrolledSince = enrolledSince;
  }
}
