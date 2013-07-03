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

  @Autowired
  PersonJobRepository personJobRepo;

  @Transactional
  public List<PersonJob> findPersonJobs()
  {
    return personJobRepo.findAll();
  }

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
    for (PersonJob personJob : addedPerson.getJobs())
    {
      personJobRepo.saveAndFlush(personJob);
    }
    int team = addedPerson.getTeam();
    incrementMembersizeOfTeam(team);
    return addedPerson;
  }

  @Transactional
  public void removePerson(Person person)
  {
    Person dbPerson = getDatabasePerson(person);
    for (PersonJob personJob : findPersonJobs())
    {
      if (personJob.getPerson().getId() == dbPerson.getId())
      {
        personJobRepo.delete(personJob);
      }
    }

    personRepo.delete(dbPerson);

    int team = dbPerson.getTeam();
    decrementMembersizeOfTeam(team);
  }

  @Transactional
  public List<Job> findJobs()
  {
    return jobRepo.findAll();
  }

  @Transactional
  public Job addJob(Job job)
  {
    Job addedJob = jobRepo.saveAndFlush(job);
    for (PersonJob personJob : addedJob.getPersons())
    {
      personJobRepo.saveAndFlush(personJob);
    }
    return addedJob;
  }

  @Transactional
  public void removeJob(Job job)
  {
    Job dbJob = getDatabaseJob(job);
    for (PersonJob personJob : findPersonJobs())
    {
      if (personJob.getJob().getId() == dbJob.getId())
      {
        personJobRepo.delete(personJob);
      }
    }

    jobRepo.delete(dbJob);
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
    Team dbTeam = getDatabaseTeam(team);
    teamRepo.delete(dbTeam);
  }

  private void updateMembersizeOfTeam(int teamId, int amount)
  {
    Team team = findTeamById(teamId);
    if (team == null)
    {
      throw new RuntimeException("Team not in database");
    }

    int oldMembersize = team.getMembersize();
    team.setMembersize(oldMembersize + amount);
    teamRepo.saveAndFlush(team);
  }

  private void incrementMembersizeOfTeam(int team)
  {
    updateMembersizeOfTeam(team, 1);
  }

  private void decrementMembersizeOfTeam(int team)
  {
    updateMembersizeOfTeam(team, -1);
  }

  public Team findTeamById(long teamId)
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

  public Person findPersonById(long personId)
  {
    for (Person person : personRepo.findAll())
    {
      if (personId == person.getId())
      {
        return person;
      }
    }
    return null;
  }

  public Job findJobById(long jobId)
  {
    for (Job job : jobRepo.findAll())
    {
      if (jobId == job.getId())
      {
        return job;
      }
    }
    return null;
  }

  private Person getDatabasePerson(Person person)
  {
    Person dbPerson = findPersonById(person.getId());
    if (dbPerson == null) {
      throw new RuntimeException("Person does not exist");
    }

    // TODO NM think about comparing the persons...

    return dbPerson;
  }

  private Job getDatabaseJob(Job job)
  {
    Job dbJob = findJobById(job.getId());
    if (dbJob == null) {
      throw new RuntimeException("Job does not exist");
    }

    // TODO NM think about comparing the persons...

    return dbJob;
  }

  private Team getDatabaseTeam(Team team)
  {
    Team dbTeam = findTeamById(team.getId());
    if (dbTeam== null) {
      throw new RuntimeException("Team does not exist");
    }

    // TODO NM think about comparing the persons...

    return dbTeam;
  }
}
