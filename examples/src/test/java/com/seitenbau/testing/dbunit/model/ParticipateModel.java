package com.seitenbau.testing.dbunit.model;

import java.util.Date;
import com.seitenbau.testing.util.DateUtil;
import com.seitenbau.testing.util.date.DateBuilder;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;

/* *******************************************************
  Generated via : codegeneration.GenerateDatabaseClasses
**********************************************************/
public class ParticipateModel
{
  /** actual type : java.lang.Long */
  protected java.lang.Object studentId;
  /** actual type : java.lang.Long */
  protected java.lang.Object lectureId;

  DatasetIdGenerator _generator;
  public void setIdGenerator(DatasetIdGenerator generator) 
  {
    _generator=generator;
  }

  public ParticipateModel setStudentId(Integer intValue)
  {
    studentId = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public ParticipateModel setStudentId(java.lang.Long value)
  {
    studentId = value;
    return this;
  }
  public ParticipateModel setStudentIdRaw(Object value)
  {
    studentId =  value;
    return this;
  }
  public java.lang.Long getStudentId()
  {
    return (java.lang.Long)  studentId;
  }
  public java.lang.Object getStudentIdRaw()
  {
    return studentId;
  }
  public ParticipateModel setLectureId(Integer intValue)
  {
    lectureId = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public ParticipateModel setLectureId(java.lang.Long value)
  {
    lectureId = value;
    return this;
  }
  public ParticipateModel setLectureIdRaw(Object value)
  {
    lectureId =  value;
    return this;
  }
  public java.lang.Long getLectureId()
  {
    return (java.lang.Long)  lectureId;
  }
  public java.lang.Object getLectureIdRaw()
  {
    return lectureId;
  }
 
}
