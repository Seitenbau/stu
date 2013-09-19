package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.dbunit.generator.data.Multiplicity;

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

    // use column to identify Edge
    hashCode = column.hashCode();
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
    return column == e.column;
  }

  @Override
  public String toString()
  {
    return "[" + source.table.getName() + "--->" + destination.table.getName() + "]";
  }

  public static class Node
  {
    private final Table table;

    private final Multiplicity multiplicity;

    Node(Table table, String multiplicity)
    {
      this.table = table;
      this.multiplicity = Multiplicity.parse(multiplicity);
    }

    public Table getTable() {
      return table;
    }

    public Multiplicity.Border getMin()
    {
      return multiplicity.getMin();
    }

    public Multiplicity.Border getMax()
    {
      return multiplicity.getMax();
    }

  }

}
