package com.seitenbau.testing.dbunit;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.KAULBERSCH;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.QA;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.SWD;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.TM;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.model.JobsTable;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.util.Action;
import com.seitenbau.testing.util.Filter;


public class DataSetJavaAPITest 
{
  
  private final DemoGroovyDataSet dataSet = new DemoGroovyDataSet();
  
  @Test
  public void findWhereSWD()
  {
    assertThat(dataSet.jobsTable.findWhere.id(SWD).getTitle()).isEqualTo("Software Developer");
  }

  @Test
  public void findWhereQA()
  {
    assertThat(dataSet.teamsTable.findWhere.id(QA).getTitle()).isEqualTo("Quality Assurance");
  }

  @Test
  public void findWhereFirstName()
  {
    assertThat(dataSet.personsTable.findWhere.firstName("Dennis").getTeamId()).isEqualTo(1);
  }

  @Test
  public void findWhereRowCount()
  {
    assertThat(dataSet.personJobTable.findWhere.jobId(SWD).getRowCount()).isEqualTo(1);
  }

  @Test
  public void QAMemberSizeLazy()
  {
    assertThat(QA.getMembersize()).isEqualTo(3);
  }

  @Test
  public void QARowCount()
  {
    assertThat(dataSet.personsTable.findWhere.teamId(QA).getRowCount()).isEqualTo(3);
  }

  @Test
  public void QAMemberSizeLazyAccess()
  {
    assertThat(dataSet.teamsTable.findWhere.membersize(3).getTitle()).isEqualTo("Quality Assurance");
  }

  @Test
  public void getDennisLastName()
  {
    assertThat(KAULBERSCH.getName()).isEqualTo("Kaulbersch");
  }

  @Test
  public void getTMTitle()
  {
    assertThat(TM.getTitle()).isEqualTo("Team Manager");
  }

  @Test
  public void getTableRowCount()
  {
    assertThat(dataSet.personsTable.getRowCount()).isEqualTo(3);
  }
  
  @Test
  public void testForEach()
  {
    // linked list because other list would be immutable
    final List<String> names = new LinkedList<String>(Arrays.asList(new String[] { "Christian", "Dennis", "Julien" })); 
    dataSet.personsTable.foreach(new Action<RowBuilder_Persons>() {

      @Override
      public void call(RowBuilder_Persons value)
      {
        System.out.println("removing " + value.getFirstName());
        names.remove(value.getFirstName());
      }

    });
    assertThat(names.size()).isEqualTo(0);
  }

  @Test
  public void testFilter()
  {
    final Filter<RowBuilder_Persons> LEN_FILTER = new Filter<RowBuilder_Persons>() 
        {

          @Override
          public boolean accept(RowBuilder_Persons value)
          {
            return (value.getFirstName().length() == 6);
          }
          
        };
        
    assertThat(dataSet.personsTable.find(LEN_FILTER).getRowCount()).isEqualTo(2);
  }

  @Test
  public void testExistingFlag()
  {
    assertThat(JobsTable.getColumnMetaData("title").hasFlag("any_custom_flag")).isEqualTo(true);
  }

  @Test
  public void testMissingFlag()
  {
    assertThat(JobsTable.getColumnMetaData("title").hasFlag("no_custom_flag")).isEqualTo(false);
  }

}
