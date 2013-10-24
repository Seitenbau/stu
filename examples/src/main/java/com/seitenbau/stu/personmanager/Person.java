package com.seitenbau.stu.personmanager;

import java.util.Date;
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
@Table(name = "persons")
public class Person
{
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "persons_seq")
  @SequenceGenerator(name="persons_seq", sequenceName = "persons_seq")
  @Column(name="id")
  private Integer id;

  @Column(name = "first_name")
  private String firstName;

  @Column
  private String name;

  @Column(name = "team_id")
  private Integer team;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.person")
  private Set<PersonJob> jobs = new HashSet<PersonJob>();

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
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


  public Set<PersonJob> getJobs() {
      return jobs;
  }

  public void setJobs(Set<PersonJob> jobs) {
      this.jobs = jobs;
  }

  public void addJob(Job job)
  {
    PersonJob personJob = new PersonJob();
    personJob.setPerson(this);
    personJob.setJob(job);
    jobs.add(personJob);
  }

  public void addJob(Job job, Date engagementStart)
  {
    PersonJob personJob = new PersonJob();
    personJob.setPerson(this);
    personJob.setJob(job);
    personJob.setEngagementStart(engagementStart);
    jobs.add(personJob);
  }

  public Integer getTeam()
  {
    return team;
  }

  public void setTeam(Integer team)
  {
    this.team = team;
  }

}
