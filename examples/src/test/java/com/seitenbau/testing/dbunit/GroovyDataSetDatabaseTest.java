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
  //TODO access dataset when rowCount is implemented on tables
    assertThat(persons).hasSize(dataSet.personsTable.findWhere.teamId(QA).getRowCount());
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
    //TODO access dataset when rowCount is implemented on tables
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
    
    dbTester.assertDataBase(dataSet);
  }
  
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
  
}