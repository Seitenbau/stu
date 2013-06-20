package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.seitenbau.testing.util.CamelCase;

public class Table
{

  public static final String NAME_SUFFIX = "Table";

  private DataSet _dataSet;

  private final String _name;

  private final String _javaName;

  private final String _description;

  private final List<Column> _columns;

  public Table(String name, String javaName, String description, List<ColumnBuilder> columnBuilders)
  {
    _name = name;
    _javaName = javaName;
    _description = description;
    _columns = new ArrayList<Column>();
    for (ColumnBuilder columnBuilder : columnBuilders) {
      _columns.add(columnBuilder.buildColumn(this));
    }
  }

  public String getName()
  {
    return _name;
  }

  public String getDescription()
  {
    return _description;
  }

  public List<Column> getColumns()
  {
    return Collections.unmodifiableList(_columns);
  }

  public String getJavaName()
  {
    return _javaName;
  }

  public String getJavaNameFirstLower()
  {
    return CamelCase.makeFirstLowerCase(getJavaName());
  }

  public String getPackage()
  {
    return getDataSet().getPackage();
  }

  public String getSuffix()
  {
    return NAME_SUFFIX;
  }

  void setParent(DataSet dataSet)
  {
    _dataSet = dataSet;
  }

  public DataSet getDataSet()
  {
    return _dataSet;
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

  public List<Column> getUniqueColumns()
  {
    List<Column> result = new LinkedList<Column>();
    for (Column col : getColumns())
    {
      if (col.isUnique())
      {
        result.add(col);
      }
    }
    return result;
  }

  public boolean isAssociativeTable()
  {
    int foreignKeys = 0;
    if (getColumns().size() < 2)
    {
      return false;
    }
    for (Column col : getColumns())
    {
      if (col.getRelation() != null)
      {
        foreignKeys++;
      }
    }

    return (foreignKeys == 2);
  }


  public Column getIdentifierColumn()
  {
    for (Column col : _columns)
    {
      if (col.isIdentifier())
      {
        return col;
      }
    }
    throw new RuntimeException("No identifier column found");
  }

  /**
   * Helper method for the Generator. Determines the requires imports for the table.
   * @return Set of Table, which have to be imported
   */
  public Set<Table> getRequiredTableImports()
  {
    Set<Table> result = new HashSet<Table>();
    for (Column col : getColumns())
    {
      if (col.getRelation() != null)
      {
        result.add(col.getRelation().getTable());
      }
    }
    return result;
  }

  public List<RelationTemplateHelper> getManyToOneRelations()
  {
    List<RelationTemplateHelper> result = new LinkedList<RelationTemplateHelper>();
    if (isAssociativeTable()) {
      return result;
    }
    for (Column col : _columns)
    {
      if (col.getRelation() == null)
      {
        continue;
      }

      Relation rel = col.getRelation();
      RelationTemplateHelper relation = new RelationTemplateHelper();
      relation.localColumn = col;
      relation.otherColumn = rel.getColumn();
      relation.localName = rel.getLocalName();
      relation.otherName = rel.getRemoteName();
      relation.description = rel.getLocalDescription();
      if (relation.description.isEmpty()) {
        relation.description = rel.getRemoteDescription();
      }

      result.add(relation);
    }
    return result;
  }

  public List<RelationTemplateHelper> getOneToManyRelations()
  {
    List<RelationTemplateHelper> result = new LinkedList<RelationTemplateHelper>();
    if (isAssociativeTable()) {
      return result;
    }
    for (Column col : _columns)
    {
      for (Column referencingCol : col.getReferencedByList())
      {
        if (referencingCol.getTable().isAssociativeTable()) {
          continue;
        }

        Relation rel = referencingCol.getRelation();
        RelationTemplateHelper relation = new RelationTemplateHelper();
        relation.localColumn = col;
        relation.otherColumn = referencingCol;
        relation.localName = rel.getRemoteName();
        relation.otherName = rel.getLocalName();
        relation.description = rel.getRemoteDescription();

        result.add(relation);
      }
    }
    return result;
  }

  public List<RelationTemplateHelper> getAssociativeRelations()
  {
    List<RelationTemplateHelper> result = new LinkedList<RelationTemplateHelper>();
    if (isAssociativeTable()) {
      return result;
    }
    for (Column col : _columns)
    {
      for (Column referencingCol : col.getReferencedByList())
      {
        if (!referencingCol.getTable().isAssociativeTable()) {
          continue;
        }

        RelationTemplateHelper relation = new RelationTemplateHelper();

        relation.localColumn = col;
        relation.otherColumn = referencingCol.getTable().getAssociatedColumn(this);
        relation.localName = referencingCol.getRelation().getRemoteName();
        relation.otherName = relation.otherColumn.getRelation().getRemoteName();

        relation.localAssociationColumn = referencingCol;
        relation.otherAssociationColumn = relation.otherColumn.getRelation().getColumn();

        relation.description = referencingCol.getRelation().getRemoteDescription();

        result.add(relation);
      }
    }
    return result;
  }

  private Column getAssociatedColumn(Table table)
  {
    for (Column col : getColumns())
    {
      final Relation relation = col.getRelation();
      if (relation == null)
      {
        continue;
      }
      if (relation.getColumn().getTable() == table)
      {
        continue;
      }

      return col;
    }

    throw new RuntimeException("No associating column found");
  }

  public final class RelationTemplateHelper
  {
    Column localColumn;
    String localName;

    Column otherColumn;
    String otherName;

    Column localAssociationColumn;
    Column otherAssociationColumn;

    String description;

    public Column getLocalColumn()
    {
      return localColumn;
    }

    public String getLocalName()
    {
      return localName;
    }

    public Column getOtherColumn()
    {
      return otherColumn;
    }

    public String getOtherName()
    {
      return otherName;
    }

    public boolean isAssociative()
    {
      return (localAssociationColumn != null) && (otherAssociationColumn != null);
    }

    public Column getLocalAssociationColumn()
    {
      return localAssociationColumn;
    }

    public Column getOtherAssociationColumn()
    {
      return otherAssociationColumn;
    }

    public String getOtherRowbuilderClass()
    {
      return _dataSet.getNames().getRowBuilderClass(otherColumn.getTable());
    }

    public String getOtherRefClass()
    {
      if (isAssociative())
      {
        return _dataSet.getNames().getRefClass(otherAssociationColumn.getTable());
      } else {
        return _dataSet.getNames().getRefClass(otherColumn.getTable());
      }
    }

    public String getOtherRefVariable()
    {
      if (isAssociative())
      {
        return _dataSet.getNames().getRefVariable(otherAssociationColumn.getTable());
      } else {
        return _dataSet.getNames().getRefVariable(otherColumn.getTable());
      }
    }

    public String getDescription()
    {
      return description;
    }
  }
}
