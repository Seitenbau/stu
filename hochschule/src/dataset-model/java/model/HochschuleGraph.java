package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.seitenbau.testing.dbunit.generator.Edge;
import com.seitenbau.testing.dbunit.generator.Table;

public class HochschuleGraph
{

  static final Set<Edge> visitedEdges = new HashSet<Edge>();
  static final Map<Table, TableGenerationData> tableGenerationData =
      new HashMap<Table, TableGenerationData>();

  public static void main(String[] args) throws Exception
  {
    HochschuleModel model = new HochschuleModel();
    for (Table table : model.getTables()) {
      //System.out.println(table.getName() + ": " + table.getIncomingEdgeCount());
    }

    visitTable(getStartTable(model));
    System.out.println("Done");
  }

  static void visitTable(Table table)
  {
    //System.out.println("Visiting: " + table.getName());
    TableGenerationData data = new TableGenerationData(table);
    tableGenerationData.put(table, data);

    for (Edge edge : table.getOutgoingEdges())
    {
      if (!visitedEdges.contains(edge)) {
        visitedEdges.add(edge);

        Table next = edge.getDestination().getTable();
        //System.out.println(table.getName() + " ---> " + next.getName());

        new EdgeHandler(edge).handle();

        if (!tableGenerationData.containsKey(next)) {
          visitTable(next);
        }
      }
    }

    for (Edge edge : table.getIncomingEdges())
    {
      if (!visitedEdges.contains(edge)) {
        visitedEdges.add(edge);

        Table next = edge.getSource().getTable();
        //System.out.println(table.getName() + " <--- " + next.getName());

        new EdgeHandler(edge).handle();

        if (!tableGenerationData.containsKey(next)) {
          visitTable(next);
        }
      }
    }
  }

  static Table getStartTable(HochschuleModel model)
  {
    Table result = null;
    int value = Integer.MAX_VALUE;
    for (Table table : model.getTables()) {
      int count = table.getIncomingEdgeCount();
      if (count == 0) {
        return table;
      }

      if (count < value) {
        value = count;
        result = table;
      }
    }
    return result;
  }

  static class TableGenerationData {
    final Table table;

    int rowsToGenerate;

    TableGenerationData(Table table) {
      this.table = table;
    }
  }

  static class EdgeHandler
  {

    Edge edge;
    Set<String> generated = new HashSet<String>();

    EdgeHandler(Edge edge) {
      this.edge = edge;
    }

    void handle()
    {
      System.out.println("Handling: " + edge.getSource().getTable().getName() + " ---> " + edge.getDestination().getTable().getName());

      // Destination is always m..1
      generate(edge.getDestination().getMin(), edge.getSource().getMin());
      generate(edge.getDestination().getMin(), edge.getSource().getMax());
      generate(edge.getDestination().getMax(), edge.getSource().getMin());
      generate(edge.getDestination().getMax(), edge.getSource().getMax());
    }

    void generate(int destBorder, int sourceBorder)
    {
      String id = destBorder + ":" + sourceBorder;
      if (generated.contains(id)) {
        return;
      }

      generated.add(id);
      System.out.println("  Create: " + id);

      // if destBorder == 1 it is == max
      //if (destBorder == 0) {
        //generate(1, sourceBorder);
      //}
      if (sourceBorder == 0) {
        generate(destBorder, 1);
      }
    }

  }

}
