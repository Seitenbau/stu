package com.seitenbau.testing.dbunit.groovy

import static org.fest.assertions.Assertions.*;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seitenbau.testing.dbunit.config.TestConfig;
import com.seitenbau.testing.dbunit.dao.Person;
import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.dsl.ScopeRegistry;
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.testing.dbunit.services.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(["/config/spring/context.xml", "/config/spring/test-context.xml"])
class GroovyDatabaseDataSetTest
{

  @Rule
  public DatabaseTesterRule dbTester = new DatabaseTesterRule(TestConfig.class);

  @Autowired
  PersonService sut;
  
  DemoGroovyDataSet dataSet = new DemoGroovyDataSet();
  
  @Before void setup() {
    ScopeRegistry.use(dataSet);
    
    dbTester.truncate(dataSet.createDataSet());
    dbTester.cleanInsert(dataSet.createDataSet());
  }
  
  @Test void savePerson() {
    // prepare
    Person person = new Person();
    person.setFirstName("Nikolaus");
    person.setName("Moll");
    person.setJob(SWD.getId());
    person.setTeam(QA.getId());

    // execute
    sut.addPerson(person);
    
    // verify
    dataSet.personsTable.rows {
      
      first_name | name   | job | team
      "Nikolaus" | "Moll" | SWD | QA
      
    }
    dbTester.assertDataBase(dataSet.createDataSet());
  }
  
}
