package com.seitenbau.testing.dbunit;

import static org.fest.assertions.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seitenbau.testing.dbunit.config.TestConfig;
import com.seitenbau.testing.dbunit.dao.Job;
import com.seitenbau.testing.dbunit.dao.Person;
import com.seitenbau.testing.dbunit.dao.Team;
import com.seitenbau.testing.dbunit.datasets.DefaultDataSet;
import com.seitenbau.testing.dbunit.datasets.EmptyDataSet;
import com.seitenbau.testing.dbunit.model.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.testing.dbunit.services.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/config/spring/context.xml", "/config/spring/test-context.xml"})
public class ManualDatabaseTester
{
  @Rule
  public DatabaseTesterRule dbTesterRule = new DatabaseTesterRule(TestConfig.class);

  @Autowired
  PersonService sut;

  @Test
  public void findAllPersonsOnEmptyDataset() throws Exception
  {
    // prepare
    List<Person> expected = new LinkedList<Person>();
    EmptyDataSet emptyDataset = new EmptyDataSet();
    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Person> result = sut.findPersons();
    // verify
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void findAllPersonsWithOneEntryInDataset() throws Exception
  {
    // prepare
    EmptyDataSet emptyDataset = new EmptyDataSet();
    RowBuilder_Jobs job = emptyDataset.table_Jobs.insertRow().setTitle("Software Developer")
        .setDescription("Developing Software");
    RowBuilder_Teams team = emptyDataset.table_Teams.insertRow().setTitle("Quality Assurance")
        .setDescription("Just hanging around").setMembersize(1);
    emptyDataset.table_Persons.insertRow().setFirstName("Dennis").setName("Kaulbersch").setJobId(job.getId())
        .setTeamId(team.getId());

    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Person> result = sut.findPersons();
    // verify
    assertThat(result).hasSize(1);
    Person person = result.get(0);
    assertThat(person.getFirstName()).isEqualTo("Dennis");
  }

  @Test
  public void findAllPersonsWithDefaultProfessorDataset() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    // execute
    List<Person> result = sut.findPersons();
    // verify
    assertThat(result).hasSize(3);
  }

  @Test
  public void addPersonToEmptyDataset() throws Exception
  {
    // prepare
    EmptyDataSet emptyDataset = new EmptyDataSet();

    RowBuilder_Jobs job = emptyDataset.table_Jobs.insertRow().setTitle("Software Developer")
        .setDescription("Developing Software");
    RowBuilder_Teams team = emptyDataset.table_Teams.insertRow().setTitle("Quality Assurance")
        .setDescription("Verifies that requirments for a product is fulfilled").setMembersize(0);

    dbTesterRule.cleanInsert(emptyDataset);

    Person person = new Person();
    person.setFirstName("Dennis");
    person.setName("Kaulbersch");
    person.setJob(job.getId().intValue());
    person.setTeam(team.getId().intValue());

    int initialId = person.getId();

    // execute
    Person result = sut.addPerson(person);
    // verify
    assertThat(result.getFirstName()).isEqualTo(person.getFirstName());
    assertThat(result.getId()).isNotEqualTo(initialId);
  }

  // @Test(expected = DataIntegrityViolationException.class)
  // public void removePersonFromDefaultDatasetWithExistingLecture()
  // throws Exception
  // {
  // // prepare
  // DefaultDataSet defaultDataSet = new DefaultDataSet();
  // dbTesterRule.cleanInsert(defaultDataSet);
  //
  // Professor professor = new Professor();
  // professor.setFirstName("Hansi");
  // professor.setName("Krankl");
  // professor.setTitle("Dipl.-Med.-Sys.-Wiss.");
  // professor.setFaculty("Media");
  //
  // for (Professor currentProfessor : sut.findProfessors())
  // {
  // if (currentProfessor.getName().equals("Krankl"))
  // {
  // professor.setId(currentProfessor.getId());
  // break;
  // }
  // }
  //
  // // execute
  // sut.removeProfessor(professor);
  // // verify
  // Assert.fail();
  // }
  //
  // @Test
  // public void
  // removeProfessorsFromDefaultDatasetWithoutExistingLecture() throws
  // Exception
  // {
  // // prepare
  // DefaultDataSet defaultDataSet = new DefaultDataSet();
  // dbTesterRule.cleanInsert(defaultDataSet);
  //
  // Professor professor = new Professor();
  // professor.setFirstName("Paul");
  // professor.setName("Breitner");
  // professor.setTitle("Dr.");
  // professor.setFaculty("Architecture");
  //
  // for (Professor currentProfessor : sut.findProfessors())
  // {
  // if (currentProfessor.getName().equals("Breitner"))
  // {
  // professor.setId(currentProfessor.getId());
  // break;
  // }
  // }
  //
  // // execute
  // int deletedRows = sut.removeProfessor(professor);
  // // verify
  // assertThat(deletedRows).isEqualTo(1);
  // }

  // Jobs

  @Test
  public void findAllJobsOnEmptyDataset() throws Exception
  {
    // prepare
    List<Job> expected = new LinkedList<Job>();
    EmptyDataSet emptyDataset = new EmptyDataSet();
    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Job> result = sut.findJobs();
    // verify
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void findAllJobsWithOneEntryInDataset() throws Exception
  {
    // prepare
    EmptyDataSet emptyDataset = new EmptyDataSet();
    emptyDataset.table_Jobs.insertRow().setTitle("Software Developer").setDescription("Creating software");
    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Job> result = sut.findJobs();
    // verify
    assertThat(result).hasSize(1);
    Job job = result.get(0);
    assertThat(job.getTitle()).isEqualTo("Software Developer");
  }

  @Test
  public void findAllJobsWithDefaultDataset() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    // execute
    List<Job> result = sut.findJobs();
    // verify
    assertThat(result).hasSize(3);
  }

  @Test
  public void addJobToDefaultDataSet() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    Job job = new Job();
    job.setTitle("Software Architect");
    job.setDescription("Developing software architecture");
    int expectedId = job.getId();
    // execute
    Job result = sut.addJob(job);
    // verify
    assertThat(result.getTitle()).isEqualTo(job.getTitle());
    assertThat(result.getId()).isNotEqualTo(expectedId);
  }

//  @Test
//  public void removeLectureFromDefaultDataset() throws Exception
//  {
//    // prepare
//    DefaultDataSet defaultDataSet = new DefaultDataSet();
//    dbTesterRule.cleanInsert(defaultDataSet);
//
//    Professor professor = new Professor();
//    professor.setFirstName("Hansi");
//    professor.setName("Krankl");
//    professor.setTitle("Dipl.-Med.-Sys.-Wiss.");
//    professor.setFaculty("Media");
//
//    for (Professor currentProfessor : sut.findProfessors())
//    {
//      if (currentProfessor.getName().equals("Krankl"))
//      {
//        professor.setId(currentProfessor.getId());
//        break;
//      }
//    }
//
//    Lecture lecture = new Lecture();
//    lecture.setTitle("Semiotik Today");
//    lecture.setWeeklyHours(2);
//    lecture.setSemesterCredits(10);
//    lecture.setGivenBy(professor.getId());
//
//    for (Lecture currentLecture : sut.findLectures())
//    {
//      if (currentLecture.getTitle().equals("Semiotik Today"))
//      {
//        lecture.setId(currentLecture.getId());
//        break;
//      }
//    }
//
//    // execute
//    int deletedRows = sut.removeLecture(lecture);
//    // verify
//    assertThat(deletedRows).isEqualTo(1);
//  }
  
  @Test
  public void findAllTeamsOnEmptyDataset() throws Exception
  {
    // prepare
    List<Team> expected = new LinkedList<Team>();
    EmptyDataSet emptyDataset = new EmptyDataSet();
    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Team> result = sut.findTeams();
    // verify
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void findAllTeamsWithOneEntryInDataset() throws Exception
  {
    // prepare
    EmptyDataSet emptyDataset = new EmptyDataSet();
    emptyDataset.table_Teams.insertRow().setTitle("Quality Assurance")
        .setDescription("Verifies that requirments for a product is fulfilled").setMembersize(0);
    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Team> result = sut.findTeams();
    // verify
    assertThat(result).hasSize(1);
    Team team = result.get(0);
    assertThat(team.getTitle()).isEqualTo("Quality Assurance");
  }

  @Test
  public void findAllTeamsWithDefaultDataset() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    // execute
    List<Job> result = sut.findJobs();
    // verify
    assertThat(result).hasSize(3);
  }

  @Test
  public void addTeamsToDefaultDataSet() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    Team team = new Team();
    team.setTitle("Quality Assurance");
    team.setDescription("Verifies that requirments for a product is fulfilled");
    team.setMembersize(0);
    int expectedId = team.getId();
    // execute
    Team result = sut.addTeam(team);
    // verify
    assertThat(result.getTitle()).isEqualTo(team.getTitle());
    assertThat(result.getId()).isNotEqualTo(expectedId);
  }
}
