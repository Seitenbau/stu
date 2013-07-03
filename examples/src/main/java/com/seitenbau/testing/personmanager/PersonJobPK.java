package com.seitenbau.testing.personmanager;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class PersonJobPK implements Serializable
{

  private static final long serialVersionUID = -548673996859816768L;

  private Person person;

  private Job job;

  @ManyToOne
  public Person getPerson()
  {
    return person;
  }

  public void setPerson(Person person)
  {
    this.person = person;
  }

  @ManyToOne
  public Job getJob()
  {
    return job;
  }

  public void setJob(Job job)
  {
    this.job = job;
  }

  public PersonJobPK(Person person, Job job)
  {
    this.person = person;
    this.job = job;
  }

  public PersonJobPK()
  {
  }

}