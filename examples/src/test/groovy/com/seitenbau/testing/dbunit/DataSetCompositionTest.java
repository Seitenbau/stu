package com.seitenbau.testing.dbunit;

import static org.fest.assertions.Assertions.assertThat;

import org.dbunit.dataset.DataSetException;
import org.junit.Test;

import com.seitenbau.testing.dbunit.dataset.ExtendedDemoGroovyDataSet;

public class DataSetCompositionTest
{

  @Test
  public void checkExtendedDemoGroovyDataSet() throws DataSetException
  {
    ExtendedDemoGroovyDataSet dataSet = new ExtendedDemoGroovyDataSet();
    assertThat(dataSet.teamsTable.getRowCount()).isEqualTo(2);
    assertThat(dataSet.jobsTable.getRowCount()).isEqualTo(4);
    assertThat(dataSet.personsTable.getRowCount()).isEqualTo(3);
  }

}
