package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.List;

import com.seitenbau.testing.util.CamelCase;

public class Table
{

  public static final String NAME_SUFFIX = "Table";

  String _name;

  DataSet _dataSet;

  String _javaName;

  List<Column> _columns = new ArrayList<Column>();

  public List<Column> getColumns()
  {
    return _columns;
  }

  public Table(String name)
  {
    _name = name;
  }

  public Table(String name, String javaName)
  {
    _name = name;
    _javaName = javaName;
  }

  public String getName()
  {
    return _name;
  }

  void addColumn(Column column)
  {
    _columns.add(column);
  }

  void setParent(DataSet dataSet)
  {
    _dataSet = dataSet;
  }

  public String getJavaName()
  {
    if (_javaName != null)
    {
      return CamelCase.makeFirstUpperCase(_javaName);
    }
    return DataSet.makeNiceJavaName(_name);
  }
  
  public String getJavaNameFirstLower()
  {
    return CamelCase.makeFirstLowerCase(getJavaName());
  }

  public DataSet getDataSet()
  {
    return _dataSet;
  }

  public String getPackage()
  {
    return getDataSet().getPackage();
  }

  public String getSuffix()
  {
    return NAME_SUFFIX;
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

  public Column getIdentifierColumn()
  {
    for (Column col : getColumns())
    {
      if (col.isIdentifierColumn())
      {
        return col;
      }
    }
    throw new RuntimeException("No Identifier column");
  }

  public boolean isAssociativeTable()
  {
    if (getColumns().size() != 2)
    {
      return false;
    }
    for (Column col : getColumns())
    {
      if (col.getReference() == null)
      {
        return false;
      }
    }

    return true;
  }

  public Table getAssociatedTable(Table table)
  {
    for (Column col : getColumns())
    {
      final Reference reference = col.getReference();
      if (reference == null) {
        continue;
      }
      if (reference.getColumn().getTable() == table) {
        continue;
      }
      
      return reference.getColumn().getTable();
    }
    
    return null;
    //throw new RuntimeException("No associating column found");
  }
  
  public Column getReferencingColumn(Table table)
  {
    for (Column col : getColumns())
    {
      final Reference reference = col.getReference();
      if (reference == null) {
        continue;
      }
      if (reference.getColumn().getTable() == table) {
        continue;
      }
      
      return col;
    }
    
    return null;
    //throw new RuntimeException("No associating column found");
  }
}
