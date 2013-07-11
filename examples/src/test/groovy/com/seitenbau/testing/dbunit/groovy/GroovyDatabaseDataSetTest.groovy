package com.seitenbau.testing.dbunit.groovy

import org.junit.rules.ExpectedException

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*
import static org.fest.assertions.Assertions.*

import javax.sql.DataSource

import org.fest.assertions.Fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import com.seitenbau.testing.dbunit.SortConfig
import com.seitenbau.testing.dbunit.dataset.*
import com.seitenbau.testing.dbunit.extend.impl.ApacheDerbySequenceReset
import com.seitenbau.testing.dbunit.model.*
import com.seitenbau.testing.dbunit.rule.DatabaseSetup
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule
import com.seitenbau.testing.dbunit.rule.InjectDataSet
import com.seitenbau.testing.personmanager.*
import com.seitenbau.testing.util.Future

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=[PersonManagerContext])
class GroovyDatabaseDataSetTest {

  @Autowired
  DataSource dataSource;

  @Rule
  public ExpectedException thrown = ExpectedException.none()

  @Rule
  public DatabaseTesterRule dbTester =
    new DatabaseTesterRule({dataSource })
            .addCleanAction(new ApacheDerbySequenceReset().autoDerivateFromTablename("_SEQ"))
            .setDefaultSortConfig(sortConfig)

  SortConfig[] sortConfig = [
      new SortConfig(PersonJobTable.NAME, PersonJobTable.Columns.PersonId, PersonJobTable.Columns.JobId),
      new SortConfig(PersonsTable.NAME, PersonsTable.Columns.Id),
      new SortConfig(JobsTable.NAME, JobsTable.Columns.Id),
      new SortConfig(TeamsTable.NAME, TeamsTable.Columns.Id),
    ]

  @Autowired
  PersonService sut

  @InjectDataSet
  PersonDatabaseBuilder dataSet;

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void findPersons() {
    // execute
    def persons = sut.findPersons()

    // verify
    assertThat(persons).hasSize(dataSet.personsTable.getRowCount())
  }

  @Test
  @DatabaseSetup(prepare = EmptyGroovyDataSet)
  void findPersonsInEmptyDataSet() {
    // prepare
    List<Person> expected = new LinkedList<Person>();

    // execute
    def persons = sut.findPersons()

    // verify
    assertThat(persons).isEqualTo(expected)
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void findPersonsForTeam() {
    Team team = new Team()
    team.setId(QA.id)
    team.setTitle(QA.title)
    team.setDescription(QA.description)
    team.setMembersize(QA.membersize)
    // execute
    def persons = sut.findPersons(team)

    // verify
    assertThat(persons).hasSize(dataSet.personsTable.getRowCount())
  }

  @Test
  @DatabaseSetup(prepare = ExtendedDemoGroovyDataSet)
  void findPersonsForTeamInExtendedDataSet() {
    Team team = new Team()
    team.setId(QA.id)
    team.setTitle(QA.title)
    team.setDescription(QA.description)
    team.setMembersize(QA.membersize)
    // execute
    def persons = sut.findPersons(team)

    // verify
    assertThat(persons).hasSize(dataSet.personsTable.getRowCount())
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void addPerson() {
    // prepare
    Job job = getJob(SWD)

    Person person = new Person()
    person.setFirstName("Nikolaus")
    person.setName("Moll")
    person.setTeam(QA.id.intValue())
    person.addJob(job)

    // execute
    def savedPerson = sut.addPerson(person)

    // verify
    dataSet.personsTable.rows {

      id             | first_name | name   | team
      savedPerson.id | "Nikolaus" | "Moll" | QA

    }

    dataSet.personJobTable.rows {

      person_id       | job_id
      savedPerson.id  | SWD.id

    }
    dbTester.assertDataBaseSorted(dataSet, sortConfig)
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void removePerson()
  {
    // prepare
    Job job = getJob(SWD)

    Person person = new Person()
    person.setFirstName("Dennis")
    person.setName("Kaulbersch")
    person.setTeam(QA.id.intValue())
    person.setId(KAULBERSCH.id.intValue())
    person.addJob(job)

    // execute
    sut.removePerson(person)

    // verify
    dataSet.personsTable.deleteRow(KAULBERSCH)
    dataSet.personJobTable.deleteAssociation(KAULBERSCH, SWD)
    dbTester.assertDataBase(dataSet)
  }

  @Test(expected=RuntimeException.class)
  @DatabaseSetup(prepare = EmptyGroovyDataSet)
  void removePersonThatDoesNotExist()
  {
    // prepare
    Person person = new Person()
    person.setFirstName("John")
    person.setId(23)
    person.setName("Doe")
    person.setTeam(1899)

    // execute
    sut.removePerson(person)

    // verify
    Fail.fail()
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void findAllJobs()
  {
    // execute
    def jobs = sut.findJobs()

    // verify
    assertThat(jobs).hasSize(dataSet.jobsTable.getRowCount())
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void addJob()
  {
    // prepare
    Job job = new Job()
    job.setTitle("Software Architect")
    job.setDescription("Developing software architecture")

    // execute
    def savedJob = sut.addJob(job)

    // verify
    dataSet.jobsTable.rows {
      id            | title                   | description
      savedJob.id   | "Software Architect"    | "Developing software architecture"
    }
    dbTester.assertDataBase(dataSet)
  }

  @Test
  @DatabaseSetup(prepare = ExtendedWithoutRelationsDemoGroovyDataSet)
  void removeJobWithoutExistingReference()
  {
    // prepare
    Job job = getJob(SAT)

    // execute
    sut.removeJob(job)

    // verify
    dataSet.jobsTable.deleteRow(SAT)
    dbTester.assertDataBase(dataSet)
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void removeJobWithExistingReference()
  {
    // prepare
    Job job = getJob(SWD)

    // execute
    sut.removeJob(job)

    // verify
    dataSet.jobsTable.deleteRow(SWD);
    dataSet.personJobTable.deleteAllAssociations(SWD);
    dbTester.assertDataBaseSorted(dataSet, sortConfig);
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void findAllTeams()
  {
    // execute
    def teams = sut.findTeams()

    // verify
    assertThat(teams).hasSize(dataSet.teamsTable.getRowCount())
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void addTeam()
  {
    // prepare
    Team team = new Team()
    team.setTitle("Human Resources")
    team.setDescription("Make up workforce of an organzation")
    team.setMembersize(0)

    // execute
    def savedTeam = sut.addTeam(team)

    // verify
    dataSet.teamsTable.rows {
      REF           | id             | title                   | description                           | membersize
      HR            | savedTeam.id   | "Human Resources"       | "Make up workforce of an organzation" | 0
    }
    dbTester.assertDataBase(dataSet)
  }

  @Test
  @DatabaseSetup(prepare = ExtendedWithoutRelationsDemoGroovyDataSet)
  void removeTeamWithoutExistingReference()
  {
    // prepare
    Team team = getTeam(HR)

    // execute
    sut.removeTeam(team)

    // verify
    dataSet.teamsTable.deleteRow(HR)
    dbTester.assertDataBase(dataSet)
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void removeTeamWithExistingReference()
  {
    // prepare
    Team team = getTeam(QA)

    thrown.expect(RuntimeException)

    // execute
    sut.removeTeam(team)
  }

  private Job getJob(JobsRef jobsRef)
  {
    for (Job job : sut.findJobs())
    {
      if (job.getId() == jobsRef.getId()) {
        return job;
      }
    }

    throw new RuntimeException("No job found");
  }

  private Team getTeam(TeamsRef teamsRef)
  {
    for (Team team : sut.findTeams())
    {
      if (team.getId() == teamsRef.getId()) {
        return team;
      }
    }

    throw new RuntimeException("No team found");
  }
}
