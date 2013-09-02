package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.dbunit.generator.data.MultiplicityBorder;
import com.seitenbau.testing.dbunit.generator.data.MultiplicityParser;

public class Edge
{

  private final Node source;

  private final Node destination;

  private final int hashCode;

  private final Column column;

  public Edge(Column column)
  {
    this.column = column;
    source = new Node(column.getTable(), column.getRelation().getForeignMultiplicity());
    destination = new Node(column.getRelation().getForeignColumn().getTable(), column.getRelation().getLocalMultiplicity());
    hashCode = source.table.hashCode() * 17 + destination.table.hashCode();
  }

  public Node getSource()
  {
    return source;
  }

  public Node getDestination()
  {
    return destination;
  }

  public Column getColumn()
  {
    return column;
  }

  @Override
  public int hashCode()
  {
    return hashCode;
  }

  @Override
  public boolean equals(Object o)
  {
    if (!(o instanceof Edge)) {
      return false;
    }

    Edge e = (Edge)o;
    return source.table == e.source.table && destination.table == e.destination.table;
  }

  @Override
  public String toString()
  {
    return "[" + source.table.getName() + "--->" + destination.table.getName() + "]";
  }

  public static class Node
  {
    private final Table table;

    private final MultiplicityBorder min;

    private final MultiplicityBorder max;

    Node(Table table, String multiplicity)
    {
      this.table = table;
      MultiplicityParser p = new MultiplicityParser(multiplicity);
      min = p.getMin();
      max = p.getMax();
    }

    public Table getTable() {
      return table;
    }

    public MultiplicityBorder getMin()
    {
      return min;
    }

    public MultiplicityBorder getMax()
    {
      return max;
    }

  }

}
