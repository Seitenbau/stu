package com.seitenbau.testing.dbunit.manipulate;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.CompositeTable;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.FilteredTableMetaData;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.filter.IColumnFilter;

import com.seitenbau.testing.dbunit.modifier.IDataSetFilter;

/**
 * @deprecated use RemoveTableColumns instead
 *             RemoveTableColumns.fromTable
 *             ("tablename").includeOnly("col1","col2");
 */
public class RemoveColumnFromTable implements IDataSetFilter
{
  private String fTableName;

  private String[] fColumns;

  public RemoveColumnFromTable(String tableName, String... columns)
  {
    fTableName = tableName;
    fColumns = columns;
  }

  public IDataSet filter(IDataSet current) throws Exception
  {
    return filterOutTableColumns(current, fTableName, fColumns);
  }

  public static IDataSet filterOutTableColumns(IDataSet theDataSet, final String inTableName,
      final String... columnToFilterOut) throws Exception
  {
    if (columnToFilterOut == null)
    {
      return theDataSet;
    }
    DefaultDataSet ds = new DefaultDataSet();
    for (ITableIterator iter = theDataSet.iterator(); iter.next();)
    {
      ITable oldtable = iter.getTable();
      IColumnFilter rowFilter = new IColumnFilter()
      {
        public boolean accept(String tableName, Column column)
        {
          boolean filter = true;
          for (String col : columnToFilterOut)
          {
            if (col.equals(column.getColumnName()))
            {
              filter = false;
              break;
            }
          }
          if (!filter)
          {
            if (inTableName == null)
            {
              return false;
            }
            else if (inTableName.equals(tableName))
            {
              return false;
            }
          }
          return true;
        }
      };
      ds.addTable(new CompositeTable(new FilteredTableMetaData(oldtable.getTableMetaData(), rowFilter), oldtable));
    }
    return ds;
  }
}
