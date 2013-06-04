package com.seitenbau.testing.dbunit;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*;
import static org.fest.assertions.Assertions.assertThat;

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

import com.seitenbau.testing.dbunit.config.TestConfig;
import com.seitenbau.testing.dbunit.dao.Person;
import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.dataset.EmptyGroovyDataSet;
import com.seitenbau.testing.dbunit.dsl.ScopeRegistry;
import com.seitenbau.testing.dbunit.model.dsl.PersonDatabaseBuilder;
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.testing.dbunit.services.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/config/spring/context.xml", "/config/spring/test-context.xml"})
public class GroovyDataSetDatabaseTest
{

  @Rule
  public DatabaseTesterRule dbTester = new DatabaseTesterRule(TestConfig.class);

  @Autowired
  PersonService sut;
  
  PersonDatabaseBuilder dataSet;
  
  @Before
  public void setup() throws Exception {
    dataSet = prepareDatabaseWithDemoDataset();
  }
  
  @Test
  public void findPersons() throws Exception
  {
    // execute
    List<Person> persons = sut.findPersons();

    // verify
    assertThat(persons).hasSize(dataSet.personsTable.findWhere.teamId(QA).getRowCount());
  }

  @Test
  public void findPersonsInEmptyDataset() throws Exception
  {
    // prepare
    List<Person> expected = new LinkedList<Person>();
    dataSet = prepareDatabase(new EmptyGroovyDataSet());
    
    // execute
    List<Person> persons = sut.findPersons();

    // verify
    //TODO access dataset when rowCount is implemented on tables
    assertThat(persons).isEqualTo(expected);
  }
  
  @Test
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
    
    dbTester.assertDataBase(dataSet.createDataSet());
  }
  
  @Ignore("Must run when the removeRow function is implemented")
  @Test
  public void removePerson() throws Exception {
    // prepare
    Person person = new Person();
    person.setFirstName("Dennis");
    person.setName("Kaulbersch");
    person.setJob(SWD.getId());
    person.setTeam(QA.getId());
    person.setId(dataSet.personsTable.findWhere.name(KAULBERSCH).getId());

    // execute
    sut.removePerson(person);
    
    // verify
    // TODO remove person / remove row
//    dataSet.personsTable.insertRow()
//      .setFirstName("Nikolaus")
//      .setName("Moll")
//      .setJobId(SWD)
//      .setTeamId(QA);
    
    dbTester.assertDataBase(dataSet.createDataSet());
  }
  
  @Test(expected=DataIntegrityViolationException.class)
  public void removePersonThatDoesNotExist() throws Exception {
    // prepare
    dataSet = prepareDatabase(new EmptyGroovyDataSet());
    
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
  
  private PersonDatabaseBuilder prepareDatabaseWithDemoDataset() throws Exception
  {
    DemoGroovyDataSet dataSet = new DemoGroovyDataSet();
    return prepareDatabase(dataSet);
  }

  private PersonDatabaseBuilder prepareDatabase(PersonDatabaseBuilder dataSet) throws Exception
  {
    ScopeRegistry.use(dataSet);
    dbTester.truncate(dataSet.createDataSet());
    dbTester.cleanInsert(dataSet.createDataSet());
    return dataSet;
  }
}
