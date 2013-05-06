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
import com.seitenbau.testing.dbunit.datasets.DefaultProfessorDataSet;
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
  public void findAllProfessorsOnEmptyDataset() throws Exception
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
  public void findAllProfessorsWithOneEntryInDataset() throws Exception
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

  @Test
  public void findAllProfessorsWithDefaultProfessorDataset() throws Exception
  {
    // prepare
    DefaultProfessorDataSet defaultDataSet = new DefaultProfessorDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    // execute
    List<Professor> result = sut.findProfessors();
    // verify
    Assertions.assertThat(result).hasSize(3);
  }

  @Test
  public void addProfessorsToEmptyDataset() throws Exception
  {
    // prepare
    EmptyDataset emptyDataset = new EmptyDataset();
    dbTesterRule.cleanInsert(emptyDataset);

    Professor professor = new Professor();
    professor.setFirstName("Hansi");
    professor.setName("Krankl");
    professor.setTitle("Dipl.-Med.-Sys.-Wiss.");
    professor.setFaculty("Media");

    // execute
    boolean insertWasSuccessful = sut.addProfessor(professor);
    // verify
    Assertions.assertThat(insertWasSuccessful).isTrue();
  }

  @Test
  public void removeProfessorsFromDefaultDataset() throws Exception
  {
    // prepare
    DefaultProfessorDataSet defaultDataSet = new DefaultProfessorDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    Professor professor = new Professor();
    professor.setFirstName("Hansi");
    professor.setName("Krankl");
    professor.setTitle("Dipl.-Med.-Sys.-Wiss.");
    professor.setFaculty("Media");

    for (Professor currentProfessor : sut.findProfessors())
    {
      if (currentProfessor.getName().equals("Krankl"))
      {
        professor.setId(currentProfessor.getId());
        break;
      }
    }

    // execute
    int deletedRows = sut.removeProfessor(professor);
    // verify
    Assertions.assertThat(deletedRows).isEqualTo(1);
  }
  
  @Test
  public void updateProfessorsFromDefaultDataset() throws Exception
  {
    // prepare
    DefaultProfessorDataSet defaultDataSet = new DefaultProfessorDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    Professor professor = new Professor();
    professor.setFirstName("Hansi");
    professor.setName("Schmidt");
    professor.setTitle("Dipl.-Med.-Sys.-Wiss.");
    professor.setFaculty("Media");

    for (Professor currentProfessor : sut.findProfessors())
    {
      if (currentProfessor.getName().equals("Krankl"))
      {
        professor.setId(currentProfessor.getId());
        break;
      }
    }

    // execute
    boolean updateWasSuccessful = sut.updateProfessor(professor);
    // verify
    Assertions.assertThat(updateWasSuccessful).isTrue();
  }
}
