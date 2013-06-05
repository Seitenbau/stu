package com.seitenbau.testing.dbunit.groovy

import static org.fest.assertions.Assertions.*;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*;

import java.util.LinkedList;
import java.util.List;

import org.fest.assertions.Fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.jdbc.PreparedStatement;
import com.seitenbau.testing.dbunit.config.TestConfig;
import com.seitenbau.testing.dbunit.dao.Job;
import com.seitenbau.testing.dbunit.dao.Person;
import com.seitenbau.testing.dbunit.dao.Team;
import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.dataset.EmptyGroovyDataSet;
import com.seitenbau.testing.dbunit.datasets.DefaultDataSet;
import com.seitenbau.testing.dbunit.dsl.ScopeRegistry;
import com.seitenbau.testing.dbunit.model.dsl.PersonDatabaseBuilder;
import com.seitenbau.testing.dbunit.rule.DatabaseSetup;
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.testing.dbunit.rule.InjectDataSet;
import com.seitenbau.testing.dbunit.services.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(["/config/spring/context.xml", "/config/spring/test-context.xml"])
class GroovyDatabaseDataSetTest {

  @Rule
  public DatabaseTesterRule dbTester =
  new DatabaseTesterRule(TestConfig.class)
    .useTruncateAsCleanInsert();

  @Autowired
  PersonService sut

  @InjectDataSet
  PersonDatabaseBuilder dataSet;

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  void findPersons() {
    // execute
    def persons = sut.findPersons()

    // verify
    assertThat(persons).hasSize(dataSet.personsTable.getRowCount())
  }

  @Test
  @DatabaseSetup(prepare = EmptyGroovyDataSet.class)
  void findPersonsInEmptyDataSet() {
    // prepare
    List<Person> expected = new LinkedList<Person>();

    // execute
    def persons = sut.findPersons()

    // verify
    assertThat(persons).isEqualTo(expected)
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  void addPerson() {
    // prepare
    Person person = new Person()
    person.setFirstName("Nikolaus")
    person.setName("Moll")
    person.setJob(SWD.getId())
    person.setTeam(QA.getId())

    // execute
    sut.addPerson(person)

    // verify
    dataSet.personsTable.rows {

      first_name | name   | job | team
      "Nikolaus" | "Moll" | SWD | QA

    }
    dbTester.assertDataBase(dataSet)
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  void removePerson()
  {
    // prepare
    Person person = new Person()
    person.setFirstName("Dennis")
    person.setName("Kaulbersch")
    person.setJob(SWD.getId())
    person.setTeam(QA.getId())
    person.setId(KAULBERSCH.getId())

    // execute
    sut.removePerson(person)

    // verify
    dataSet.personsTable.findWhere.id(KAULBERSCH).delete()
    dbTester.assertDataBase(dataSet)
  }

  @Test(expected=DataIntegrityViolationException.class)
  @DatabaseSetup(prepare = EmptyGroovyDataSet.class)
  @Ignore
  void removePersonThatDoesNotExist()
  {
    // prepare

    Person person = new Person()
    person.setFirstName("John")
    person.setId(23)
    person.setJob(0)
    person.setName("Doe")
    person.setTeam(1899)

    // execute
    sut.removePerson(person)

    // verify
    Fail.fail()
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  void findAllJobs()
  {
    // execute
    def jobs = sut.findJobs()

    // verify
    assertThat(jobs).hasSize(dataSet.jobsTable.getRowCount())
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  void addJob()
  {
    // prepare
    Job job = new Job()
    job.setTitle("Software Architect")
    job.setDescription("Developing software architecture")

    // execute
    def result = sut.addJob(job)

    // verify
    dataSet.jobsTable.rows {
      REF           | id  | title                   | description
      SAT           | 4   | "Software Architect"    | "Developing software architecture"
    }
    dbTester.assertDataBase(dataSet)
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  void removeJobWithoutExistingReference()
  {
    // prepare
    Job job = new Job()
    job.setTitle("Software Architect")
    job.setDescription("Developing software architecture")
    job.setId(4)
    
    // execute
    sut.removeJob(job)

    // verify
    dataSet.jobsTable.rows {
      REF           | id  | title                   | description
      SAT           | 4   | "Software Architect"    | "Developing software architecture"
    }
    dataSet.jobsTable.findWhere.id(SAT).delete()
    dbTester.assertDataBase(dataSet)
  }
  
  @Test(expected=DataIntegrityViolationException.class)
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  @Ignore
  void removeJobWithExistingReference()
  {
    // prepare
    Job job = new Job()
    job.setDescription(SWD.getDescription())
    job.setTitle(SWD.getTitle())
    job.setId(SWD.getId())
    
    // execute
    sut.removeJob(job)

    // verify
    Fail.fail()
  }
  
  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  void findAllTeams()
  {
    // execute
    def teams = sut.findTeams()

    // verify
    assertThat(teams).hasSize(dataSet.teamsTable.getRowCount())
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  void addTeam()
  {
    // prepare
    Team team = new Team()
    team.setTitle("Human Resources")
    team.setDescription("Make up workforce of an organzation")
    team.setMembersize(0)

    // execute
    def result = sut.addTeam(team)

    // verify
    dataSet.teamsTable.rows {
      REF           | id  | title                   | description                           | membersize
      HR            | 2   | "Human Resources"       | "Make up workforce of an organzation" | 0
    }
    dbTester.assertDataBase(dataSet)
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  void removeTeamWithoutExistingReference()
  {
    // prepare
    Team team = new Team()
    team.setTitle("Human Resources")
    team.setDescription("Make up workforce of an organzation")
    team.setMembersize(0)
    team.setId(2)
    
    // execute
    sut.removeTeam(team)

    // verify
   dataSet.teamsTable.rows {
      REF           | id  | title                   | description                           | membersize
      HR            | 2   | "Human Resources"       | "Make up workforce of an organzation" | 0
    }
    dataSet.teamsTable.findWhere.id(HR.getId()).delete()
    dbTester.assertDataBase(dataSet)
  }
  
  @Test(expected=DataIntegrityViolationException.class)
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  @Ignore
  void removeTeamWithExistingReference()
  {
    // prepare
    Team team = new Team()
    team.setTitle("Quality Assurance")
    team.setDescription("Verifies software")
    team.setMembersize(0)
    team.setId(1)
   
    // execute
    sut.removeTeam(team)

    // verify
    Fail.fail()
  }
}
