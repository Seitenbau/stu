package com.seitenbau.testing.dbunit;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.KAULBERSCH;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.QA;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.SWD;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.TM;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.fest.assertions.Fail;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.dsl.DataSetRegistry;
import com.seitenbau.testing.dbunit.model.JobsTable;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.rule.DatabaseSetup;
import com.seitenbau.testing.util.Action;
import com.seitenbau.testing.util.Filter;


public class DataSetJavaAPITest
{

  @Rule
  public ExpectedException exception = ExpectedException.none();

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
    assertThat(dataSet.personsTable.findWhere.teamId(QA).getRowCount()).isEqualTo(3);
  }

  @Test
  public void lazyQAMemberSize()
  {
    assertThat(QA.getMembersize()).isEqualTo(3);
  }

  @Test
  public void findWhereOnLazyValue()
  {
    assertThat(dataSet.teamsTable.findWhere.membersize(3).getTitle()).isEqualTo("Quality Assurance");
  }

  @Test
  public void personRefLastName()
  {
    assertThat(KAULBERSCH.getName()).isEqualTo("Kaulbersch");
  }

  @Test
  public void jobRefTitle()
  {
    assertThat(TM.getTitle()).isEqualTo("Team Manager");
  }

  @Test
  public void tableRowCount()
  {
    assertThat(dataSet.personsTable.getRowCount()).isEqualTo(3);
  }

  @Test
  public void forEach()
  {
    // linked list because other list would be immutable
    final List<String> names = new LinkedList<String>(Arrays.asList(new String[] { "Christian", "Dennis", "Julien" }));
    dataSet.personsTable.foreach(new Action<RowBuilder_Persons>() {

      @Override
      public void call(RowBuilder_Persons value)
      {
        names.remove(value.getFirstName());
      }

    });
    assertThat(names.size()).isEqualTo(0);
  }

  @Test
  public void findWithFilter()
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
  public void queryExistingFlag()
  {
    assertThat(JobsTable.getColumnMetaData("title").hasFlag("any_custom_flag")).isEqualTo(true);
  }

  @Test
  public void queryMissingFlag()
  {
    assertThat(JobsTable.getColumnMetaData("title").hasFlag("no_custom_flag")).isEqualTo(false);
  }

  @Test
  public void tableInsertRow()
  {
    // execute
    dataSet.personsTable.insertRow() //
      .setId(23)
      .setFirstName("Michael")
      .setName("Knight")
      .setTeamId(QA);

    // verify
    assertThat(dataSet.personsTable.findWhere.teamId(QA).getRowCount()).isEqualTo(4);
  }

  @Test
  public void tableDeleteRow()
  {
    // execute
    dataSet.personsTable.deleteRow(KAULBERSCH);

    // verify
    assertThat(dataSet.personsTable.findWhere.teamId(QA).getRowCount()).isEqualTo(2);
  }

  @Test
  public void findWhereWithNonExistingArgument()
  {
    // prepare
    exception.expect(RuntimeException.class);
    // execute
    dataSet.personsTable.findWhere.name("Majors");
    //verify
    Fail.fail();
  }

  @Test
  @DatabaseSetup(prepare = DemoGroovyDataSet.class)
  public void getWhereWithNonExistingArgument()
  {
    // execute
    dataSet.personsTable.getWhere.name("Majors");
  }

  @Test
  public void removeFromDataSet()
  {
    DataSetRegistry.use(dataSet);

    // execute
    KAULBERSCH.removeFromDataSet();

    // verify
    assertThat(QA.getMembersize()).isEqualTo(2);
    assertThat(dataSet.personsTable.getRowCount()).isEqualTo(2);
  }

}
