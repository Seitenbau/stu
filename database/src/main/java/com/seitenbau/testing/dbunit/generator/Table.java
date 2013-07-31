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

  public List<Column> getIdentifierColumns()
  {
    List<Column> result = new LinkedList<Column>();
    for (Column col : getColumns())
    {
      if (col.isIdentifier())
      {
        result.add(col);
      }
    }
    return result;
  }

  public boolean isAssociativeTable()
  {
    return false;
  }

  public boolean hasDataColumns()
  {
    for (Column col : getColumns())
    {
      if (col.getRelation() == null)
      {
        return true;
      }
    }
    return false;
  }

  public Column getDefaultIdentifierColumn()
  {
    for (Column col : _columns)
    {
      if (col.isDefaultIdentifier())
      {
        return col;
      }
    }
    throw new RuntimeException("No default identifier column found");
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
        result.add(col.getRelation().getForeignColumn().getTable());
      }
    }
    return result;
  }

  public List<Column> getForeignColumns()
  {
    List<Column> result = new LinkedList<Column>();
    if (isAssociativeTable()) {
      return result;
    }
    for (Column col : _columns)
    {
      if (col.getRelation() == null)
      {
        continue;
      }

      result.add(col);
    }
    return result;
  }

  public List<Column> getReferencingColumns()
  {
    List<Column> result = new LinkedList<Column>();
    if (isAssociativeTable()) {
      return result;
    }
    for (Column col : _columns)
    {
      for (Column foreignCol : col.getReferencedByList())
      {
        if (foreignCol.getTable().isAssociativeTable()) {
          continue;
        }

        result.add(foreignCol);
      }
    }
    return result;
  }

  public List<AssociativeRelation> getAssociativeRelations()
  {
    List<AssociativeRelation> result = new LinkedList<AssociativeRelation>();
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

        AssociativeRelation relation = new AssociativeRelation();

        relation.localColumn = col;
        relation.localName = referencingCol.getRelation().getForeignName();
        relation.localAssociationColumn = referencingCol;

        relation.foreignColumn = referencingCol.getTable().getAssociatedColumn(this);
        relation.foreignName = relation.foreignColumn.getRelation().getForeignName();
        relation.foreignAssociationColumn = relation.foreignColumn.getRelation().getForeignColumn();

        relation.description = referencingCol.getRelation().getForeignDescription();

        result.add(relation);
      }
    }
    return result;
  }

  private Column getAssociatedColumn(Table table)
  {
    for (Column col : getColumns())
    {
      final ColumnReference relation = col.getRelation();
      if (relation == null)
      {
        continue;
      }
      if (relation.getForeignColumn().getTable() == table)
      {
        continue;
      }

      return col;
    }

    throw new RuntimeException("No associating column found");
  }

  public final class AssociativeRelation
  {
    Column localColumn;
    String localName;
    Column localAssociationColumn;

    Column foreignColumn;
    String foreignName;
    Column foreignAssociationColumn;

    String description;

    public Column getLocalColumn()
    {
      return localColumn;
    }

    public String getLocalName()
    {
      return localName;
    }

    public Column getForeignColumn()
    {
      return foreignColumn;
    }

    public String getForeignName()
    {
      return foreignName;
    }

    public Column getLocalAssociationColumn()
    {
      return localAssociationColumn;
    }

    public Column getForeignAssociationColumn()
    {
      return foreignAssociationColumn;
    }

    public String getForeignRowbuilderClass()
    {
      return _dataSet.getNames().getRowBuilderClass(foreignColumn.getTable());
    }

    public String getForeignRefVariable()
    {
      return _dataSet.getNames().getRefVariable(foreignAssociationColumn.getTable());
    }

    public String getDescription()
    {
      return description;
    }
  }
}
