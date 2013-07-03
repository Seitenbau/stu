package com.seitenbau.testing.personmanager;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "jobs")
public class Job
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobs_seq")
  @SequenceGenerator(name = "jobs_seq", sequenceName = "jobs_seq")
  @Column(name = "id")
  int id;

  @Column
  String title;

  @Column
  String description;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.job")
  private Set<PersonJob> persons = new HashSet<PersonJob>();

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

  public void addPerson(Person person)
  {
    PersonJob personJob = new PersonJob();
    personJob.setPerson(person);
    personJob.setJob(this);
    persons.add(personJob);
  }

  public Set<PersonJob> getPersons()
  {
    return persons;
  }

  public void setPersons(Set<PersonJob> persons)
  {
    this.persons = persons;
  }

}
