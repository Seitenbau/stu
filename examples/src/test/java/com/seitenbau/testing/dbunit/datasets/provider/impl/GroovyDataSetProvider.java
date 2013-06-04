package com.seitenbau.testing.dbunit.datasets.provider.impl;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.datasets.provider.DataSetProvider;

public class GroovyDataSetProvider implements DataSetProvider
{

  private IDataSet dataSet;

  public GroovyDataSetProvider()
  {
    dataSet = createDataSet();
  }

  private IDataSet createDataSet()
  {
    return new DemoGroovyDataSet().createDBUnitDataSet();
  }

  @Override
  public IDataSet getDataSet() throws DataSetException
  {
    if (dataSet == null)
    {
      throw new DataSetException("No dataSet provided");
    }
    else
    {
      return dataSet;
    }
  }

}
