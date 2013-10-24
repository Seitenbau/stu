package com.seitenbau.stu.dbunit.internal;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.UnknownDataType;

/**
 * Helper class to decorate a TableDecorator so all Compares will be
 * handled by the {@link DataTypeCompareDecorator} class.
 */
public class TableCompareDecorator extends TableDecorator
{

  public TableCompareDecorator(ITable delegateTo)
  {
    super(delegateTo);
  }

  public ITableMetaData getTableMetaData()
  {
    ITableMetaData tableMetaData = getDelegate().getTableMetaData();
    DefaultTableMetaData defaultTableMetaData = null;
    try
    {
      Column[] newColumns = new Column[tableMetaData.getColumns().length];
      Column[] columns = tableMetaData.getColumns();
      for (int i = 0; i < columns.length; i++)
      {
        Column column = columns[i];
        if (column.getDataType() instanceof UnknownDataType)
        {
          newColumns[i] = columns[i];
        }
        else
        {
          newColumns[i] = new Column(column.getColumnName(), new DataTypeCompareDecorator(column.getDataType()));
        }
      }
      defaultTableMetaData = new DefaultTableMetaData(tableMetaData.getTableName(), newColumns);
    }
    catch (DataSetException e)
    {
      throw new RuntimeException(e);
    }
    return defaultTableMetaData;
  }

}
