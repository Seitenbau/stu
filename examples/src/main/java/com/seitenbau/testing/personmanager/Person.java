package com.seitenbau.testing.personmanager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

  @ManyToMany
  @JoinTable (
    name="person_job",
        joinColumns={@JoinColumn(name="person_id", referencedColumnName="id")},
        inverseJoinColumns={@JoinColumn(name="job_id", referencedColumnName="id")}
  )
  private List<Job> jobs;

  @Column(name = "team_id")
  private Integer team;

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

  public List<Job> getJobs()
  {
    return jobs;
  }

  public void setJobs(List<Job> jobs)
  {
    this.jobs = jobs;
  }
  
  public void addJob(Job job)
  {
    if(this.jobs == null) {
      this.jobs = new ArrayList<Job>();
    }
    this.jobs.add(job);
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
