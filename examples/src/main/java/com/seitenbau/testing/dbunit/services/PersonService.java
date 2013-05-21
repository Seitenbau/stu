package com.seitenbau.testing.dbunit.services;

import java.util.LinkedList;
import java.util.List;

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
  PersonRepo personRepo = new PersonRepo();
  
  JobRepo jobRepo = new JobRepo();
  
  TeamRepo teamRepo = new TeamRepo();

  public List<Person> findPersons()
  {
    return personRepo.getAll();
  }

  public List<Person> findPersons(Team team)
  {
    List<Person> persons = new LinkedList<Person>();
    for (Person person : personRepo.getAll())
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
    return personRepo.add(person);
  }

  public boolean removePerson(Person person)
  {
    boolean result = false;
    if(personRepo.remove(person) > 0) {
      result = true;
    }
    return result;
  }

  public List<Job> findJobs()
  {
    return jobRepo.getAll();
  }

  public Job addJob(Job job)
  {
    return jobRepo.add(job);
  }

  public boolean removeJob(Job job)
  {
    boolean result = false;
    if(jobRepo.remove(job) > 0) {
      result = true;
    }
    return result;
  }

  public List<Team> findTeams()
  {
    return teamRepo.getAll();
  }

  public Team addTeam(Team team)
  {
    return teamRepo.add(team);
  }

  public boolean removeTeam(Team team)
  {
    boolean result = false;
    if(teamRepo.remove(team) > 0) {
      result = true;
    }
    return result;
  }

}
