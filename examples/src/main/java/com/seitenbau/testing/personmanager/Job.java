package com.seitenbau.testing.personmanager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jobs")
public class Job
{
  @Id
  @GeneratedValue
  int id;

  @Column
  String title;

  @Column
  String description;
  
  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }
  
  public void setId(Long id)
  {
    this.id = id.intValue();
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

}
