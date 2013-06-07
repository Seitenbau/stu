package com.seitenbau.testing.personmanager;

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
  Long job;

  @Column(name = "team_id")
  Long team;

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
    return job.intValue();
  }

  public void setJob(int job)
  {
    this.job = Long.valueOf(job);
  }
  
  public void setJob(Long job)
  {
    this.job = job;
  }

  public int getTeam()
  {
    return team.intValue();
  }

  public void setTeam(int team)
  {
    this.team = Long.valueOf(team);
  }
  
  public void setTeam(Long team)
  {
    this.team = team;
  }

}
