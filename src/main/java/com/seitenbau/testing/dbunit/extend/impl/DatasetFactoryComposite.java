package com.seitenbau.testing.dbunit.extend.impl;

import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.dbunit.extend.DbUnitDatasetFactory;

public abstract class DatasetFactoryComposite
{
  public static DbUnitDatasetFactory of(final DbUnitDatasetFactory... factories)
  {
    return new DbUnitDatasetFactory()
    {
      public IDataSet createDBUnitDataSet()
      {
        List<IDataSet> datasets = new ArrayList<IDataSet>();
        for (DbUnitDatasetFactory factory : factories)
        {
          IDataSet ds = factory.createDBUnitDataSet();
          datasets.add(ds);
        }
        try
        {
          IDataSet[] cdsa = datasets.toArray(new IDataSet[0]);
          CompositeDataSet cds = new CompositeDataSet(cdsa);
          return cds;
        }
        catch (DataSetException e)
        {
          throw new RuntimeException(e);
        }
      }
    };
  }
}
