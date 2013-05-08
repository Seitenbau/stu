package com.seitenbau.testing.dbunit.services.intern;

import java.util.List;

import org.springframework.stereotype.Service;

import com.seitenbau.testing.dbunit.dao.Job;
import com.seitenbau.testing.dbunit.dao.JobRepo;
import com.seitenbau.testing.dbunit.dao.Person;
import com.seitenbau.testing.dbunit.dao.PersonRepo;
import com.seitenbau.testing.dbunit.dao.Team;
import com.seitenbau.testing.dbunit.dao.TeamRepo;
import com.seitenbau.testing.dbunit.services.PersonService;

@Service
public class PersonServiceImpl implements PersonService
{
  PersonRepo personRepo = new PersonRepo();
  
  JobRepo jobRepo = new JobRepo();
  
  TeamRepo teamRepo = new TeamRepo();

  @Override
  public List<Person> findPersons()
  {
    return personRepo.getAll();
  }

  @Override
  public List<Person> findPersons(Team team)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Person addPerson(Person person)
  {
    return personRepo.add(person);
  }

  @Override
  public boolean removePerson(Person person)
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<Job> findJobs()
  {
    return jobRepo.getAll();
  }

  @Override
  public Job addJob(Job job)
  {
    return jobRepo.add(job);
  }

  @Override
  public boolean removeJob(Job job)
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<Team> findTeams()
  {
    return teamRepo.getAll();
  }

  @Override
  public Team addTeam(Team team)
  {
    return teamRepo.add(team);
  }

  @Override
  public boolean removeTeam(Team team)
  {
    // TODO Auto-generated method stub
    return false;
  }

}
