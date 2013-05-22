package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.seitenbau.testing.util.CamelCase;

public class Column
{

  String _type;

  String _javaType;

  String _name;

  String _javaName;

  EnumSet<Flags> _flags = EnumSet.noneOf(Flags.class);

  List<Reference> _references = new ArrayList<Reference>();

  Table _table;

  public Column(Table table, String name, String javaName, String type, String javaType, Reference[] references,
      Flags[] flags)
  {
    _table = table;
    _name = name;
    _javaName = javaName;
    _type = type;
    _javaType = javaType;
    if (flags != null)
    {
      for (Flags flag : flags)
      {
        _flags.add(flag);
      }
    }
    if (references != null)
    {
      for (Reference ref : references)
      {
        _references.add(ref);
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

  public String getType()
  {
    return _type;
  }

  public String getName()
  {
    return _name;
  }

  public EnumSet<Flags> getFlags()
  {
    return _flags;
  }

  public List<Reference> getReferences()
  {
    return _references;
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

  public boolean isIdentifier()
  {
    return _flags.contains(Flags.IdentifierColumn);
  }

  public Reference getReference() 
  {
    if (_references.size() == 0) {
      throw new IllegalStateException("No references...");
    }
    
    return _references.get(0);
  }
  
  public boolean isReferencingTable(Table table)
  {
    for (Reference reference : _references) {
      for (Column column : table.getColumns()) {
        if (reference.getColumn() == column) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  public boolean isIdTruncable()
  {
    if (_references.size() == 0 || !_name.endsWith("_id")) {
      return false;
    }
    
    final String shortName = getNameWithoutId();
    for (Column column : _table.getColumns()) {
      if (shortName.equals(column.getName())) {
        return false;
      }
    }
    
    return true;
  }
  
  public String getNameWithoutId()
  {
    if (_name.endsWith("_id")) {
      return _name.substring(0, _name.length() - 3);
    } else {
      return _name;
    }
  }  
}
