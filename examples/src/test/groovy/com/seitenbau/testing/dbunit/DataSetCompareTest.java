package com.seitenbau.testing.dbunit;

import static org.fest.assertions.Assertions.assertThat;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.Test;

import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.datasets.DefaultDataSet;
import com.seitenbau.testing.dbunit.datasets.JavaDataSet;

public class DataSetCompareTest
{

  @Test
  public void compareJavaDataSetWithSTUDataSet() throws DataSetException
  {
    // prepare
    IDataSet javaDataSet = new JavaDataSet();
    IDataSet stuDataSet = new DefaultDataSet().createDBUnitDataSet();
    
    // verify
    assertThat(javaDataSet.getTableNames()).isEqualTo(stuDataSet.getTableNames());

    for (String tableName : javaDataSet.getTableNames())
    {
      ITable javaTable = javaDataSet.getTable(tableName);
      ITable stuTable = stuDataSet.getTable(tableName);
      assertThat(javaTable.getRowCount()).isEqualTo(stuTable.getRowCount());
      // TODO verify content
    }
  }
  
  @Test
  public void compareJavaDataSetWithGroovyDataSet() throws DataSetException
  {
    // prepare
    IDataSet javaDataSet = new JavaDataSet();
    IDataSet groovyDataSet = new DemoGroovyDataSet().createDBUnitDataSet();
    
    // verify
    assertThat(javaDataSet.getTableNames()).isEqualTo(groovyDataSet.getTableNames());

    for (String tableName : javaDataSet.getTableNames())
    {
      ITable javaTable = javaDataSet.getTable(tableName);
      ITable groovyTable = groovyDataSet.getTable(tableName);
      assertThat(javaTable.getRowCount()).isEqualTo(groovyTable.getRowCount());
      // TODO verify content
    }
  }
}
