package com.seitenbau.testing.dbunit.demomodel;

import java.util.Date;
import com.seitenbau.testing.util.DateUtil;
import com.seitenbau.testing.util.date.DateBuilder;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;

/* *******************************************************
  Generated via : codegeneration.GenerateDatabaseClasses
**********************************************************/
public class PersonsModel
{
  /** actual type : java.lang.Long */
  protected java.lang.Object id;
  /** actual type : java.lang.String */
  protected java.lang.Object firstName;
  /** actual type : java.lang.String */
  protected java.lang.Object name;
  /** actual type : java.lang.Long */
  protected java.lang.Object jobId;
  /** actual type : java.lang.Long */
  protected java.lang.Object teamId;

  DatasetIdGenerator _generator;
  public void setIdGenerator(DatasetIdGenerator generator) 
  {
    _generator=generator;
  }

  public PersonsModel setId(Integer intValue)
  {
    id = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public PersonsModel setId(java.lang.Long value)
  {
    id = value;
    return this;
  }
  public PersonsModel setIdRaw(Object value)
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
  public PersonsModel nextId()
  {
    Long nextId = _generator.nextId("persons","id");
    setId(nextId);
    return this;
  }
  public PersonsModel setFirstName(java.lang.String value)
  {
    firstName = value;
    return this;
  }
  public PersonsModel setFirstNameRaw(Object value)
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
  public PersonsModel setName(java.lang.String value)
  {
    name = value;
    return this;
  }
  public PersonsModel setNameRaw(Object value)
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
  public PersonsModel setJobId(Integer intValue)
  {
    jobId = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public PersonsModel setJobId(java.lang.Long value)
  {
    jobId = value;
    return this;
  }
  public PersonsModel setJobIdRaw(Object value)
  {
    jobId =  value;
    return this;
  }
  public java.lang.Long getJobId()
  {
    return (java.lang.Long)  jobId;
  }
  public java.lang.Object getJobIdRaw()
  {
    return jobId;
  }
  public PersonsModel setTeamId(Integer intValue)
  {
    teamId = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public PersonsModel setTeamId(java.lang.Long value)
  {
    teamId = value;
    return this;
  }
  public PersonsModel setTeamIdRaw(Object value)
  {
    teamId =  value;
    return this;
  }
  public java.lang.Long getTeamId()
  {
    return (java.lang.Long)  teamId;
  }
  public java.lang.Object getTeamIdRaw()
  {
    return teamId;
  }
 
}
