package com.seitenbau.testing.dbunit.groovy

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*
import static org.fest.assertions.Assertions.assertThat

import org.junit.Test

import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet
import com.seitenbau.testing.dbunit.dsl.DataSetRegistry
import com.seitenbau.testing.dbunit.model.JobsTable


public class DataSetGroovyAPITest 
{
  
  DemoGroovyDataSet dataSet = new DemoGroovyDataSet()
  
  @Test
  void findWhereSWD()
  {
    assertThat(dataSet.jobsTable.findWhere.id(SWD).title).isEqualTo("Software Developer");
  }

  @Test
  void findWhereQA()
  {
    assertThat(dataSet.teamsTable.findWhere.id(QA).title).isEqualTo("Quality Assurance");
  }

  @Test
  void findWhereFirstName()
  {
    assertThat(dataSet.personsTable.findWhere.firstName("Dennis").teamId).isEqualTo(1);
  }

  @Test
  void findWhereRowCount()
  {
    assertThat(dataSet.personJobTable.findWhere.jobId(SWD).rowCount).isEqualTo(1);
  }

  @Test
  void QAMemberSizeLazy()
  {
    DataSetRegistry.use(dataSet);
    assertThat(QA.membersize).isEqualTo(3);
  }

  @Test
  void QARowCount()
  {
    assertThat(dataSet.personsTable.findWhere.teamId(QA).rowCount).isEqualTo(3);
  }

  @Test
  void QAMemberSizeLazyAccess()
  {
    assertThat(dataSet.teamsTable.findWhere.membersize(3).title).isEqualTo("Quality Assurance");
  }

  @Test
  void getDennisLastName()
  {
    assertThat(KAULBERSCH.name).isEqualTo("Kaulbersch");
  }

  @Test
  void getTMTitle()
  {
    assertThat(TM.title).isEqualTo("Team Manager");
  }

  @Test
  void getTableRowCount()
  {
    assertThat(dataSet.personsTable.rowCount).isEqualTo(3);
  }
  
  @Test
  void testForEach()
  {
    List<String> names = ["Christian", "Dennis", "Julien"] 
    dataSet.personsTable.foreach({ names.remove(it.firstName) })
    assertThat(names.size()).isEqualTo(0);
  }

  @Test
  void testFilter()
  {
    assertThat(dataSet.personsTable.find({ it.firstName.length() == 6 }).rowCount).isEqualTo(2);
  }

  @Test
  void testExistingFlag()
  {
    assertThat(JobsTable.getColumnMetaData("title").hasFlag("any_custom_flag")).isEqualTo(true);
  }

  @Test
  void testMissingFlag()
  {
    assertThat(JobsTable.getColumnMetaData("title").hasFlag("no_custom_flag")).isEqualTo(false);
  }

}
