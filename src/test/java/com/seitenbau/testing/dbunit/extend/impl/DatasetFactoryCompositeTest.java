package com.seitenbau.testing.dbunit.extend.impl;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import com.seitenbau.testing.dbunit.extend.DbUnitDatasetFactory;
import com.seitenbau.testing.mockito.MockitoRule;

public class DatasetFactoryCompositeTest
{
  @Test
  public void noParameters() throws DataSetException
  {
    DbUnitDatasetFactory result = DatasetFactoryComposite.of();
    assertThat(result.createDBUnitDataSet().getTableNames()).hasSize(0);
  }

  @Rule
  public MockitoRule rule = new MockitoRule();

  @Mock   DbUnitDatasetFactory datasetFactory1;
  @Mock   DbUnitDatasetFactory datasetFactory2;
  
  @Before
  public void setupMocks() {
    DefaultDataSet ds1 = new DefaultDataSet();
    ds1.addTable(new DefaultTable("rainer"));
    when(datasetFactory1.createDBUnitDataSet()).thenReturn(ds1);
    
    DefaultDataSet ds2 = new DefaultDataSet();
    ds2.addTable(new DefaultTable("rainer2"));
    when(datasetFactory2.createDBUnitDataSet()).thenReturn(ds2);
  }

  @Test
  public void oneParameters() throws DataSetException
  {
    // prepare
    DefaultDataSet ds = new DefaultDataSet();
    ds.addTable(new DefaultTable("rainer"));
    when(datasetFactory1.createDBUnitDataSet()).thenReturn(ds);

    // execute
    DbUnitDatasetFactory result = DatasetFactoryComposite.of(datasetFactory1);

    // verify
    assertThat(result.createDBUnitDataSet().getTableNames()).hasSize(1);
  }

  @Test
  public void twoParameters() throws DataSetException
  {
    // prepare


    // execute
    DbUnitDatasetFactory result = DatasetFactoryComposite.of(datasetFactory1,datasetFactory2);

    // verify
    assertThat(result.createDBUnitDataSet().getTableNames()).hasSize(2);
  }
}
