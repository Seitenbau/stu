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

  public Table addColumn(String dbColName, String type, String javaType)
  {
    _columns.add(new Column(this, dbColName, null, type, javaType, null, new Flags[] {}));
    return this;
  }

  public Table addColumn(String dbColName, String javaName, String type, String javaType)
  {
    _columns.add(new Column(this, dbColName, javaName, type, javaType, null, new Flags[] {}));
    return this;
  }

  public Table addColumn(String dbColName, String type, String javaType, Flags... flags)
  {
    _columns.add(new Column(this, dbColName, null, type, javaType, null, flags));
    return this;
  }

  public Table addColumn(String dbColName, String type, String javaType, RelationDescription relation, Flags... flags)
  {
    _columns.add(new Column(this, dbColName, null, type, javaType, relation, flags));
    return this;
  }

  public Table addColumn(String dbColName, DataType type, RelationDescription relation, Flags... flags)
  {
    _columns.add(new Column(this, dbColName, null, type.getDataType(), type.getJavaType(), relation, flags));
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

  public Column getIdentifierColumn()
  {
    for (Column col : getColumns())
    {
      if (col.isIdentifier())
      {
        return col;
      }
    }
    return null;
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

  public boolean isAssociatingTable(Table table)
  {
    if (!isAssociativeTable())
    {
      return false;
    }
    final Column identifier = table.getIdentifierColumn();
    for (Column col : getColumns())
    {
      if (col.getReferences().contains(identifier))
      {
        return true;
      }
    }

    return false;
  }

  public String getAssociatedType(Table table)
  {
    if (!isAssociatingTable(table))
    {
      return "<No Assoc>";
    }
    final Column identifier = table.getIdentifierColumn();
    for (Column col : getColumns())
    {
      if (col.getReferences().contains(identifier))
      {
        continue;
      }
      return col.getReferences().get(0).getTable().getJavaName();
    }
    return "<None found>";
  }

}
