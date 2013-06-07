package com.seitenbau.testing.personmanager;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService
{
  @Autowired
  PersonRepository personRepo;

  @Autowired
  JobRepository jobRepo;

  @Autowired
  TeamRepository teamRepo;

  @Transactional
  public List<Person> findPersons()
  {
    return personRepo.findAll();
  }

  @Transactional
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

  @Transactional
  public Person addPerson(Person person)
  {
    Person addedPerson = personRepo.saveAndFlush(person);
    int teamId = addedPerson.getTeam();
    
    incrementMembersizeOfTeam(teamId);

    return addedPerson;
  }

  @Transactional
  public void removePerson(Person person)
  {
    personRepo.delete(person);
    
    int teamId = person.getTeam();
    decrementMembersizeOfTeam(teamId);
  }

  @Transactional
  public List<Job> findJobs()
  {
    return jobRepo.findAll();
  }

  @Transactional
  public Job addJob(Job job)
  {
    return jobRepo.saveAndFlush(job);
  }

  @Transactional
  public void removeJob(Job job)
  {
    jobRepo.delete(job);
  }

  @Transactional
  public List<Team> findTeams()
  {
    return teamRepo.findAll();
  }

  @Transactional
  public Team addTeam(Team team)
  {
    return teamRepo.saveAndFlush(team);
  }

  @Transactional
  public void removeTeam(Team team)
  {
    teamRepo.delete(team);
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
