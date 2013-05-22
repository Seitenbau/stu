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

  public void addColumn(Column column)
  {
    _columns.add(column);
  }
  
  public Table addColumn(String dbColName, String type, String javaType)
  {
    _columns.add(new Column(this, dbColName, null, type, javaType, new Reference[] {}, new Flags[] {}));
    return this;
  }

  public Table addColumn(String dbColName, String javaName, String type, String javaType)
  {
    _columns.add(new Column(this, dbColName, javaName, type, javaType, new Reference[] {}, new Flags[] {}));
    return this;
  }

  public Table addColumn(String dbColName, String type, String javaType, Flags... flags)
  {
    _columns.add(new Column(this, dbColName, null, type, javaType, new Reference[] {}, flags));
    return this;
  }

  public Table addColumn(String dbColName, String type, String javaType, Column reference, Flags... flags)
  {
    final Reference ref = new Reference(reference, null, null, null, null);
    _columns.add(new Column(this, dbColName, null, type, javaType, new Reference[] { ref }, flags));
    return this;
  }

  public Table addColumn(String dbColName, DataType type, Column reference, Flags... flags)
  {
    final Reference ref = new Reference(reference, null, null, null, null);
    _columns.add(new Column(this, dbColName, null, type.getDataType(), type.getJavaType(), new Reference[] { ref },
        flags));
    return this;
  }

  public Table addColumn(String dbColName, DataType type)
  {
    return addColumn(dbColName, type.getDataType(), type.getJavaType());
  }

  public Table addColumn(String dbColName, String javaName, DataType type)
  {
    return addColumn(dbColName, javaName, type.getDataType(), type.getJavaType());
  }

  /** use Flag.AutoIncrement instead of boolean */
  @Deprecated
  // use Flag.AutoIncrement instead of boolean
  public Table addColumn(String dbColName, DataType type, boolean auto)
  {
    if (auto)
    {
      return addColumn(dbColName, type, Flags.AutoIncrement);
    }
    else
    {
      return addColumn(dbColName, type);
    }
  }

  public Table addColumn(String dbColName, DataType type, Flags... flags)
  {
    return addColumn(dbColName, type.getDataType(), type.getJavaType(), flags);
  }

  public Table addColumn(String name, DataType type, Class<?> javaType)
  {
    return addColumn(name, type.getDataType(), javaType.getCanonicalName());
  }

  public void setParent(DataSet dataSet)
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
  
  public String getJavaVariableName()
  {
    if (_javaName != null)
    {
      return CamelCase.makeFirstLowerCase(_javaName);
    }
    return CamelCase.makeFirstLowerCase(DataSet.makeNiceJavaName(_name));
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

  public Column getIdColumn()
  {
    for (Column col : getColumns())
    {
      if (col.isIdentifier())
      {
        return col;
      }
    }
    throw new RuntimeException("No ID column");
  }

  public boolean isAssociativeTable()
  {
    if (getColumns().size() != 2)
    {
      return false;
    }
    for (Column col : getColumns())
    {
      if (col.getReferences().size() == 0)
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
      for (Reference reference : col.getReferences()) {
        if (reference.getColumn().getTable() == table) {
          continue;
        }
        
        return reference.getColumn().getTable();
      }
    }
    
    return null;
    //throw new RuntimeException("No associating column found");
  }
  
}
