package com.seitenbau.testing.dbunit.model;

import java.util.Date;
import com.seitenbau.testing.util.DateUtil;
import com.seitenbau.testing.util.date.DateBuilder;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;

/* *******************************************************
  Generated via : codegeneration.GenerateDatabaseClasses
**********************************************************/
public class LectureModel
{
  /** actual type : java.lang.Long */
  protected java.lang.Object id;
  /** actual type : java.lang.Long */
  protected java.lang.Object professorId;
  /** actual type : java.lang.String */
  protected java.lang.Object name;
  /** actual type : java.lang.Integer */
  protected java.lang.Object sws;
  /** actual type : java.lang.Integer */
  protected java.lang.Object ects;

  DatasetIdGenerator _generator;
  public void setIdGenerator(DatasetIdGenerator generator) 
  {
    _generator=generator;
  }

  public LectureModel setId(Integer intValue)
  {
    id = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public LectureModel setId(java.lang.Long value)
  {
    id = value;
    return this;
  }
  public LectureModel setIdRaw(Object value)
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
  public LectureModel nextId()
  {
    Long nextId = _generator.nextId("LECTURE","id");
    setId(nextId);
    return this;
  }
  public LectureModel setProfessorId(Integer intValue)
  {
    professorId = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public LectureModel setProfessorId(java.lang.Long value)
  {
    professorId = value;
    return this;
  }
  public LectureModel setProfessorIdRaw(Object value)
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
  public LectureModel setName(java.lang.String value)
  {
    name = value;
    return this;
  }
  public LectureModel setNameRaw(Object value)
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
  public LectureModel setSws(java.lang.Integer value)
  {
    sws = value;
    return this;
  }
  public LectureModel setSwsRaw(Object value)
  {
    sws =  value;
    return this;
  }
  public java.lang.Integer getSws()
  {
    return (java.lang.Integer)  sws;
  }
  public java.lang.Object getSwsRaw()
  {
    return sws;
  }
  public LectureModel setEcts(java.lang.Integer value)
  {
    ects = value;
    return this;
  }
  public LectureModel setEctsRaw(Object value)
  {
    ects =  value;
    return this;
  }
  public java.lang.Integer getEcts()
  {
    return (java.lang.Integer)  ects;
  }
  public java.lang.Object getEctsRaw()
  {
    return ects;
  }
 
}
