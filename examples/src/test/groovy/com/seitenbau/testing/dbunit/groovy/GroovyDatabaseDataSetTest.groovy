package com.seitenbau.testing.dbunit.groovy

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*
import static org.fest.assertions.Assertions.*

import javax.sql.DataSource;

import org.fest.assertions.Fail
import org.junit.Ignore;
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import com.seitenbau.testing.personmanager.*
import com.seitenbau.testing.util.Future;

import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet
import com.seitenbau.testing.dbunit.dataset.ExtendedDemoGroovyDataSet
import com.seitenbau.testing.dbunit.dataset.EmptyGroovyDataSet
import com.seitenbau.testing.dbunit.extend.impl.ApacheDerbySequenceReset;
import com.seitenbau.testing.dbunit.model.JobsRef;
import com.seitenbau.testing.dbunit.model.PersonDatabaseBuilder
import com.seitenbau.testing.dbunit.rule.DatabaseSetup
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule
import com.seitenbau.testing.dbunit.rule.InjectDataSet

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=[PersonManagerContext])
class GroovyDatabaseDataSetTest {

  @Autowired
  DataSource dataSource;
  
  @Rule
  public DatabaseTesterRule dbTester =
    new DatabaseTesterRule(new Future<DataSource>(){
       @Override
       public DataSource getFuture()
       {
         return dataSource;
       }
     }).addCleanAction(new ApacheDerbySequenceReset().autoDerivateFromTablename("_SEQ"));

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
    List<Job> jobs = new ArrayList<Job>()
    Job job = new Job()
    job.id = SWD.id
    job.title = SWD.title
    job.description = SWD.description
    jobs.add(job)
    
    Person person = new Person()
    person.setFirstName("Nikolaus")
    person.setName("Moll")
    person.setJobs(jobs)
    person.setTeam(QA.id.intValue())

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
    dbTester.assertDataBase(dataSet)
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void removePerson()
  {
    // prepare
    List<Job> jobs = new ArrayList<Job>()
    Job job = new Job()
    job.id = SWD.id
    job.title = SWD.title
    job.description = SWD.description
    jobs.add(job)
    
    Person person = new Person()
    person.setFirstName("Dennis")
    person.setName("Kaulbersch")
    person.setJobs(jobs)
    person.setTeam(QA.id.intValue())
    person.setId(KAULBERSCH.id.intValue())

    // execute
    sut.removePerson(person)

    // verify
    dataSet.personsTable.findWhere.id(KAULBERSCH).delete()
    dataSet.personJobTable.deleteAssociation(KAULBERSCH, SWD)
    dbTester.assertDataBase(dataSet)
  }

  @Ignore // TODO Exception when removing is not thrown on every machine
  @Test(expected=DataIntegrityViolationException.class)
  @DatabaseSetup(prepare = EmptyGroovyDataSet)
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
      REF           | id            | title                   | description
      SAT           | savedJob.id   | "Software Architect"    | "Developing software architecture"
    }
    dbTester.assertDataBase(dataSet)
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet)
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
  
  @Ignore // TODO Exception when removing is not thrown on every machine
  @Test(expected=DataIntegrityViolationException)
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void removeJobWithExistingReference()
  {
    // prepare
    Job job = new Job()
    job.setDescription(SWD.description)
    job.setTitle(SWD.title)
    job.setId(SWD.id)
    
    // execute
    sut.removeJob(job)

    // verify
    Fail.fail()
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
  @DatabaseSetup(prepare = DemoGroovyDataSet)
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
    dataSet.teamsTable.findWhere.id(HR.id).delete()
    dbTester.assertDataBase(dataSet)
  }
  
  @Ignore // TODO Exception when removing is not thrown on every machine
  @Test(expected=DataIntegrityViolationException)
  @DatabaseSetup(prepare = DemoGroovyDataSet)
  void removeTeamWithExistingReference()
  {
    // prepare
    Team team = new Team()
    team.setTitle(QA.title)
    team.setDescription(QA.description)
    team.setMembersize(QA.membersize)
    team.setId(QA.id)
   
    // execute
    sut.removeTeam(team)

    // verify
    Fail.fail()
  }
}
