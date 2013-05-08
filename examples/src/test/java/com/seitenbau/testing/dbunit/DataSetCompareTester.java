package com.seitenbau.testing.dbunit;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.fest.assertions.Assertions;
import org.junit.Test;

import com.seitenbau.testing.dbunit.datasets.provider.impl.JavaDataSetProvider;
import com.seitenbau.testing.dbunit.datasets.provider.impl.STUDataSetProvider;

public class DataSetCompareTester
{
  JavaDataSetProvider javaDataSetProvider = new JavaDataSetProvider();
  STUDataSetProvider stuDataSetProvider = new STUDataSetProvider();
  
  @Test
  public void compareJavaDataSetWithSTUDataSet() throws DataSetException
  {
    // prepare
    IDataSet javaDataSet = javaDataSetProvider.getDataSet();
    IDataSet stuDataSet = stuDataSetProvider.getDataSet();
    // verify
    Assertions.assertThat(javaDataSet).isEqualTo(stuDataSet);
  }
}
