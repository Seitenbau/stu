package com.seitenbau.stu.database.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.seitenbau.stu.util.CamelCase;

public class Table
{

  public static final String NAME_SUFFIX = "Table";

  private DataSet _dataSet;

  private final String _name;

  private final String _javaName;

  private final String _description;

  private final long _seed;

  private final Integer _infinite;

  private final int _minEntities;

  private final List<Column> _columns;

  public Table(String name, String javaName, String description, long seed, Integer infinite,
      int minEntities, List<ColumnBuilder> columnBuilders)
  {
    _name = name;
    _javaName = javaName;
    _description = description;
    _seed = seed;
    _columns = new ArrayList<Column>();
    _infinite = infinite;
    _minEntities = minEntities;

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

  public long getSeed()
  {
    return _seed;
  }

  public int getMinEntities()
  {
    return _minEntities;
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

  public Integer getInfinite()
  {
    return _infinite;
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
    private Column localColumn;
    private String localName;
    private Column localAssociationColumn;

    private Column foreignColumn;
    private String foreignName;
    private Column foreignAssociationColumn;

    private String description;

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

  public int getIncomingEdgeCount()
  {
    int result = 0;

    for (Column col : _columns)
    {
      result += col.getReferencedByList().size();
    }

    return result;
  }

  public List<Edge> getOutgoingEdges()
  {
    List<Edge> result = new LinkedList<Edge>();

    for (Column col : _columns)
    {
      if (col.getRelation() == null)
      {
        continue;
      }

      result.add(new Edge(col));
    }

    return result;
  }

  public List<Edge> getIncomingEdges()
  {
    List<Edge> result = new LinkedList<Edge>();

    for (Column col : _columns)
    {
      for (Column ref : col.getReferencedByList())
      {
        result.add(new Edge(ref));
      }
    }

    return result;
  }
}
