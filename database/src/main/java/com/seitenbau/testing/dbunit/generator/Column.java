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
  
  String _testModelType;

  EnumSet<Flags> _flags = EnumSet.noneOf(Flags.class);

  List<Column> _references = new ArrayList<Column>();

  Table _table;

  public Column(Table table, String name, String javaName, String type, String javaType, Column[] references,
      Flags[] flags)
  {
    if (isReference() && table.getReferenceColumn() != null) {
      throw new IllegalArgumentException("Cannot set multiple reference columns in a single table");
    }
    
    _table = table;
    _name = name;
    _javaName = javaName;
    _type = type;
    _javaType = javaType;
    _testModelType = javaType;
    if (flags != null)
    {
      for (Flags flag : flags)
      {
        _flags.add(flag);
      }
    }
    if (references != null)
    {
      for (Column ref : references)
      {
        _references.add(ref);
        _testModelType = ref.getTable().getJavaName() + "Id";
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
    if (_references.size() == 0 || !_name.endsWith("_id")) {
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

  public List<Column> getReferences()
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

  public boolean isReference()
  {
    return _flags.contains(Flags.ReferenceColumn);
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

}
