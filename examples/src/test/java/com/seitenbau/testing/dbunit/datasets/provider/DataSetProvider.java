package com.seitenbau.testing.dbunit.datasets.provider;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;

public interface DataSetProvider
{
  IDataSet getDataSet() throws DataSetException;
}
