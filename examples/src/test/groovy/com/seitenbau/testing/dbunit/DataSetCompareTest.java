package com.seitenbau.testing.dbunit;

import static org.fest.assertions.Assertions.assertThat;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.Ignore;
import org.junit.Test;

import com.seitenbau.testing.dbunit.dataset.DemoClassicAPIDataSet;
import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.datasets.DefaultDataSet;
import com.seitenbau.testing.dbunit.datasets.JavaDataSet;

public class DataSetCompareTest
{

  @Ignore
  @Test
  public void compareJavaDataSetWithSTUDataSet() throws DataSetException
  {
    // prepare
    IDataSet javaDataSet = new JavaDataSet();
    IDataSet stuDataSet = new DefaultDataSet().createDBUnitDataSet();

    // verify
    assertEquality(javaDataSet, stuDataSet);
  }

  @Ignore
  @Test
  public void compareJavaDataSetWithGroovyDataSet() throws DataSetException
  {
    // prepare
    IDataSet javaDataSet = new JavaDataSet();
    IDataSet groovyDataSet = new DemoGroovyDataSet().createDBUnitDataSet();

    // verify
    assertEquality(javaDataSet, groovyDataSet);
  }

  @Ignore
  @Test
  public void compareJavaDataSetWithClassicAPIDataSet() throws DataSetException
  {
    // prepare
    IDataSet javaDataSet = new JavaDataSet();
    IDataSet classicAPIDataSet = new DemoClassicAPIDataSet().createDBUnitDataSet();

    // verify
    assertEquality(javaDataSet, classicAPIDataSet);
  }

  private void assertEquality(IDataSet first, IDataSet second) throws DataSetException
  {
    assertThat(first.getTableNames()).isEqualTo(second.getTableNames());

    for (String tableName : first.getTableNames())
    {
      ITable firstTable = first.getTable(tableName);
      ITable secondTable = second.getTable(tableName);
      assertThat(firstTable.getRowCount()).isEqualTo(secondTable.getRowCount());

      Column[] firstColumns = firstTable.getTableMetaData().getColumns();
      Column[] secondColumns = secondTable.getTableMetaData().getColumns();
      assertThat(firstColumns).isEqualTo(secondColumns);

      for (int row = 0; row < firstTable.getRowCount(); row++)
      {
        for (Column column : firstColumns)
        {
          Object firstValue = firstTable.getValue(row, column.getColumnName());
          Object secondValue = firstTable.getValue(row, column.getColumnName());
          assertThat(firstValue).isEqualTo(secondValue);
        }
      }
    }
  }
}
