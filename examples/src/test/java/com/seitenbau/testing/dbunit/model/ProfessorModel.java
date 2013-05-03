package com.seitenbau.testing.dbunit.model;

import java.util.Date;
import com.seitenbau.testing.util.DateUtil;
import com.seitenbau.testing.util.date.DateBuilder;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;

/* *******************************************************
  Generated via : codegeneration.GenerateDatabaseClasses
**********************************************************/
public class ProfessorModel
{
  /** actual type : java.lang.Long */
  protected java.lang.Object id;
  /** actual type : java.lang.String */
  protected java.lang.Object name;
  /** actual type : java.lang.String */
  protected java.lang.Object firstName;
  /** actual type : java.lang.String */
  protected java.lang.Object title;
  /** actual type : java.lang.String */
  protected java.lang.Object faculty;

  DatasetIdGenerator _generator;
  public void setIdGenerator(DatasetIdGenerator generator) 
  {
    _generator=generator;
  }

  public ProfessorModel setId(Integer intValue)
  {
    id = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public ProfessorModel setId(java.lang.Long value)
  {
    id = value;
    return this;
  }
  public ProfessorModel setIdRaw(Object value)
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
  public ProfessorModel nextId()
  {
    Long nextId = _generator.nextId("professor","id");
    setId(nextId);
    return this;
  }
  public ProfessorModel setName(java.lang.String value)
  {
    name = value;
    return this;
  }
  public ProfessorModel setNameRaw(Object value)
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
  public ProfessorModel setFirstName(java.lang.String value)
  {
    firstName = value;
    return this;
  }
  public ProfessorModel setFirstNameRaw(Object value)
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
  public ProfessorModel setTitle(java.lang.String value)
  {
    title = value;
    return this;
  }
  public ProfessorModel setTitleRaw(Object value)
  {
    title =  value;
    return this;
  }
  public java.lang.String getTitle()
  {
    return (java.lang.String)  title;
  }
  public java.lang.Object getTitleRaw()
  {
    return title;
  }
  public ProfessorModel setFaculty(java.lang.String value)
  {
    faculty = value;
    return this;
  }
  public ProfessorModel setFacultyRaw(Object value)
  {
    faculty =  value;
    return this;
  }
  public java.lang.String getFaculty()
  {
    return (java.lang.String)  faculty;
  }
  public java.lang.Object getFacultyRaw()
  {
    return faculty;
  }
 
}
