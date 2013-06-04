package com.seitenbau.testing.dbunit;

import static org.fest.assertions.Assertions.assertThat;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.Test;

import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.datasets.provider.impl.GroovyDataSetProvider;
import com.seitenbau.testing.dbunit.datasets.provider.impl.JavaDataSetProvider;
import com.seitenbau.testing.dbunit.datasets.provider.impl.STUDataSetProvider;

public class DataSetCompareTest
{
  JavaDataSetProvider javaDataSetProvider = new JavaDataSetProvider();

  STUDataSetProvider stuDataSetProvider = new STUDataSetProvider();
  
  GroovyDataSetProvider groovyDataSetProvider = new GroovyDataSetProvider();

  @Test
  public void compareJavaDataSetWithSTUDataSet() throws DataSetException
  {
    // prepare
    IDataSet javaDataSet = javaDataSetProvider.getDataSet();
    IDataSet stuDataSet = stuDataSetProvider.getDataSet();
    
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
    IDataSet javaDataSet = javaDataSetProvider.getDataSet();
    IDataSet groovyDataSet = groovyDataSetProvider.getDataSet();
    
    // verify
    assertThat(javaDataSet.getTableNames()).isEqualTo(groovyDataSet.getTableNames());

    for (String tableName : javaDataSet.getTableNames())
    {
      ITable javaTable = javaDataSet.getTable(tableName);
      ITable stuTable = groovyDataSet.getTable(tableName);
      assertThat(javaTable.getRowCount()).isEqualTo(stuTable.getRowCount());
      // TODO verify content
    }
  }
}
