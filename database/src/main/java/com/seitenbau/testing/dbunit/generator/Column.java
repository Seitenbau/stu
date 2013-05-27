package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.util.CamelCase;

public class Column
{

  private final String _type;

  private final String _javaType;

  private final String _name;

  private final String _javaName;

  private final Reference _reference;

  private final Table _table;
  
  private final boolean _isIdentifier;
  
  private final boolean _enableAutoIdHandling;
  
  private final boolean _isAutoIncrement;
  
  private final boolean _isNextIdMethodGenerated;

  Column(Table table, String name, String javaName, String type, String javaType, Reference reference,
      boolean isIdentifier, boolean isAutoIncrement, boolean addNextMethod, boolean enableAutoIdHandling)
  {
    _table = table;
    _name = name;
    _javaName = javaName;
    _type = type;
    _javaType = javaType;
    _reference = reference;
    _isAutoIncrement = isAutoIncrement;
    _isIdentifier = isIdentifier;
    _enableAutoIdHandling = enableAutoIdHandling;
    
    _isNextIdMethodGenerated = addNextMethod || isAutoIncrement || enableAutoIdHandling;
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

  public Reference getReference()
  {
    return _reference;
  }

  public boolean isAutoIncrement()
  {
    return _isAutoIncrement;
  }

  public boolean isIdGenerationAutoInvokeOnInsert()
  {
    return _enableAutoIdHandling;
  }

  public boolean isNextIdMethodGenerated()
  {
    return _isNextIdMethodGenerated; 
  }

  public String getJavaName()
  {
    if (_javaName != null)
    {
      return CamelCase.makeFirstUpperCase(_javaName);
    }
    String name = CamelCase.makeFirstOfBlockUppercase(_name);
    return CamelCase.makeFirstUpperCase(name); // old
  }

  public String getJavaNameFirstLower()
  {
    return CamelCase.makeFirstLowerCase(getJavaName());
  }

  public boolean isIdentifierColumn()
  {
    return _isIdentifier;
  }

  public boolean isReferencingTable(Table table)
  {
    if (_reference == null) {
      return false;
    }

    return _reference.getColumn().getTable() == table;
  }
  
  public String getTruncatedReferenceName()
  {
    if (_reference == null || !_name.endsWith("_id")) {
      return null;
    }
    
    final String result = _name.substring(0, _name.length() - 3);
    for (Column column : _table.getColumns()) {
      if (result.equals(column.getName())) {
        // column cannot be truncated
        return null;
      }
    }
    
    return result;
  }
}
