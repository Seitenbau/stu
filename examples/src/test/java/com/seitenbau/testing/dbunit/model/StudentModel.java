package com.seitenbau.testing.dbunit.model;

import java.util.Date;
import com.seitenbau.testing.util.DateUtil;
import com.seitenbau.testing.util.date.DateBuilder;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;

/* *******************************************************
  Generated via : codegeneration.GenerateDatabaseClasses
**********************************************************/
public class StudentModel
{
  /** actual type : java.lang.Long */
  protected java.lang.Object studentNumber;
  /** actual type : java.lang.String */
  protected java.lang.Object name;
  /** actual type : java.lang.String */
  protected java.lang.Object firstName;
  /** actual type : java.lang.String */
  protected java.lang.Object degreeCourse;
  /** actual type : java.lang.Integer */
  protected java.lang.Object semester;
  /** actual type : java.util.Date */
  protected java.lang.Object enrolledSince;

  DatasetIdGenerator _generator;
  public void setIdGenerator(DatasetIdGenerator generator) 
  {
    _generator=generator;
  }

  public StudentModel setStudentNumber(Integer intValue)
  {
    studentNumber = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public StudentModel setStudentNumber(java.lang.Long value)
  {
    studentNumber = value;
    return this;
  }
  public StudentModel setStudentNumberRaw(Object value)
  {
    studentNumber =  value;
    return this;
  }
  public java.lang.Long getStudentNumber()
  {
    return (java.lang.Long)  studentNumber;
  }
  public java.lang.Object getStudentNumberRaw()
  {
    return studentNumber;
  }
  public StudentModel nextStudentNumber()
  {
    Long nextId = _generator.nextId("STUDENT","student_number");
    setStudentNumber(nextId);
    return this;
  }
  public StudentModel setName(java.lang.String value)
  {
    name = value;
    return this;
  }
  public StudentModel setNameRaw(Object value)
  {
    name =  value;
    return this;
  }
  public java.lang.String getName()
  {
    return (java.lang.String)  name;
  }
  public java.lang.Object getNameRaw()
  {
    return name;
  }
  public StudentModel setFirstName(java.lang.String value)
  {
    firstName = value;
    return this;
  }
  public StudentModel setFirstNameRaw(Object value)
  {
    firstName =  value;
    return this;
  }
  public java.lang.String getFirstName()
  {
    return (java.lang.String)  firstName;
  }
  public java.lang.Object getFirstNameRaw()
  {
    return firstName;
  }
  public StudentModel setDegreeCourse(java.lang.String value)
  {
    degreeCourse = value;
    return this;
  }
  public StudentModel setDegreeCourseRaw(Object value)
  {
    degreeCourse =  value;
    return this;
  }
  public java.lang.String getDegreeCourse()
  {
    return (java.lang.String)  degreeCourse;
  }
  public java.lang.Object getDegreeCourseRaw()
  {
    return degreeCourse;
  }
  public StudentModel setSemester(java.lang.Integer value)
  {
    semester = value;
    return this;
  }
  public StudentModel setSemesterRaw(Object value)
  {
    semester =  value;
    return this;
  }
  public java.lang.Integer getSemester()
  {
    return (java.lang.Integer)  semester;
  }
  public java.lang.Object getSemesterRaw()
  {
    return semester;
  }
  public StudentModel setEnrolledSince(String dateString)
  {
    enrolledSince = DateUtil.asDate(dateString);
    return this;
  }
  public StudentModel setEnrolledSince(DateBuilder datum)
  {
    enrolledSince = datum.asDate();
    return this;
  }
  public StudentModel setEnrolledSince(java.util.Date value)
  {
    enrolledSince = value;
    return this;
  }
  public StudentModel setEnrolledSinceRaw(Object value)
  {
    enrolledSince =  value;
    return this;
  }
  public java.util.Date getEnrolledSince()
  {
    return (java.util.Date)  enrolledSince;
  }
  public java.lang.Object getEnrolledSinceRaw()
  {
    return enrolledSince;
  }
 
}
