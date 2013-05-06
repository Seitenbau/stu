package com.seitenbau.testing.dbunit;

import static org.fest.assertions.Assertions.*;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.apache.zookeeper.KeeperException.DataInconsistencyException;
import org.fest.assertions.Assertions;
import org.fest.assertions.Fail;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seitenbau.testing.dbunit.config.TestConfig;
import com.seitenbau.testing.dbunit.dao.Lecture;
import com.seitenbau.testing.dbunit.dao.Professor;
import com.seitenbau.testing.dbunit.datasets.DefaultDataSet;
import com.seitenbau.testing.dbunit.datasets.EmptyDataSet;
import com.seitenbau.testing.dbunit.model.ProfessorTable.RowBuilder_Professor;
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
    EmptyDataSet emptyDataset = new EmptyDataSet();
    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Professor> result = sut.findProfessors();
    // verify
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void findAllProfessorsWithOneEntryInDataset() throws Exception
  {
    // prepare
    EmptyDataSet emptyDataset = new EmptyDataSet();
    emptyDataset.table_Professor.insertRow().setFirstName("Hansi").setName("Krankl").setTitle("Dipl.-Med.-Sys.-Wiss.")
        .setFaculty("Media");

    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Professor> result = sut.findProfessors();
    // verify
    assertThat(result).hasSize(1);
    Professor professor = result.get(0);
    assertThat(professor.getFirstName()).isEqualTo("Hansi");
  }

  @Test
  public void findAllProfessorsWithDefaultProfessorDataset() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    // execute
    List<Professor> result = sut.findProfessors();
    // verify
    assertThat(result).hasSize(3);
  }

  @Test
  public void addProfessorsToEmptyDataset() throws Exception
  {
    // prepare
    EmptyDataSet emptyDataset = new EmptyDataSet();
    dbTesterRule.cleanInsert(emptyDataset);

    Professor professor = new Professor();
    professor.setFirstName("Hansi");
    professor.setName("Krankl");
    professor.setTitle("Dipl.-Med.-Sys.-Wiss.");
    professor.setFaculty("Media");

    // execute
    boolean insertWasSuccessful = sut.addProfessor(professor);
    // verify
    assertThat(insertWasSuccessful).isTrue();
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void removeProfessorsFromDefaultDatasetWithExistingLecture() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
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
    sut.removeProfessor(professor);
    // verify
    Assert.fail();
  }
  
  @Test
  public void removeProfessorsFromDefaultDatasetWithoutExistingLecture() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    Professor professor = new Professor();
    professor.setFirstName("Paul");
    professor.setName("Breitner");
    professor.setTitle("Dr.");
    professor.setFaculty("Architecture");

    for (Professor currentProfessor : sut.findProfessors())
    {
      if (currentProfessor.getName().equals("Breitner"))
      {
        professor.setId(currentProfessor.getId());
        break;
      }
    }

    // execute
    int deletedRows = sut.removeProfessor(professor);
    // verify
    assertThat(deletedRows).isEqualTo(1);
  }

  @Test
  public void updateProfessorsFromDefaultDataset() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
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
    assertThat(updateWasSuccessful).isTrue();
  }

  // Lectures

  @Test
  public void findAllLecturesOnEmptyDataset() throws Exception
  {
    // prepare
    List<Professor> expected = new LinkedList<Professor>();
    EmptyDataSet emptyDataset = new EmptyDataSet();
    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Lecture> result = sut.findLectures();
    // verify
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void findAllLecturesWithOneEntryInDataset() throws Exception
  {
    // prepare
    EmptyDataSet emptyDataset = new EmptyDataSet();
    RowBuilder_Professor hansi = emptyDataset.table_Professor.insertRow().setFirstName("Hansi").setName("Krankl")
        .setTitle("Dipl.-Med.-Sys.-Wiss.").setFaculty("Media");

    emptyDataset.table_Lecture.insertRow().setName("Semiotik Today").setSws(2).setEcts(10).refProfessorId(hansi);

    dbTesterRule.cleanInsert(emptyDataset);

    // execute
    List<Lecture> result = sut.findLectures();
    // verify
    assertThat(result).hasSize(1);
    Lecture lecture = result.get(0);
    assertThat(lecture.getTitle()).isEqualTo("Semiotik Today");
    assertThat(lecture.getGivenBy()).isEqualTo(hansi.getId().intValue());
  }

  @Test
  public void findAllLecturesWithDefaultProfessorDataset() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
    dbTesterRule.cleanInsert(defaultDataSet);

    // execute
    List<Lecture> result = sut.findLectures();
    // verify
    assertThat(result).hasSize(2);
  }

  @Test
  public void addLectureToDefaultDataSet() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
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

    Lecture lecture = new Lecture();
    lecture.setGivenBy(professor.getId());
    lecture.setTitle("Operating Systems");
    lecture.setSemesterCredits(2);
    lecture.setWeeklyHours(2);
    // execute
    boolean result = sut.addLecture(lecture);
    // verify
    assertThat(result).isTrue();
  }

  @Test
  public void removeLectureFromDefaultDataset() throws Exception
  {
    // prepare
    DefaultDataSet defaultDataSet = new DefaultDataSet();
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

    Lecture lecture = new Lecture();
    lecture.setTitle("Semiotik Today");
    lecture.setWeeklyHours(2);
    lecture.setSemesterCredits(10);
    lecture.setGivenBy(professor.getId());

    for (Lecture currentLecture : sut.findLectures())
    {
      if (currentLecture.getTitle().equals("Semiotik Today"))
      {
        lecture.setId(currentLecture.getId());
        break;
      }
    }

    // execute
    int deletedRows = sut.removeLecture(lecture);
    // verify
    assertThat(deletedRows).isEqualTo(1);
  }
}
