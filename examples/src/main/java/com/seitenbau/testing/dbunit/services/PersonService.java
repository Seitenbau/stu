package com.seitenbau.testing.dbunit.services;

import java.util.List;

import com.seitenbau.testing.dbunit.dao.Job;
import com.seitenbau.testing.dbunit.dao.Person;
import com.seitenbau.testing.dbunit.dao.Team;

public interface PersonService
{
  public List<Person> findPersons();

  public List<Person> findPersons(Team team);

  public Person addPerson(Person person);

  public boolean removePerson(Person person);

  public List<Job> findJobs();

  public Job addJob(Job job);

  public boolean removeJob(Job job);

  public List<Team> findTeams();

  public Team addTeam(Team team);

  public boolean removeTeam(Team team);

}
