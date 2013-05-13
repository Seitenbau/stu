package com.seitenbau.testing.dbunit.generator;

import java.util.LinkedList;
import java.util.List;

public class RelationDescription
{
  Column _owner;
  
  List<Column> _references;
  
  String _ownerRelationName;
  
  String _referencesRelationName;
  
  RelationDescription(String ownerRelationName, Column[] references, String referencesRelationName)
  {
    _ownerRelationName = ownerRelationName;
    _references = new LinkedList<Column>();
    _referencesRelationName = referencesRelationName;

    if (references != null) {
      for (Column col : references) {
        _references.add(col);
      }
    }
  }
  
  public Table getOwnerTable()
  {
    return _owner.getTable();
  }

  public String getOwnerRelationName()
  {
    return _ownerRelationName;
  }

  public List<Column> getReferences()
  {
    return _references;
  }

  public String getReferencesRelationName()
  {
    return _referencesRelationName;
  }
  
  public static RelationDescription oneToMany(String name, Table otherTable, String otherName) {
    return new RelationDescription(name, new Column[] { otherTable.getIdentifierColumn() } , otherName);
  }
  
  public static RelationDescription oneToOne(String name, Table otherTable, String otherName) {
    return new RelationDescription(name, new Column[] { otherTable.getIdentifierColumn() } , otherName);
  }
  
  public static RelationDescription toOne(Table otherTable, String otherName) {
    return new RelationDescription(null, new Column[] { otherTable.getIdentifierColumn() } , otherName);
  }
}
