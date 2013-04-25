package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.List;

import com.seitenbau.testing.util.CamelCase;

public class DataSet
{
  public static final String NAME_SUFFIX = "DataSet";

  List<Table> _tables = new ArrayList<Table>();

  String _name;

  String _package;

  String _createException;

  String _caller;

  public String getPackage()
  {
    return _package;
  }

  public DataSet()
  {
  }

  public DataSet(String thePackage, String name)
  {
    _package = thePackage;
    _name = name;
  }

  public String getName()
  {
    return _name;
  }

  public List<Table> getTables()
  {
    return _tables;
  }

  public void addTable(Table table)
  {
    table.setParent(this);
    _tables.add(table);
  }


  public static String makeNiceJavaName(String name)
  {
    if (name == null)
    {
      return null;
    }
    String[] items = CamelCase.explode(name).split(" ");
    String temp = CamelCase.implode(items);
    return CamelCase.makeFirstUpperCase(temp);
  }

  public void letCreateThrow(String exception)
  {
    _createException = exception;
  }

  public String getCreateException()
  {
    if(_createException == null) 
    {
      // Autodetect newer DBUnit Versions
      try
      {
        Thread.currentThread().getContextClassLoader().loadClass("org.dbunit.database.AmbiguousTableNameException");
        return "org.dbunit.database.AmbiguousTableNameException";
      }
      catch (ClassNotFoundException e)
      {
        return null;
      }
    }
    return _createException;
  }

  public String getSuffix() {
      return NAME_SUFFIX;
  }

  public void setCaller(String caller)
  {
    _caller = caller;
  }

  public String getCaller()
  {
    return _caller;
  }

}
