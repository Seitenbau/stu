package com.seitenbau.testing.dbunit.generator;

public class Edge
{
  private static final int INFINITE = -1;

  private final Node source;

  private final Node destination;

  private final int hashCode;

  Edge(Column column)
  {
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
    final Table table;

    int min;

    int max;

    Node(Table table, String multiplicity)
    {
      this.table = table;
      MultiplicityParser p = new MultiplicityParser(multiplicity);
      min = p.min;
      max = p.max;

      if (max == INFINITE) {
        max = 5;
      }
    }

    public Table getTable() {
      return table;
    }

    public int getMin()
    {
      return min;
    }

    public int getMax()
    {
      return max;
    }

  }

  static class MultiplicityParser
  {
    int min;
    int max;

    MultiplicityParser(String multiplicity)
    {
      if (multiplicity.contains("..")) {
        String[] minmax = multiplicity.split("\\.\\.", 2);
        min = getValue(minmax[0]);
        max = getValue(minmax[1]);
      } else {
        min = getValue(multiplicity);
        max = min;
      }
    }

    int getValue(String value)
    {
      if ("*".equals(value)) {
        return INFINITE;
      }

      return Integer.parseInt(value);
    }

  }
}
