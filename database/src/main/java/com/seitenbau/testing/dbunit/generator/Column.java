package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.seitenbau.testing.util.CamelCase;

public class Column
{

  private static final String ID_SUFFIX = "_id";

  private final Table _table;

  private final String _type;

  private final String _javaType;

  private final String _name;

  private final String _javaName;

  private final String _description;

  private final Reference _reference;

  private final ColumnMetaData _metaData;

  private final List<Column> _referencedBy;

  Column(Table table, String name, String javaName, String description, String type, String javaType,
      Reference reference, Set<String> flags)
  {
    _table = table;
    _name = name;
    _javaName = javaName;
    _description = description;
    _type = type;
    _javaType = javaType;
    _reference = reference;

    _metaData = new ColumnMetaData(flags);

    _referencedBy = new ArrayList<Column>();
    if (reference != null)
    {
      _reference.getColumn()._referencedBy.add(this);
    }
  }

  public Table getTable()
  {
    return _table;
  }

  public String getJavaType()
  {
    return _javaType;
  }

  public String getType()
  {
    return _type;
  }

  public String getName()
  {
    return _name;
  }

  public String getJavaName()
  {
    return _javaName;
  }

  public String getJavaNameFirstLower()
  {
    return CamelCase.makeFirstLowerCase(getJavaName());
  }

  public String getDescription()
  {
    return _description;
  }

  public Reference getReference()
  {
    return _reference;
  }

  public ColumnMetaData getMetaData()
  {
    return _metaData;
  }

  public boolean isReferencingTable(Table table)
  {
    if (_reference == null)
    {
      return false;
    }

    return _reference.getColumn().getTable() == table;
  }

  public List<Column> getReferencedByList()
  {
    return Collections.unmodifiableList(_referencedBy);
  }

  public String getTruncatedReferenceName()
  {
    if (_reference == null || !_name.endsWith(ID_SUFFIX))
    {
      return null;
    }

    final String result = _name.substring(0, _name.length() - ID_SUFFIX.length());
    for (Column column : _table.getColumns())
    {
      if (result.equals(column.getName()))
      {
        // column cannot be truncated
        return null;
      }
    }

    return result;
  }

  public boolean isIdentifierColumn()
  {
    return _metaData.hasFlag(ColumnMetaData.IDENTIFIER);
  }
  
  public boolean isNextValueMethodGenerated()
  {
    return _metaData.hasFlag(ColumnMetaData.ADD_NEXT_METHOD);
  }
  
  public boolean isAutoInvokeValueGeneration()
  {
    return _metaData.hasFlag(ColumnMetaData.AUTO_INVOKE_NEXT);
  }

  public boolean isAutoIncrement()
  {
    return _metaData.hasFlag(ColumnMetaData.AUTO_INCREMENT);
  }
  
}
