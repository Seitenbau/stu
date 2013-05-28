package com.seitenbau.testing.dbunit.demomodel;

import java.util.Date;
import com.seitenbau.testing.util.DateUtil;
import com.seitenbau.testing.util.date.DateBuilder;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;

/* *******************************************************
  Generated via : codegeneration.GenerateDatabaseClasses
**********************************************************/
public class TeamsModel
{
  /** actual type : java.lang.Long */
  protected java.lang.Object id;
  /** actual type : java.lang.String */
  protected java.lang.Object title;
  /** actual type : java.lang.String */
  protected java.lang.Object description;
  /** actual type : java.lang.Long */
  protected java.lang.Object membersize;

  DatasetIdGenerator _generator;
  public void setIdGenerator(DatasetIdGenerator generator) 
  {
    _generator=generator;
  }

  public TeamsModel setId(Integer intValue)
  {
    id = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public TeamsModel setId(java.lang.Long value)
  {
    id = value;
    return this;
  }
  public TeamsModel setIdRaw(Object value)
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
  public TeamsModel nextId()
  {
    Long nextId = _generator.nextId("teams","id");
    setId(nextId);
    return this;
  }
  public TeamsModel setTitle(java.lang.String value)
  {
    title = value;
    return this;
  }
  public TeamsModel setTitleRaw(Object value)
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
  public TeamsModel setDescription(java.lang.String value)
  {
    description = value;
    return this;
  }
  public TeamsModel setDescriptionRaw(Object value)
  {
    description =  value;
    return this;
  }
  public java.lang.String getDescription()
  {
    return (java.lang.String)  description;
  }
  public java.lang.Object getDescriptionRaw()
  {
    return description;
  }
  public TeamsModel setMembersize(Integer intValue)
  {
    membersize = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public TeamsModel setMembersize(java.lang.Long value)
  {
    membersize = value;
    return this;
  }
  public TeamsModel setMembersizeRaw(Object value)
  {
    membersize =  value;
    return this;
  }
  public java.lang.Long getMembersize()
  {
    return (java.lang.Long)  membersize;
  }
  public java.lang.Object getMembersizeRaw()
  {
    return membersize;
  }
 
}
