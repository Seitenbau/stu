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

import com.seitenbau.testing.dbunit.config.TestConfig;
import com.seitenbau.testing.dbunit.dao.Person;
import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.dataset.EmptyGroovyDataSet;
import com.seitenbau.testing.dbunit.dsl.ScopeRegistry;
import com.seitenbau.testing.dbunit.model.dsl.PersonDatabaseBuilder;
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.testing.dbunit.services.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(["/config/spring/context.xml", "/config/spring/test-context.xml"])
class GroovyDatabaseDataSetTest
{

  @Rule
  public DatabaseTesterRule dbTester = new DatabaseTesterRule(TestConfig.class)

  @Autowired
  PersonService sut
  
  PersonDatabaseBuilder dataSet
  
  @Before void setup()
  {
    dataSet = prepareDatabaseWithDemoDataset()
    ScopeRegistry.use(dataSet);
    
    dbTester.truncate(dataSet)
    dbTester.cleanInsert(dataSet)
  }
  
  @Test void findPersons()
  {
    // execute
    List<Person> persons = sut.findPersons()
    
    // verify
    //TODO access dataset when rowCount is implemented on tables
    assertThat(persons).hasSize(dataSet.personsTable.findWhere.teamId(QA).getRowCount())
  }
  
  @Test void findPersonsInEmptyDataSet()
  {
    // prepare
    List<Person> expected = new LinkedList<Person>();
    dataSet = prepareDatabase(new EmptyGroovyDataSet());
    
    // execute
    List<Person> persons = sut.findPersons()
    
    // verify
    //TODO access dataset when rowCount is implemented on tables
    assertThat(persons).hasSize(0)
  }
  
  @Test void addPerson()
  {
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
  
  @Ignore("Must run when the removeRow function is implemented")
  @Test void removePerson()
  {
    // prepare
    Person person = new Person()
    person.setFirstName("Dennis")
    person.setName("Kaulbersch")
    person.setJob(SWD.getId())
    person.setTeam(QA.getId())
    person.setId(dataSet.personsTable.findWhere.name(KAULBERSCH).getId())

    // execute
    sut.removePerson(person)
    
    // verify
    // TODO remove person / remove row
//    dataSet.personsTable.delete {
//      
//      first_name | name   | job | team
//      "Nikolaus" | "Moll" | SWD | QA
//      
//    }
    dbTester.assertDataBase(dataSet)
  }
  
  @Test(expected=DataIntegrityViolationException.class) void removePersonThatDoesNotExist()
  {
    // prepare
    dataSet = prepareDatabase(new EmptyGroovyDataSet())
    
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
  
  private PersonDatabaseBuilder prepareDatabaseWithDemoDataset() throws Exception
  {
    DemoGroovyDataSet dataSet = new DemoGroovyDataSet()
    prepareDatabase(dataSet)
  }

  private PersonDatabaseBuilder prepareDatabase(PersonDatabaseBuilder dataSet) throws Exception
  {
    ScopeRegistry.use(dataSet)
    dbTester.truncate(dataSet)
    dbTester.cleanInsert(dataSet)
    return dataSet
  }
  
}
