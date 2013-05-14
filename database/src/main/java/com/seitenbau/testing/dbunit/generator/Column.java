package com.seitenbau.testing.dbunit.generator;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import com.seitenbau.testing.util.CamelCase;

public class Column
{

  String _type;

  String _javaType;

  String _name;

  String _javaName;
  
  String _testModelType;

  EnumSet<Flags> _flags = EnumSet.noneOf(Flags.class);
  
  RelationDescription _relation;

  Table _table;

  public Column(Table table, String name, String javaName, String type, String javaType, RelationDescription relation,
      Flags[] flags)
  {
    if (isIdentifier() && table.getIdentifierColumn() != null) {
      throw new IllegalArgumentException("Cannot set multiple identifier columns in a single table");
    }
    
    _table = table;
    _name = name;
    _javaName = javaName;
    _type = type;
    _javaType = javaType;
    _testModelType = javaType;
    _relation = relation;
    if (flags != null)
    {
      for (Flags flag : flags)
      {
        _flags.add(flag);
      }
    }
    if (_relation != null)
    {
      for (Column ref : _relation.getReferences())
      {
        _testModelType = ref.getTable().getJavaName() + "Ref";
      }
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

  public String getTestModelType()
  {
    return _testModelType;
  }

  public String getType()
  {
    return _type;
  }

  public String getName()
  {
    return _name;
  }

  public String getNameWithoutId()
  {
    if (_name.endsWith("_id")) {
      return _name.substring(0, _name.length() - 3);
    } else {
      return _name;
    }
  }
  
  public boolean isIdTruncable()
  {
    if (_relation == null || !_name.endsWith("_id")) {
      return false;
    }
    
    final String shortName = getNameWithoutId();
    for (Column column : _table.getColumns()) {
      if (shortName.equals(column.getName())) {
        System.out.println(shortName + " matches: " + column.getName());
        return false;
      }
    }
    
    return true;
  }
  
  public EnumSet<Flags> getFlags()
  {
    return _flags;
  }
  
  public RelationDescription getRelation()
  {
    return _relation;
  }

  public List<Column> getReferences()
  {
    if (_relation == null) {
      return new LinkedList<Column>();
    }
    return _relation.getReferences();
  }

  public boolean isAutoIncrement()
  {
    return _flags.contains(Flags.AutoIncrement);
  }

  public boolean isIdGenerationAutoInvokeOnInsert()
  {
    return _flags.contains(Flags.AutoInvokeNextIdMethod);
  }

  public boolean isIdGeneration()
  {
    return isAutoIncrement() || isIdGenerationAutoInvokeOnInsert() || _flags.contains(Flags.AddNextIdMethod);
  }

  public boolean isIdentifier()
  {
    return _flags.contains(Flags.IdentifierColumn);
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

  public String getJavaVariableName()
  {
    if (_javaName != null)
    {
      return CamelCase.makeFirstUpperCase(_javaName);
    }
    String name = CamelCase.makeFirstOfBlockUppercase(_name);
    return CamelCase.makeFirstLowerCase(name); // old
  }

  public String getJavaNameFirstLower()
  {
    return CamelCase.makeFirstLowerCase(getJavaName());
  }
  
  public Table getReferencedTable() 
  {
    if (_relation == null) {
      throw new IllegalArgumentException("No references...");
    }
    
    return _relation.getReferences().get(0).getTable();    
  }
  
  public boolean isReferencingTable(Table table)
  {
    if (_relation == null) {
      return false;
    }
    for (Column column : table.getColumns()) {
      if (_relation.getReferences().contains(column)) {
        return true;
      }
    }
    
    return false;
  }

}
