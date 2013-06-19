package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.seitenbau.testing.util.CamelCase;

public class Table
{

  public static final String NAME_SUFFIX = "Table";

  private DataSet _dataSet;

  private final String _name;

  private final String _javaName;

  private final String _description;

  private final List<Column> _columns;

  public Table(String name, String javaName, String description, List<ColumnBuilder> columnBuilders)
  {
    _name = name;
    _javaName = javaName;
    _description = description;
    _columns = new ArrayList<Column>();
    for (ColumnBuilder columnBuilder : columnBuilders) {
      _columns.add(columnBuilder.buildColumn(this));
    }
  }

  public String getName()
  {
    return _name;
  }

  public String getDescription()
  {
    return _description;
  }

  public List<Column> getColumns()
  {
    return Collections.unmodifiableList(_columns);
  }

  public String getJavaName()
  {
    return _javaName;
  }

  public String getJavaNameFirstLower()
  {
    return CamelCase.makeFirstLowerCase(getJavaName());
  }

  public String getPackage()
  {
    return getDataSet().getPackage();
  }

  public String getSuffix()
  {
    return NAME_SUFFIX;
  }

  void setParent(DataSet dataSet)
  {
    _dataSet = dataSet;
  }

  public DataSet getDataSet()
  {
    return _dataSet;
  }

  public Column ref(String colName)
  {
    for (Column col : getColumns())
    {
      if (col.getName().equals(colName))
      {
        return col;
      }
    }
    throw new RuntimeException("No column " + colName);
  }

  public List<Column> getUniqueColumns()
  {
    List<Column> result = new LinkedList<Column>();
    for (Column col : getColumns())
    {
      if (col.isUnique())
      {
        result.add(col);
      }
    }
    return result;
  }

  public boolean isAssociativeTable()
  {
    int foreignKeys = 0;
    if (getColumns().size() < 2)
    {
      return false;
    }
    for (Column col : getColumns())
    {
      if (col.isUnique())
      {
        return false;
      }
      if (col.getReference() != null)
      {
        foreignKeys++;
      }
    }

    return (foreignKeys == 2);
  }

  public Table getAssociatedTable(Table table)
  {
    for (Column col : getColumns())
    {
      final Relation reference = col.getReference();
      if (reference == null)
      {
        continue;
      }
      if (reference.getColumn().getTable() == table)
      {
        continue;
      }

      return reference.getColumn().getTable();
    }

    return null;
    // throw new RuntimeException("No associating column found");
  }

  public Column getAssociatedColumn(Table table)
  {
    for (Column col : getColumns())
    {
      final Relation reference = col.getReference();
      if (reference == null)
      {
        continue;
      }
      if (reference.getColumn().getTable() == table)
      {
        continue;
      }

      return col;
    }

    return null;
    // throw new RuntimeException("No associating column found");
  }


  public Set<Table> getAssociatedTables()
  {
    Set<Table> result = new HashSet<Table>();
    result.add(this);
    for (Column col : getColumns())
    {
      if (col.getReference() != null)
      {
        result.add(col.getReference().getTable());
      }
    }
    return result;
  }

  public Set<Table> getAssociatingTables()
  {
    Set<Table> result = new HashSet<Table>();
    for (Column col : getColumns())
    {
      for (Column refCol : col.getReferencedByList())
      {
        result.add(refCol.getTable());
      }
    }
    return result;
  }

  public Column getIdentifierColumn()
  {
    for (Column col : getColumns())
    {
      if (col.isIdentifier())
      {
        return col;
      }
    }
    throw new RuntimeException("No identifier column found");
  }
}
