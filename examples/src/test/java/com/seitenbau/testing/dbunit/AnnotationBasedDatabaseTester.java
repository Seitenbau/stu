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
import com.seitenbau.testing.dbunit.dao.Professor;
import com.seitenbau.testing.dbunit.datasets.DefaultDataSet;
import com.seitenbau.testing.dbunit.rule.DatabaseSetup;
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.testing.dbunit.services.CRUDService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/config/spring/context.xml", "/config/spring/test-context.xml"})
@DatabaseSetup(prepare = DefaultDataSet.class, assertNoModification = false)
public class AnnotationBasedDatabaseTester
{
  @Rule
  public DatabaseTesterRule dbTesterRule = new DatabaseTesterRule(TestConfig.class);

  @Autowired
  CRUDService sut;

  @Test
  public void allProfessorsFromDefaultDataset() throws Exception
  {
    // prepare
    // execute
    List<Professor> result = sut.findProfessors();
    // verify
    Assertions.assertThat(result).hasSize(3);
  }
}
