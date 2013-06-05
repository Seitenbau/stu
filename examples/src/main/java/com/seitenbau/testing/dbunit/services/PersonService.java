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
    Person addedPerson = personRepo.saveAndFlush(person);
    int teamId = addedPerson.getTeam();
    
    incrementMembersizeOfTeam(teamId);

    return addedPerson;
  }

  public void removePerson(Person person)
  {
    personRepo.delete(person);
    
    int teamId = person.getTeam();
    decrementMembersizeOfTeam(teamId);
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
  
  private void updateMembersizeOfTeam(int teamId, int amount)
  {
    Team team = findTeamForTeamId(teamId);

    if (team != null)
    {
      int oldMembersize = team.getMembersize();
      team.setMembersize(oldMembersize + amount);
      teamRepo.saveAndFlush(team);
    }
  }

  private Team findTeamForTeamId(int teamId)
  {
    for (Team team : teamRepo.findAll())
    {
      if (teamId == team.getId())
      {
        return team;
      }
    }
    return null;
  }
  
  private void incrementMembersizeOfTeam(int teamId)
  {
    updateMembersizeOfTeam(teamId, 1);
  }
  
  private void decrementMembersizeOfTeam(int teamId)
  {
    updateMembersizeOfTeam(teamId, -1);
  }
}
