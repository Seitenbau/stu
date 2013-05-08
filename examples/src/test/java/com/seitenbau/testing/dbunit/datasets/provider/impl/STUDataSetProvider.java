package com.seitenbau.testing.dbunit.datasets.provider.impl;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.dbunit.datasets.DefaultDataSet;
import com.seitenbau.testing.dbunit.datasets.provider.DataSetProvider;

public class STUDataSetProvider implements DataSetProvider
{

  private IDataSet dataSet;

  public STUDataSetProvider()
  {
    dataSet = createDataSet();
  }

  private IDataSet createDataSet()
  {
    return new DefaultDataSet().createDBUnitDataSet();
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
