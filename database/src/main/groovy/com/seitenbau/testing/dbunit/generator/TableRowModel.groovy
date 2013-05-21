package com.seitenbau.testing.dbunit.generator

import groovy.transform.ToString

@ToString
class TableRowModel
{
  List<Object> values = []

  public TableRowModel or(arg) {
    println "TableRowModel or(" + arg + ")"
    values.add(arg);
    return this;
  }
  
  public int getColumnCount() {
    return values.size();
  }
  
  public boolean isHeadRow() {
    if (values.size() == 0) {
      return false;
    }
    
    for (Object o : values) {
      if (!(o instanceof ColumnBinding)) {
        return false;
      }
    }
    
    return true;
  }
  
  public int getRefColumn() {
    int result = -1;
    int index = 0;
    values.each { ColumnBinding column ->
      if (column.isRefColumn()) result = index
      index++
    }
    return result;
  }
  
  public int getIdColumn() {
    int result = -1;
    int index = 0;
    values.each { ColumnBinding column ->
      if (column.isIdColumn()) result = index
      index++
    }
    return result;
  }

}
