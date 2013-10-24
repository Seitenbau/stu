package com.seitenbau.stu.database;

import static com.seitenbau.stu.database.PersonDatabaseRefs.BARANOWSKI;
import static com.seitenbau.stu.database.PersonDatabaseRefs.QA;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.seitenbau.stu.database.dataset.DemoClassicAPIDataSet;
import com.seitenbau.stu.database.dsl.DataSetRegistry;
;

public class DataSetClassicAPITest
{
  private DemoClassicAPIDataSet dataSet = new DemoClassicAPIDataSet();

  @Test
  public void testQAMemberSizeLazy()
  {
    DataSetRegistry.use(dataSet);
    assertThat(QA.getMembersize()).isEqualTo(3);
  }

  @Test
  public void testBaranowskiFirstName()
  {
    assertThat(dataSet.table_Persons.getWhere.id(BARANOWSKI).get().getFirstName()).isEqualTo("Christian");
  }

  @Test
  public void testBaranowskiLastName()
  {
    DataSetRegistry.use(dataSet);
    assertThat(BARANOWSKI.getName()).isEqualTo("Baranowski");
  }
  
}
