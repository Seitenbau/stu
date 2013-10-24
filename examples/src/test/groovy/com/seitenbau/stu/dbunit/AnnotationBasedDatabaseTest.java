package com.seitenbau.stu.dbunit;

import java.util.List;

import javax.sql.DataSource;

import org.fest.assertions.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seitenbau.stu.dbunit.SortConfig;
import com.seitenbau.stu.dbunit.datasets.DefaultDataSet;
import com.seitenbau.stu.dbunit.datasets.SampleDataSet;
import com.seitenbau.stu.dbunit.rule.DatabaseBefore;
import com.seitenbau.stu.dbunit.rule.DatabasePrepare;
import com.seitenbau.stu.dbunit.rule.DatabaseSetup;
import com.seitenbau.stu.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.stu.personmanager.Person;
import com.seitenbau.stu.personmanager.PersonManagerContext;
import com.seitenbau.stu.personmanager.PersonService;
import com.seitenbau.stu.util.Future;
import com.seitenbau.stu.dbunit.model.JobsTable;
import com.seitenbau.stu.dbunit.model.JobsTable.RowBuilder_Jobs;
import com.seitenbau.stu.dbunit.model.PersonDatabaseDataSet;
import com.seitenbau.stu.dbunit.model.PersonJobTable;
import com.seitenbau.stu.dbunit.model.PersonsTable;
import com.seitenbau.stu.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.stu.dbunit.model.TeamsTable;
import com.seitenbau.stu.dbunit.model.TeamsTable.RowBuilder_Teams;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=PersonManagerContext.class)
@DatabaseSetup(prepare = DefaultDataSet.class, assertNoModification = false)
public class AnnotationBasedDatabaseTest
{

  @Autowired
  PersonService sut;

  @Autowired
  DataSource dataSource;

  private final SortConfig[] sortConfig = new SortConfig[] {
      new SortConfig(PersonJobTable.NAME, PersonJobTable.Columns.PersonId, PersonJobTable.Columns.JobId),
      new SortConfig(PersonsTable.NAME, PersonsTable.Columns.Id),
      new SortConfig(JobsTable.NAME, JobsTable.Columns.Id),
      new SortConfig(TeamsTable.NAME, TeamsTable.Columns.Id),
    };

  @Rule
  public DatabaseTesterRule dbTesterRule = new DatabaseTesterRule(new Future<DataSource>(){
    @Override
    public DataSource getFuture()
    {
      return dataSource;
    }
  }).setDefaultSortConfig(sortConfig);

  @Test
  public void allPersonsFromDefaultDataset() throws Exception
  {
    // prepare
    // execute
    List<Person> result = sut.findPersons();
    // verify
    Assertions.assertThat(result).hasSize(3);
  }

  @DatabaseBefore
  public void removeHansi(SampleDataSet sampleDataSet)
  {
    sampleDataSet.table_Persons.findWhere.firstName("Hansi").delete();
  }

  @Test
  @DatabaseSetup(prepare = SampleDataSet.class, assertNoModification = true)
  public void allPersonsFromSampleDatasetWithoutHansi() throws Exception
  {
    // prepare
    // execute
    List<Person> result = sut.findPersons();
    // verify
    Assertions.assertThat(result).hasSize(0);
  }

  @DatabaseBefore(id = "withRainer")
  public void addRainer(PersonDatabaseDataSet defaultDataSet)
  {
    RowBuilder_Jobs job = defaultDataSet.table_Jobs.insertRow().setTitle("Agile Tester").setDescription("Just agile.");
    RowBuilder_Teams team = defaultDataSet.table_Teams.insertRow().setTitle("Agile Experts").setDescription("Agile only.")
        .setMembersize(1);
    RowBuilder_Persons person = defaultDataSet.table_Persons.insertRow().setFirstName("Rainer").setName("Weinhold").setTeamId(team.getId());
    defaultDataSet.table_PersonJob.insertRow().setPersonId(person.getId()).setJobId(job.getId());
  }

  //@Ignore("the sort order has to be set on the DatabaseTesteRule")
  @Test
  @DatabasePrepare("withRainer")
  @DatabaseSetup(prepare = DefaultDataSet.class, assertNoModification = true)
  public void getAllPersonsWithRainer()
  {
    // prepare
    // execute
    List<Person> result = sut.findPersons();
    // verify
    Assertions.assertThat(result).hasSize(4);
  }

}
