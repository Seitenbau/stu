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
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.dataset.SubQueryGroovyDataSet;
import com.seitenbau.testing.dbunit.dsl.DataSetRegistry;
import com.seitenbau.testing.dbunit.model.JobsTable;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowCollection_Persons;
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
    DataSetRegistry.use(dataSet);
    assertThat(dataSet.jobsTable.findWhere.id(SWD).getTitle()).isEqualTo("Software Developer");
  }

  @Test
  public void findWhereQA()
  {
    DataSetRegistry.use(dataSet);
    assertThat(dataSet.teamsTable.findWhere.id(QA).getTitle()).isEqualTo("Quality Assurance");
  }

  @Test
  public void findWhereFirstName()
  {
    DataSetRegistry.use(dataSet);
    assertThat(dataSet.personsTable.findWhere.firstName("Dennis").getTeamId()).isEqualTo(1);
  }

  @Test
  public void findWhereRowCount()
  {
    DataSetRegistry.use(dataSet);
    assertThat(dataSet.personJobTable.findWhere.jobId(SWD).getRowCount()).isEqualTo(1);
    assertThat(dataSet.personsTable.findWhere.teamId(QA).getRowCount()).isEqualTo(3);
  }

  @Test
  public void findWhereSubQueries()
  {
    SubQueryGroovyDataSet testDataSet = new SubQueryGroovyDataSet();
    DataSetRegistry.use(testDataSet);

    RowCollection_Persons firstQuery = testDataSet.personsTable.findWhere.firstName("Hans");
    RowCollection_Persons subQuery = firstQuery.where.name("Wurst");

    assertThat(firstQuery.getRowCount()).isEqualTo(2);
    assertThat(subQuery.getRowCount()).isEqualTo(1);
  }

  @Test
  public void quietFindWhereSubQueries()
  {
    SubQueryGroovyDataSet testDataSet = new SubQueryGroovyDataSet();

    RowCollection_Persons firstQuery = testDataSet.personsTable.quietFindWhere.firstName("Hans");
    RowCollection_Persons secondQuery = firstQuery.where.name("Wurst");
    RowCollection_Persons thirdQuery = firstQuery.where.name("invalid name");

    assertThat(firstQuery.getRowCount()).isEqualTo(2);
    assertThat(secondQuery.getRowCount()).isEqualTo(1);
    assertThat(thirdQuery.getRowCount()).isEqualTo(0);
  }

  @Test
  public void getWhereOnMultipleValues()
  {
    //given
    SubQueryGroovyDataSet testDataSet = new SubQueryGroovyDataSet();

    try {
      //when
      testDataSet.personsTable.getWhere.firstName("Hans");
      //then
      Assert.fail();
    } catch (RuntimeException e) {}
  }

  @Test
  public void lazyQAMemberSize()
  {
    DataSetRegistry.use(dataSet);
    assertThat(QA.getMembersize()).isEqualTo(3);
  }

  @Test
  public void findWhereOnLazyValue()
  {
    DataSetRegistry.use(dataSet);
    assertThat(dataSet.teamsTable.findWhere.membersize(3).getTitle()).isEqualTo("Quality Assurance");
  }

  @Test
  public void personRefLastName()
  {
    DataSetRegistry.use(dataSet);
    assertThat(KAULBERSCH.getName()).isEqualTo("Kaulbersch");
  }

  @Test
  public void jobRefTitle()
  {
    DataSetRegistry.use(dataSet);
    assertThat(TM.getTitle()).isEqualTo("Team Manager");
  }

  @Test
  public void tableRowCount()
  {
    DataSetRegistry.use(dataSet);
    assertThat(dataSet.personsTable.getRowCount()).isEqualTo(3);
  }

  @Test
  public void getRows()
  {
    DataSetRegistry.use(dataSet);
    // linked list because other list would be immutable
    final List<String> names = new LinkedList<String>(Arrays.asList(new String[] { "Christian", "Dennis", "Julien" }));
    for (RowBuilder_Persons row : dataSet.personsTable.getRows())
    {
      if (!names.remove(row.getFirstName()))
      {
        Fail.fail("Element not in list");
      }
    }
    assertThat(names.size()).isEqualTo(0);
  }

  @Test
  public void forEach()
  {
    DataSetRegistry.use(dataSet);
    // linked list because other list would be immutable
    final List<String> names = new LinkedList<String>(Arrays.asList(new String[] { "Christian", "Dennis", "Julien" }));
    dataSet.personsTable.foreach(new Action<RowBuilder_Persons>() {

      @Override
      public void call(RowBuilder_Persons value)
      {
        if (!names.remove(value.getFirstName()))
        {
          Fail.fail("Element not in list");
        }
      }

    });
    assertThat(names.size()).isEqualTo(0);
  }

  @Test
  public void findWithFilter()
  {
    DataSetRegistry.use(dataSet);
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
    DataSetRegistry.use(dataSet);
    assertThat(JobsTable.getColumnMetaData("title").hasFlag("any_custom_flag")).isEqualTo(true);
  }

  @Test
  public void queryMissingFlag()
  {
    DataSetRegistry.use(dataSet);
    assertThat(JobsTable.getColumnMetaData("title").hasFlag("no_custom_flag")).isEqualTo(false);
  }

  @Test
  public void tableInsertRow()
  {
    DataSetRegistry.use(dataSet);

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
    DataSetRegistry.use(dataSet);

    // execute
    dataSet.personsTable.deleteRow(KAULBERSCH);

    // verify
    assertThat(dataSet.personsTable.findWhere.teamId(QA).getRowCount()).isEqualTo(2);
  }

  @Test
  public void findWhereWithNonExistingArgument()
  {
    DataSetRegistry.use(dataSet);

    // prepare
    exception.expect(RuntimeException.class);
    // execute
    dataSet.personsTable.findWhere.name("Majors");
    //verify
    Fail.fail();
  }

  @Test
  public void getWhereWithNonExistingArgument()
  {
    DataSetRegistry.use(dataSet);

    // execute
    dataSet.personsTable.getWhere.name("Majors");
  }

  @Test
  public void removeFromDataSet()
  {
    DataSetRegistry.use(dataSet);

    // remember id because it cannot be used after removeFromDataSet
    long id = KAULBERSCH.getId();

    // execute
    KAULBERSCH.removeFromDataSet();

    // verify
    assertThat(QA.getMembersize()).isEqualTo(2);
    assertThat(dataSet.personsTable.getRowCount()).isEqualTo(2);
    assertThat(dataSet.personJobTable.getWhere.personId(id).isPresent()).isEqualTo(false);
  }

  @Test
  public void removedReferenceAccess()
  {
    DataSetRegistry.use(dataSet);

    // prepare
    exception.expect(RuntimeException.class);

    // execute
    KAULBERSCH.removeFromDataSet();

    // verify
    KAULBERSCH.getId();  // access rowbuilder
    Fail.fail();
  }

  @Test
  public void setRefOnValueColumn()
  {
    DataSetRegistry.use(dataSet);

    // prepare
    exception.expect(RuntimeException.class);

    // verify
    dataSet.jobsTable.insertRow().setTitleRaw(KAULBERSCH);

    Fail.fail();
  }

  @Test
  public void setIllegalRef()
  {
    DataSetRegistry.use(dataSet);

    // prepare
    exception.expect(RuntimeException.class);

    // verify
    dataSet.personsTable.insertRow().setTeamIdRaw(KAULBERSCH);

    Fail.fail();
  }
}
