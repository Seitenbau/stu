package com.seitenbau.testing.personmanager;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "jobs")
public class Job
{
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "jobs_seq")
  @SequenceGenerator(name="jobs_seq", sequenceName = "jobs_seq")
  @Column(name="id")
  int id;

  @Column
  String title;

  @Column
  String description;
  
  @ManyToMany(mappedBy="jobs")
  private List<Person> persons;
  
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

  public List<Person> getPersons()
  {
    return persons;
  }

  public void setPersons(List<Person> persons)
  {
    this.persons = persons;
  }
}
