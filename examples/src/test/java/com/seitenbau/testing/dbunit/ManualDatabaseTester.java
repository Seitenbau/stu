package com.seitenbau.testing.dbunit;

import java.util.LinkedList;
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
import com.seitenbau.testing.dbunit.datasets.EmptyDataset;
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.testing.dbunit.services.CRUDService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/config/spring/context.xml", "/config/spring/test-context.xml"})
public class ManualDatabaseTester
{
  @Rule
  public DatabaseTesterRule dbTesterRule = new DatabaseTesterRule(TestConfig.class);

  @Autowired
  CRUDService sut;

  @Test
  public void emptyDataset() throws Exception
  {
    // prepare
    List<Professor> expected = new LinkedList<Professor>();
    EmptyDataset emptyDataset = new EmptyDataset();
    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Professor> result = sut.findProfessors();
    // verify
    Assertions.assertThat(result).isEqualTo(expected);
  }

  @Test
  public void oneEntryInDataset() throws Exception
  {
    // prepare
    EmptyDataset emptyDataset = new EmptyDataset();
    emptyDataset.table_Professor.insertRow().setFirstName("Hansi").setName("Krankl").setTitle("Dipl.-Med.-Sys.-Wiss.")
        .setFaculty("Media");

    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Professor> result = sut.findProfessors();
    // verify
    Assertions.assertThat(result).hasSize(1);
    Professor professor = result.get(0);
    Assertions.assertThat(professor.getFirstName()).isEqualTo("Hansi");
  }
}
