package com.seitenbau.stu.dbunit;

import static com.seitenbau.stu.dbunit.PersonDatabaseRefs.HR;
import static com.seitenbau.stu.dbunit.PersonDatabaseRefs.KAULBERSCH;
import static com.seitenbau.stu.dbunit.PersonDatabaseRefs.QA;
import static com.seitenbau.stu.dbunit.PersonDatabaseRefs.SAT;
import static com.seitenbau.stu.dbunit.PersonDatabaseRefs.SWD;
import static org.fest.assertions.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.fest.assertions.Fail;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seitenbau.stu.dbunit.SortConfig;
import com.seitenbau.stu.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.stu.dbunit.dataset.EmptyGroovyDataSet;
import com.seitenbau.stu.dbunit.dataset.ExtendedDemoGroovyDataSet;
import com.seitenbau.stu.dbunit.dataset.MalFormedGroovyDataSet;
import com.seitenbau.stu.dbunit.dsl.TableParserException;
import com.seitenbau.stu.dbunit.extend.impl.ApacheDerbySequenceReset;
import com.seitenbau.stu.dbunit.rule.DatabaseSetup;
import com.seitenbau.stu.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.stu.dbunit.rule.InjectDataSet;
import com.seitenbau.stu.personmanager.Job;
import com.seitenbau.stu.personmanager.Person;
import com.seitenbau.stu.personmanager.PersonManagerContext;
import com.seitenbau.stu.personmanager.PersonService;
import com.seitenbau.stu.personmanager.Team;
import com.seitenbau.stu.util.Future;
import com.seitenbau.stu.dbunit.model.JobsRef;
import com.seitenbau.stu.dbunit.model.JobsTable;
import com.seitenbau.stu.dbunit.model.PersonDatabaseBuilder;
import com.seitenbau.stu.dbunit.model.PersonJobTable;
import com.seitenbau.stu.dbunit.model.PersonsTable;
import com.seitenbau.stu.dbunit.model.TeamsRef;
import com.seitenbau.stu.dbunit.model.TeamsTable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=PersonManagerContext.class)
public class GroovyDataSetDatabaseTest
{

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

  private final SortConfig[] sortConfig = new SortConfig[] {
      new SortConfig(PersonJobTable.NAME, PersonJobTable.Columns.PersonId, PersonJobTable.Columns.JobId),
      new SortConfig(PersonsTable.NAME, PersonsTable.Columns.Id),
      new SortConfig(JobsTable.NAME, JobsTable.Columns.Id),
      new SortConfig(TeamsTable.NAME, TeamsTable.Columns.Id),
    };

  @Autowired
  PersonService sut;

  @InjectDataSet
  PersonDatabaseBuilder dataSet;

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void unmodifiedDataSet() throws Exception
  {
    dbTester.assertDataBaseSorted(dataSet, sortConfig);
  }


  @Test
  @DatabaseSetup(prepare = ExtendedDemoGroovyDataSet.class)
  public void unmodifiedExtendedDataset() throws Exception
  {
    dbTester.assertDataBaseSorted(dataSet, sortConfig);
  }

  @Test(expected=TableParserException.class)
  public void malFormedDataset() throws Exception
  {
    // exception must be thrown within test
    new MalFormedGroovyDataSet();
  }

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
  public void findAllPersonsByTeam() throws Exception
  {
    // prepare
    Team team = new Team();
    team.setId(QA.getId());
    team.setTitle(QA.getTitle());
    team.setDescription(QA.getDescription());
    team.setMembersize(QA.getMembersize());

    // execute
    List<Person> result = sut.findPersons(team);

    // verify
    assertThat(result).hasSize(3);
    dbTester.assertDataBase(dataSet);
  }

  @Test
  @DatabaseSetup(prepare = ExtendedDemoGroovyDataSet.class)
  public void findAllPersonsByTeamForExtendedDataset() throws Exception
  {
    // prepare
    Team team = new Team();
    team.setId(QA.getId());
    team.setTitle(QA.getTitle());
    team.setDescription(QA.getDescription());
    team.setMembersize(QA.getMembersize());

    // execute
    List<Person> result = sut.findPersons(team);

    // verify
    assertThat(result).hasSize(4);
    dbTester.assertDataBaseSorted(dataSet, sortConfig);
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void addPerson() throws Exception {
    // prepare
    Job job = getJob(SWD);
    Person person = new Person();
    person.setFirstName("Nikolaus");
    person.setName("Moll");
    person.setTeam(QA.getId().intValue());
    person.addJob(job);

    // execute
    Person newPerson = sut.addPerson(person);

    // verify
    dataSet.personsTable.insertRow()
      .setId(person.getId())
      .setFirstName("Nikolaus")
      .setName("Moll")
      .setTeamId(QA);

    dataSet.personJobTable.insertRow()
      .setJobId(job.getId())
      .setPersonId(newPerson.getId());

    dbTester.assertDataBaseSorted(dataSet, sortConfig);
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void removePerson() throws Exception {
    // prepare
    Person person = new Person();
    person.setFirstName("Dennis");
    person.setName("Kaulbersch");
    person.addJob(getJob(SWD));
    person.setTeam(QA.getId().intValue());
    person.setId(KAULBERSCH.getId().intValue());

    // execute
    sut.removePerson(person);

    // verify
    dataSet.personsTable.deleteRow(KAULBERSCH);
    dataSet.personJobTable.deleteAllAssociations(KAULBERSCH);
    dbTester.assertDataBase(dataSet);
  }

  @Test(expected=RuntimeException.class)
  @DatabaseSetup(prepare = EmptyGroovyDataSet.class)
  public void removePersonThatDoesNotExist() throws Exception {
    // prepare
    Person person = new Person();
    person.setFirstName("John");
    person.setId(23);
    //person.addJob(new Job());
    person.setName("Doe");
    person.setTeam(1899);

    // execute
    sut.removePerson(person);
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
    Job savedJob = sut.addJob(job);

    // verify
    dataSet.jobsTable.insertRow() //
        .setId(savedJob.getId())
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

    if (dataSet.jobsTable.getRowCount() != 4) {
      throw new RuntimeException("DataSet defect");
    }

    // execute
    sut.removeJob(job);

    // verify
    dataSet.jobsTable.deleteRow(SAT);
    dbTester.assertDataBaseSorted(dataSet, sortConfig);
  }

  @Test
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
    dataSet.jobsTable.deleteRow(SWD);
    dataSet.personJobTable.deleteAllAssociations(SWD);
    dbTester.assertDataBaseSorted(dataSet, sortConfig);
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
    Team savedTeam = sut.addTeam(team);

    // verify
    dataSet.teamsTable.insertRow() //
        .setId(savedTeam.getId())
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
    dataSet.teamsTable.deleteRow(HR);
    dbTester.assertDataBaseSorted(dataSet, sortConfig);
  }

  @Test(expected=RuntimeException.class)
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void removeTeamWithExistingReference() throws Exception
  {
    // prepare
    Team team = getTeam(QA);

    // execute
    sut.removeTeam(team);

    // verify
    Fail.fail();
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
