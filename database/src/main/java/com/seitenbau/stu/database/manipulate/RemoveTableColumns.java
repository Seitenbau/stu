package com.seitenbau.stu.database.manipulate;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.CompositeTable;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.FilteredTableMetaData;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.filter.IColumnFilter;

import com.seitenbau.stu.database.modifier.IDataSetFilter;

public class RemoveTableColumns implements IDataSetFilter
{
  private String fTableName;

  private String[] fToExcludeColumns;

  private String[] fToIncludeColumns;

  private Boolean _removeAll;

  protected RemoveTableColumns()
  {
  }

  public static Builder fromTable(String name)
  {
    if (name == null || name.trim().length() == 0)
    {
      throw new IllegalArgumentException("tablename invalid " + name);
    }
    return new Builder(name);
  }

  public static class Builder
  {

    private final String _name;

    public Builder(String name)
    {
      _name = name;
    }

    public IDataSetFilter includeOnly(String... columnToInclude)
    {
      if (columnToInclude == null || columnToInclude.length == 0)
      {
        throw new IllegalArgumentException("at least one column needed");
      }
      RemoveTableColumns newRemoveTableColumns = new RemoveTableColumns();
      newRemoveTableColumns.fTableName = _name;
      newRemoveTableColumns.fToIncludeColumns = columnToInclude;
      newRemoveTableColumns._removeAll = true;
      return newRemoveTableColumns;
    }

    public IDataSetFilter exclude(String... columnToExclude)
    {
      if (columnToExclude == null || columnToExclude.length == 0)
      {
        throw new IllegalArgumentException("at least one column needed");
      }
      RemoveTableColumns newRemoveTableColumns = new RemoveTableColumns();
      newRemoveTableColumns.fTableName = _name;
      newRemoveTableColumns.fToExcludeColumns = columnToExclude;
      newRemoveTableColumns._removeAll = false;
      return newRemoveTableColumns;
    }

  }

  public IDataSet filter(IDataSet current) throws Exception
  {
    return filterOutTableColumns(current);
  }

  public IDataSet filterOutTableColumns(IDataSet theDataSet) throws Exception
  {
    DefaultDataSet newDataSet = new DefaultDataSet();
    for (ITableIterator iter = theDataSet.iterator(); iter.next();)
    {
      ITable oldtable = iter.getTable();
      IColumnFilter rowFilter = new IColumnFilter()
      {
        public boolean accept(String tableName, Column column)
        {
          // This is not the table at all
          if (fTableName == null)
          {
            return true;
          }
          else if (!fTableName.equalsIgnoreCase(tableName))
          {
            return true;
          }
          // should this be excluded ?
          Boolean filterOut = null;
          if (fToExcludeColumns != null)
          {
            for (String col : fToExcludeColumns)
            {
              if (col.equalsIgnoreCase(column.getColumnName()))
              {
                filterOut = true;
                break;
              }
            }
          }
          // should this be included
          if (fToIncludeColumns != null)
          {
            for (String col : fToIncludeColumns)
            {
              if (col.equalsIgnoreCase(column.getColumnName()))
              {
                filterOut = false;
                break;
              }
            }
          }
          // global remove
          if (filterOut == null)
          {
            filterOut = _removeAll;
          }

          return !filterOut;
        }
      };
      newDataSet.addTable(new CompositeTable(new FilteredTableMetaData(oldtable.getTableMetaData(), rowFilter), oldtable));
    }
    return newDataSet;
  }

}
