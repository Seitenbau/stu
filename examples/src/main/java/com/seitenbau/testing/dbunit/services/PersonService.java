package com.seitenbau.testing.dbunit.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seitenbau.testing.dbunit.dao.Job;
import com.seitenbau.testing.dbunit.dao.JobRepo;
import com.seitenbau.testing.dbunit.dao.Person;
import com.seitenbau.testing.dbunit.dao.PersonRepo;
import com.seitenbau.testing.dbunit.dao.Team;
import com.seitenbau.testing.dbunit.dao.TeamRepo;

@Service
public class PersonService
{
  @Autowired
  PersonRepo personRepo;

  @Autowired
  JobRepo jobRepo;

  @Autowired
  TeamRepo teamRepo;

  public List<Person> findPersons()
  {
    return personRepo.findAll();
  }

  public List<Person> findPersons(Team team)
  {
    List<Person> persons = new LinkedList<Person>();
    for (Person person : personRepo.findAll())
    {
      if (person.getTeam() == team.getId())
      {
        persons.add(person);
      }
    }
    return persons;
  }

  public Person addPerson(Person person)
  {
    return personRepo.saveAndFlush(person);
  }

  public void removePerson(Person person)
  {
    personRepo.delete(person);
  }

  public List<Job> findJobs()
  {
    return jobRepo.findAll();
  }

  public Job addJob(Job job)
  {
    return jobRepo.saveAndFlush(job);
  }

  public void removeJob(Job job)
  {
    jobRepo.delete(job);
  }

  public List<Team> findTeams()
  {
    return teamRepo.findAll();
  }

  public Team addTeam(Team team)
  {
    return teamRepo.saveAndFlush(team);
  }

  public void removeTeam(Team team)
  {
    teamRepo.delete(team);
  }

  public void setPersonRepo(PersonRepo personRepo)
  {
    this.personRepo = personRepo;
  }

  public void setJobRepo(JobRepo jobRepo)
  {
    this.jobRepo = jobRepo;
  }

  public void setTeamRepo(TeamRepo teamRepo)
  {
    this.teamRepo = teamRepo;
  }
}
