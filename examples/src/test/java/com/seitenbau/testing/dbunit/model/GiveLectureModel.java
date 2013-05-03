package com.seitenbau.testing.dbunit.model;

import java.util.Date;
import com.seitenbau.testing.util.DateUtil;
import com.seitenbau.testing.util.date.DateBuilder;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;

/* *******************************************************
  Generated via : codegeneration.GenerateDatabaseClasses
**********************************************************/
public class GiveLectureModel
{
  /** actual type : java.lang.Long */
  protected java.lang.Object professorId;
  /** actual type : java.lang.Long */
  protected java.lang.Object examId;

  DatasetIdGenerator _generator;
  public void setIdGenerator(DatasetIdGenerator generator) 
  {
    _generator=generator;
  }

  public GiveLectureModel setProfessorId(Integer intValue)
  {
    professorId = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public GiveLectureModel setProfessorId(java.lang.Long value)
  {
    professorId = value;
    return this;
  }
  public GiveLectureModel setProfessorIdRaw(Object value)
  {
    professorId =  value;
    return this;
  }
  public java.lang.Long getProfessorId()
  {
    return (java.lang.Long)  professorId;
  }
  public java.lang.Object getProfessorIdRaw()
  {
    return professorId;
  }
  public GiveLectureModel setExamId(Integer intValue)
  {
    examId = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public GiveLectureModel setExamId(java.lang.Long value)
  {
    examId = value;
    return this;
  }
  public GiveLectureModel setExamIdRaw(Object value)
  {
    examId =  value;
    return this;
  }
  public java.lang.Long getExamId()
  {
    return (java.lang.Long)  examId;
  }
  public java.lang.Object getExamIdRaw()
  {
    return examId;
  }
 
}
