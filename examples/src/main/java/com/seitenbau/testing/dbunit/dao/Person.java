package com.seitenbau.testing.dbunit.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "persons")
public class Person
{
  @Id
  @GeneratedValue
  int id;

  @Column(name = "first_name")
  String firstName;

  @Column
  String name;

  @Column(name = "job_id")
  int job;

  @Column(name = "team_id")
  int team;

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public int getJob()
  {
    return job;
  }

  public void setJob(int job)
  {
    this.job = job;
  }

  public int getTeam()
  {
    return team;
  }

  public void setTeam(int team)
  {
    this.team = team;
  }

}
