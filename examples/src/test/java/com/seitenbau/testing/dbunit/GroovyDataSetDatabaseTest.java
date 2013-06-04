package com.seitenbau.testing.dbunit;

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
@ContextConfiguration({"/config/spring/context.xml", "/config/spring/test-context.xml"})
public class GroovyDataSetDatabaseTest
{

  @Rule
  public DatabaseTesterRule dbTester = new DatabaseTesterRule(TestConfig.class);

  @Autowired
  PersonService sut;
  
  DemoGroovyDataSet dataSet = new DemoGroovyDataSet();
  
  @Before
  public void setup() throws Exception {
    ScopeRegistry.use(dataSet);
    dbTester.truncate(dataSet.createDataSet());
    dbTester.cleanInsert(dataSet.createDataSet());
  }
  
  @Test
  public void findPersons() {
    assertThat(sut.findPersons()).hasSize(dataSet.personsTable.findWhere.teamId(QA).getRowCount());
  }
  
  @Test
  public void savePerson() throws Exception {
    // prepare
    Person person = new Person();
    person.setFirstName("Nikolaus");
    person.setName("Moll");
    person.setJob(SWD);
    person.setTeam(QA);

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
  
  
}
