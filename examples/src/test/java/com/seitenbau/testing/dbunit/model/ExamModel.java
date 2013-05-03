package com.seitenbau.testing.dbunit.model;

import java.util.Date;
import com.seitenbau.testing.util.DateUtil;
import com.seitenbau.testing.util.date.DateBuilder;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;

/* *******************************************************
  Generated via : codegeneration.GenerateDatabaseClasses
**********************************************************/
public class ExamModel
{
  /** actual type : java.lang.Long */
  protected java.lang.Object id;
  /** actual type : java.lang.Long */
  protected java.lang.Object lectureId;
  /** actual type : java.lang.String */
  protected java.lang.Object lectureType;
  /** actual type : java.util.Date */
  protected java.lang.Object pointInTime;

  DatasetIdGenerator _generator;
  public void setIdGenerator(DatasetIdGenerator generator) 
  {
    _generator=generator;
  }

  public ExamModel setId(Integer intValue)
  {
    id = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public ExamModel setId(java.lang.Long value)
  {
    id = value;
    return this;
  }
  public ExamModel setIdRaw(Object value)
  {
    id =  value;
    return this;
  }
  public java.lang.Long getId()
  {
    return (java.lang.Long)  id;
  }
  public java.lang.Object getIdRaw()
  {
    return id;
  }
  public ExamModel nextId()
  {
    Long nextId = _generator.nextId("EXAM","id");
    setId(nextId);
    return this;
  }
  public ExamModel setLectureId(Integer intValue)
  {
    lectureId = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public ExamModel setLectureId(java.lang.Long value)
  {
    lectureId = value;
    return this;
  }
  public ExamModel setLectureIdRaw(Object value)
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
  public ExamModel setLectureType(java.lang.String value)
  {
    lectureType = value;
    return this;
  }
  public ExamModel setLectureTypeRaw(Object value)
  {
    lectureType =  value;
    return this;
  }
  public java.lang.String getLectureType()
  {
    return (java.lang.String)  lectureType;
  }
  public java.lang.Object getLectureTypeRaw()
  {
    return lectureType;
  }
  public ExamModel setPointInTime(String dateString)
  {
    pointInTime = DateUtil.asDate(dateString);
    return this;
  }
  public ExamModel setPointInTime(DateBuilder datum)
  {
    pointInTime = datum.asDate();
    return this;
  }
  public ExamModel setPointInTime(java.util.Date value)
  {
    pointInTime = value;
    return this;
  }
  public ExamModel setPointInTimeRaw(Object value)
  {
    pointInTime =  value;
    return this;
  }
  public java.util.Date getPointInTime()
  {
    return (java.util.Date)  pointInTime;
  }
  public java.lang.Object getPointInTimeRaw()
  {
    return pointInTime;
  }
 
}
