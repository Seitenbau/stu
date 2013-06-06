package com.seitenbau.testing.dbunit;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.HR;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.KAULBERSCH;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.QA;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.SAT;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.SWD;
import static org.fest.assertions.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;

import org.fest.assertions.Fail;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seitenbau.testing.dbunit.config.TestConfig;
import com.seitenbau.testing.dbunit.dao.Job;
import com.seitenbau.testing.dbunit.dao.Person;
import com.seitenbau.testing.dbunit.dao.Team;
import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.dataset.EmptyGroovyDataSet;
import com.seitenbau.testing.dbunit.dataset.ExtendedDemoGroovyDataSet;
import com.seitenbau.testing.dbunit.model.dsl.PersonDatabaseBuilder;
import com.seitenbau.testing.dbunit.rule.DatabaseSetup;
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.testing.dbunit.rule.InjectDataSet;
import com.seitenbau.testing.dbunit.services.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/config/spring/context.xml", "/config/spring/test-context.xml"})
public class GroovyDataSetDatabaseTest
{

  @Rule
  public DatabaseTesterRule dbTester = 
     new DatabaseTesterRule(TestConfig.class)
         .useTruncateAsCleanInsert();

  @Autowired
  PersonService sut;
  
  @InjectDataSet
  PersonDatabaseBuilder dataSet;
  
  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void findPersons() throws Exception
  {
    // execute
    List<Person> persons = sut.findPersons();

    // verify
    assertThat(persons).hasSize(dataSet.personsTable.getRowCount());
    dbTester.assertDataBase(dataSet);
  }

  @Test
  @DatabaseSetup(prepare = EmptyGroovyDataSet.class)
  public void findPersonsInEmptyDataset() throws Exception
  {
    // prepare
    List<Person> expected = new LinkedList<Person>();
    
    // execute
    List<Person> persons = sut.findPersons();

    // verify
    assertThat(persons).isEqualTo(expected);
  }
  
  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void addPerson() throws Exception {
    // prepare
    Person person = new Person();
    person.setFirstName("Nikolaus");
    person.setName("Moll");
    person.setJob(SWD.getId());
    person.setTeam(QA.getId());

    // execute
    sut.addPerson(person);
    
    // verify
    dataSet.personsTable.insertRow()
      .setFirstName("Nikolaus")
      .setName("Moll")
      .setJobId(SWD)
      .setTeamId(QA);
    
    dbTester.assertDataBase(dataSet);
  }
  
  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void removePerson() throws Exception {
    // prepare
    Person person = new Person();
    person.setFirstName("Dennis");
    person.setName("Kaulbersch");
    person.setJob(SWD.getId());
    person.setTeam(QA.getId());
    person.setId(KAULBERSCH.getId());

    // execute
    sut.removePerson(person);
    
    // verify
    dataSet.personsTable.findWhere.id(KAULBERSCH).delete();
    dbTester.assertDataBase(dataSet);
  }
  
  @Ignore // TODO Exception when removing is not thrown on every machine
  @Test(expected=DataIntegrityViolationException.class)
  @DatabaseSetup(prepare = EmptyGroovyDataSet.class)
  public void removePersonThatDoesNotExist() throws Exception {
    // prepare
    
    Person person = new Person();
    person.setFirstName("John");
    person.setId(23);
    person.setJob(0);
    person.setName("Doe");
    person.setTeam(1899);

    // execute
    sut.removePerson(person);
    
    // verify
    Fail.fail();
  }
  
  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void findAllJobs() throws Exception
  {
    // execute
    List<Job> jobs = sut.findJobs();

    // verify
    dbTester.assertDataBase(dataSet);
    assertThat(jobs).hasSize(dataSet.jobsTable.getRowCount());
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void addJob() throws Exception
  {
    // prepare
    Job job = new Job();
    job.setTitle("Software Architect");
    job.setDescription("Developing software architecture");

    // execute
    sut.addJob(job);

    // verify
    dataSet.jobsTable.insertRow() //
        .setTitle("Software Architect") //
        .setDescription("Developing software architecture");
    dbTester.assertDataBase(dataSet);
  }

  @Test
  @DatabaseSetup(prepare = ExtendedDemoGroovyDataSet.class)
  public void removeJobWithoutExistingReference() throws Exception
  {
    // prepare
    Job job = new Job();
    job.setTitle(SAT.getTitle());
    job.setDescription(SAT.getDescription());
    job.setId(SAT.getId());

    // execute
    sut.removeJob(job);

    // verify
    dataSet.jobsTable.findWhere.id(SAT).delete();
    dbTester.assertDataBase(dataSet);
  }
  
  @Ignore // TODO Exception when removing is not thrown on every machine
  @Test(expected=DataIntegrityViolationException.class)
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void removeJobWithExistingReference () throws Exception
  {
    // prepare
    Job job = new Job();
    job.setDescription(SWD.getDescription());
    job.setTitle(SWD.getTitle());
    job.setId(SWD.getId());
    
    // execute
    sut.removeJob(job);

    // verify
    Fail.fail();
  }
  
  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void findAllTeams() throws Exception
  {
    // execute
    List<Team> teams = sut.findTeams();

    // verify
    assertThat(teams).hasSize(dataSet.teamsTable.getRowCount());
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void addTeam() throws Exception
  {
    // prepare
    Team team = new Team();
    team.setTitle("Human Resources");
    team.setDescription("Make up workforce of an organzation");
    team.setMembersize(0);

    // execute
    sut.addTeam(team);

    // verify
    dataSet.teamsTable.insertRow() //
        .setTitle("Human Resources") //
        .setDescription("Make up workforce of an organzation") //
        .setMembersize(0);
    dbTester.assertDataBase(dataSet);
  }

  @Test
  @DatabaseSetup(prepare = ExtendedDemoGroovyDataSet.class)
  public void removeTeamWithoutExistingReference() throws Exception
  {
    // prepare
    Team team = new Team();
    team.setTitle(HR.getTitle());
    team.setDescription(HR.getDescription());
    team.setMembersize(HR.getMembersize());
    team.setId(HR.getId());
    
    // execute
    sut.removeTeam(team);

    // verify
    dataSet.teamsTable.findWhere.id(HR).delete();
    dbTester.assertDataBase(dataSet);
  }
  
  @Ignore // TODO Exception when removing is not thrown on every machine
  @Test(expected=DataIntegrityViolationException.class)
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void removeTeamWithExistingReference() throws Exception
  {
    // prepare
    Team team = new Team();
    team.setTitle(QA.getTitle());
    team.setDescription(QA.getDescription());
    team.setMembersize(QA.getMembersize());
    team.setId(QA.getId());
   
    // execute
    sut.removeTeam(team);

    // verify
    Fail.fail();
  }
  
}