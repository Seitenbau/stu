package com.seitenbau.testing.dbunit;

import java.util.List;

import org.fest.assertions.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seitenbau.testing.dbunit.config.TestConfig;
import com.seitenbau.testing.dbunit.dao.Person;
import com.seitenbau.testing.dbunit.datasets.DefaultDataSet;
import com.seitenbau.testing.dbunit.datasets.SampleDataSet;
import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.rule.DatabaseBefore;
import com.seitenbau.testing.dbunit.rule.DatabasePrepare;
import com.seitenbau.testing.dbunit.rule.DatabaseSetup;
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.testing.dbunit.services.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/config/spring/context.xml", "/config/spring/test-context.xml"})
@DatabaseSetup(prepare = DefaultDataSet.class, assertNoModification = false)
public class AnnotationBasedDatabaseTester
{
  @Rule
  public DatabaseTesterRule dbTesterRule = new DatabaseTesterRule(TestConfig.class);

  @Autowired
  PersonService sut;

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
  public void removeDennis(SampleDataSet sampleDataSet)
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
  public void addRainer(DefaultDataSet defaultDataSet)
  {
    RowBuilder_Jobs job = defaultDataSet.table_Jobs.insertRow().setTitle("Agile Tester").setDescription("Just agile.");
    RowBuilder_Teams team = defaultDataSet.table_Teams.insertRow().setTitle("Agile Experts").setDescription("Agile only.")
        .setMembersize(1);
    defaultDataSet.table_Persons.insertRow().setFirstName("Rainer").setName("Weinhold").setJobId(job.getId())
        .setTeamId(team.getId());
  }

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
