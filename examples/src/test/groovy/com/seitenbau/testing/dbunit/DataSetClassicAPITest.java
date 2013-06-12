package com.seitenbau.testing.dbunit;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*;
import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

import com.seitenbau.testing.dbunit.dataset.DemoClassicAPIDataSet;
;

public class DataSetClassicAPITest
{
  private DemoClassicAPIDataSet dataSet = new DemoClassicAPIDataSet();

  @Test
  public void testQAMemberSizeLazy()
  {
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
    assertThat(BARANOWSKI.getName()).isEqualTo("Baranowski");
  }
  
}
