package com.seitenbau.stu.dbunit;

import static com.seitenbau.stu.dbunit.PersonDatabaseRefs.*;
import static org.fest.assertions.Assertions.assertThat;

import org.dbunit.dataset.DataSetException;
import org.fest.assertions.Fail;
import org.junit.Test;

import com.seitenbau.stu.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.stu.dbunit.dataset.ExtendedDemoGroovyDataSet;
import com.seitenbau.stu.dbunit.dsl.DataSetRegistry;

public class DataSetCompositionTest
{

  @Test
  public void checkExtendedDemoGroovyDataSet() throws DataSetException
  {
    // prepare
    ExtendedDemoGroovyDataSet dataSet = new ExtendedDemoGroovyDataSet();

    // verify
    assertThat(dataSet.teamsTable.getRowCount()).isEqualTo(2);
    assertThat(dataSet.jobsTable.getRowCount()).isEqualTo(4);
    assertThat(dataSet.personsTable.getRowCount()).isEqualTo(4);
  }
  
  @Test
  public void checkValidRefUsage() throws DataSetException
  {
    // prepare
    DataSetRegistry.use(new ExtendedDemoGroovyDataSet());

    // verify
    assertThat(SAT.getTitle()).isEqualTo("Software Architect");
  }

  @Test(expected=RuntimeException.class)
  public void checkInvalidRefUsage() throws DataSetException
  {
    // prepare
    DataSetRegistry.use(new DemoGroovyDataSet());

    // verify
    // throws a RuntimeException
    assertThat(SAT.getTitle()).isEqualTo("Software Architect");

    Fail.fail();
  }
  
  
}
