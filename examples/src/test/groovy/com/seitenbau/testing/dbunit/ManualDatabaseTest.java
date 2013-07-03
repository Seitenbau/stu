package com.seitenbau.testing.dbunit;

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

import com.seitenbau.testing.dbunit.datasets.DefaultDataSet;
import com.seitenbau.testing.dbunit.datasets.EmptyDataSet;
import com.seitenbau.testing.dbunit.extend.impl.ApacheDerbySequenceReset;
import com.seitenbau.testing.dbunit.model.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.testing.personmanager.Job;
import com.seitenbau.testing.personmanager.Person;
import com.seitenbau.testing.personmanager.PersonManagerContext;
import com.seitenbau.testing.personmanager.PersonService;
import com.seitenbau.testing.personmanager.Team;
import com.seitenbau.testing.util.Future;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=PersonManagerContext.class)
public class ManualDatabaseTest
{

  @Autowired
  DataSource dataSource;

  @Rule
  public DatabaseTesterRule dbTesterRule =
     new DatabaseTesterRule(new Future<DataSource>(){
       @Override
       public DataSource getFuture()
       {
         return dataSource;
       }
     }).addCleanAction(new ApacheDerbySequenceReset().autoDerivateFromTablename("_SEQ"));

  @Autowired
  PersonService sut;

  @Test
  public void findAllPersonsInEmptyDataset() throws Exception
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
    RowBuilder_Persons personRow = emptyDataset.table_Persons.insertRow().setFirstName("Dennis").setName("Kaulbersch").setTeamId(team.getId());
    emptyDataset.table_PersonJob.insertRow().setPersonId(personRow.getId()).setJobId(job.getId());

    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Person> result = sut.findPersons();
    // verify
    assertThat(result).hasSize(1);
    Person person = result.get(0);
    assertThat(person.getFirstName()).isEqualTo("Dennis");
  }

  @Test
  public void findAllPersonsInDefaultDataset() throws Exception
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
  public void findAllPersonsByTeamInDefaultDataset() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    List<Team> teams = sut.findTeams();
    Team team = teams.get(0);
    // execute
    List<Person> result = sut.findPersons(team);
    // verify
    assertThat(result).hasSize(3);
  }

  @Test
  public void findAllPersonsByTeamInEmptyDataset() throws Exception
  {
    // prepare
    EmptyDataSet emptyDataset = new EmptyDataSet();
    dbTesterRule.cleanInsert(emptyDataset);

    Team team = new Team();
    team.setId(1);
    // execute
    List<Person> result = sut.findPersons(team);
    // verify
    assertThat(result).isEmpty();
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
    person.addJob(createJob(job));
    person.setTeam(team.getId().intValue());

    // execute
    Person result = sut.addPerson(person);
    // verify
    assertThat(result.getFirstName()).isEqualTo(person.getFirstName());
    assertThat(result.getId()).isNotNull();
  }

  private Job createJob(RowBuilder_Jobs jobRow)
  {
    Job job = new Job();
    job.setId(jobRow.getId());
    job.setTitle(jobRow.getTitle());
    job.setDescription(jobRow.getDescription());
    return job;
  }

  @Test
  public void removePersonFromDataset() throws Exception
  {
    // prepare
    EmptyDataSet emptyDataset = new EmptyDataSet();
    RowBuilder_Jobs job = emptyDataset.table_Jobs.insertRow().setTitle("Software Developer")
        .setDescription("Developing Software");
    RowBuilder_Teams team = emptyDataset.table_Teams.insertRow().setTitle("Quality Assurance")
        .setDescription("Just hanging around").setMembersize(1);
    RowBuilder_Persons personRow = emptyDataset.table_Persons.insertRow().setFirstName("Dennis").setName("Kaulbersch")
        .setTeamId(team.getId());
    emptyDataset.table_PersonJob.insertRow().setPersonId(personRow.getId()).setJobId(job.getId());

    dbTesterRule.cleanInsert(emptyDataset);

    Person person = new Person();
    person.setFirstName(personRow.getFirstName());
    person.setId(personRow.getId().intValue());
    person.addJob(createJob(job));
    person.setName(personRow.getName());
    person.setTeam(personRow.getTeamId().intValue());

    // execute

    sut.removePerson(person);
    // verify
  }

  @Test(expected=RuntimeException.class)
  public void removePersonFromEmptyDataset() throws Exception
  {
    // prepare
    EmptyDataSet emptyDataset = new EmptyDataSet();

    dbTesterRule.cleanInsert(emptyDataset);

    Person person = new Person();
    person.setFirstName("John");
    person.setId(23);
    person.addJob(new Job());
    person.setName("Doe");
    person.setTeam(null);

    // execute
    sut.removePerson(person);
    // verify
    Fail.fail();
  }

  // Jobs

  @Test
  public void findAllJobsInEmptyDataset() throws Exception
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
  public void findAllJobsInDefaultDataset() throws Exception
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

  @Test
  public void removeTeamFromDefaultDatasetWithoutExistingReference() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    RowBuilder_Teams newTeam = defaultDataSet.table_Teams.insertRow().setTitle("Empty Team")
        .setDescription("No members").setMembersize(0);
    dbTesterRule.cleanInsert(defaultDataSet);

    Team team = new Team();
    team.setId(newTeam.getId().intValue());
    // execute
    sut.removeTeam(team);
    // verify
  }

  @Test(expected=RuntimeException.class)
  public void removeTeamFromEmptyDataset() throws Exception
  {
    // prepare
    EmptyDataSet emptyDataSet = new EmptyDataSet();
    dbTesterRule.cleanInsert(emptyDataSet);

    Team team = new Team();
    team.setId(1);
    // execute
    sut.removeTeam(team);
    // verify
    Fail.fail();
  }

  @Test(expected = RuntimeException.class)
  public void removeTeamFromDefaultDatasetWithExistingReference() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    List<Team> teams = sut.findTeams();
    Team team = teams.get(0);
    // execute
    sut.removeTeam(team);
    // verify
    Fail.fail();
  }

  // Teams
  @Test
  public void findAllTeamsInEmptyDataset() throws Exception
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
  public void findAllTeamsInDefaultDataset() throws Exception
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
    team.setTitle("Human Resources");
    team.setDescription("Make up workforce of an organzation");
    team.setMembersize(0);
    int expectedId = team.getId();
    // execute
    Team result = sut.addTeam(team);
    // verify
    assertThat(result.getTitle()).isEqualTo(team.getTitle());
    assertThat(result.getId()).isNotEqualTo(expectedId);
  }

  @Test
  public void removeJobFromDefaultDatasetWithoutExistingReference() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    RowBuilder_Jobs newJob = defaultDataSet.table_Jobs.insertRow().setTitle("Software Architect")
        .setDescription("Make high-level design.");
    dbTesterRule.cleanInsert(defaultDataSet);
    dbTesterRule.cleanInsert(defaultDataSet);

    Job job = new Job();
    job.setId(newJob.getId().intValue());
    // execute
    sut.removeJob(job);
    // verify
  }

  @Test(expected=RuntimeException.class)
  public void removeJobFromEmptyDataset() throws Exception
  {
    // prepare
    EmptyDataSet emptyDataSet = new EmptyDataSet();
    dbTesterRule.cleanInsert(emptyDataSet);

    Job job = new Job();
    job.setId(1);
    // execute
    sut.removeJob(job);
    // verify
    Fail.fail();
  }

  @Test
  public void removeJobFromDefaultDatasetWithExistingReference() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    List<Job> jobs = sut.findJobs();
    Job job = jobs.get(0);
    // execute
    sut.removeJob(job);
    // verify

    // TODO NM/DK implement test
    //dataSet.jobsTable.deleteRow(SWD);
    //dataSet.personJobTable.deleteAllAssociations(SWD);
    //dbTester.assertDataBaseSorted(dataSet, sortConfig);
  }
}
