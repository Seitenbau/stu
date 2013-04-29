package com.seitenbau.testing.dbunit.manipulate;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;
import static org.fest.assertions.Assertions.*;
import com.seitenbau.testing.dbunit.modifier.IDataSetFilter;

public class RemoveTableColumnsTest
{
  @Test(expected = IllegalArgumentException.class)
  public void testNoTable()
  {
    RemoveTableColumns.fromTable(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExclude()
  {
    RemoveTableColumns.fromTable("Rainer").exclude();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInclude()
  {
    RemoveTableColumns.fromTable("Rainer").includeOnly();
  }

  @Test
  public void testExclude_OneColumn() throws Exception
  {
    IDataSetFilter sut = RemoveTableColumns.fromTable("Rainer").exclude("c2");
    IDataSet current = createDS("Rainer", "c1", "c2", "c3");

    // execute
    IDataSet result = sut.filter(current);

    // verify
    Column[] cols = result.getTable("Rainer").getTableMetaData().getColumns();
    assertThat(cols).onProperty("columnName").containsOnly("c1", "c3");
  }

  @Test
  public void testExclude_Include() throws Exception
  {
    IDataSetFilter sut = RemoveTableColumns.fromTable("Rainer").includeOnly("c2");
    IDataSet current = createDS("Rainer", "c1", "c2", "c3");

    // execute
    IDataSet result = sut.filter(current);

    // verify
    Column[] cols = result.getTable("Rainer").getTableMetaData().getColumns();
    assertThat(cols).onProperty("columnName").containsOnly("c2");
  }

  private IDataSet createDS(final String tablename, final String... columns)
  {
    DefaultDataSet ds = new DefaultDataSet();
    DefaultTable table = new DefaultTable(tablename)
    {
      @Override
      public ITableMetaData getTableMetaData()
      {
        Column[] cols = new Column[columns.length];
        int i = 0;
        for (String col : columns)
        {
          cols[i++] = new Column(col, DataType.VARBINARY);
        }
        return new DefaultTableMetaData(tablename, cols);
      }
    };
    ds.addTable(table);
    return ds;
  }

}
