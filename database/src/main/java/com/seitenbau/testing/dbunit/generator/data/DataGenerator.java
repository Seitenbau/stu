package com.seitenbau.testing.dbunit.generator.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.seitenbau.testing.dbunit.generator.Column;
import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.Edge;
import com.seitenbau.testing.dbunit.generator.EntityBlueprint;
import com.seitenbau.testing.dbunit.generator.EntityCreationMode;
import com.seitenbau.testing.dbunit.generator.EntityFactory;
import com.seitenbau.testing.dbunit.generator.EntityFactory.EntityFactoryResult;
import com.seitenbau.testing.dbunit.generator.Table;

public class DataGenerator
{

  private final EntityFactory fab;
  private final DatabaseModel model;

  private final Set<Edge> visitedEdges;
  private final Map<Table, TableGenerationData> tableGenerationData;

  public DataGenerator(EntityFactory fab, DatabaseModel model)
  {
    this.fab = fab;
    this.model = model;
    this.visitedEdges = new HashSet<Edge>();
    this.tableGenerationData = new HashMap<Table, TableGenerationData>();
  }

  public void generate()
  {
    Table start = getStartTable(model, "pruefung");
    System.out.println("Start Table: " + start.getJavaName());
    visitTable(start);

    //fab.printStats();

    //System.out.println("----------------------------");

    int count = 0;
    while (count < 2000 && !validateBlueprints(model)) {
      count++;
    }

    fab.printStats();
    System.out.println("Done\t" + count);
  }

  private void visitTable(Table table)
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

        new EdgeHandler(edge, fab).handle();

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

        new EdgeHandler(edge, fab).handle();

        if (!tableGenerationData.containsKey(next)) {
          visitTable(next);
        }
      }
    }
  }

  private Table getStartTable(DatabaseModel model, String name)
  {
    for (Table table : model.getTables()) {
      if (table.getJavaName().equalsIgnoreCase(name)) {
        return table;
      }
    }

    return null;
  }

  private Table getStartTable(DatabaseModel model)
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

  private boolean validateBlueprints(DatabaseModel model)
  {
    for (Table table : model.getTables()) {
      if (!table.getOutgoingEdges().isEmpty()) {
        List<EntityBlueprint> bps = new LinkedList<EntityBlueprint>(fab.getTableBlueprints(table));
        for (EntityBlueprint bp : bps) {
          if (!validateBlueprintOutgoing(bp, table)) {
            return false;
          }
        }
      }
      if (!table.getIncomingEdges().isEmpty()) {
        List<EntityBlueprint> bps = new LinkedList<EntityBlueprint>(fab.getTableBlueprints(table));
        for (EntityBlueprint bp : bps) {
          if (!validateBlueprintIncoming(bp, table)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  private boolean validateBlueprintOutgoing(EntityBlueprint bp, Table table)
  {
    for (Edge edge : table.getOutgoingEdges()) {
      // skip optional relations
      if (edge.getDestination().getMin() == 0) {
        continue;
      }

      Column col = edge.getColumn();
      if (bp.getValue(col.getJavaName()) == null) {
        EntityFactoryResult result = fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.minMax(edge.getSource().getMin(), edge.getSource().getMax()));
        bp.setValue(col.getJavaName(),  result.getEntity());
        System.out.println("Incomplete: " + bp + " (fixed) " + edge.getSource().getMin() + " - " + edge.getSource().getMax());
        return false;
      }
    }

    return true;
  }

  private boolean validateBlueprintIncoming(EntityBlueprint bp, Table table)
  {
    // source: professor
    // destination: raum2
    for (Edge edge : table.getIncomingEdges()) {
      int min = edge.getSource().getMin();

      if (min == 0) {
        continue;
      }

      int count = fab.getReferencingCount(bp, edge.getSource().getTable(), edge.getColumn().getJavaName());
      if (count >= min) {
        continue;
      }

      for (int i = count; i < min; i++) {
        System.out.println("Incomplete: " + bp + " [" + edge.getSource().getTable().getJavaName() + "]: " + i +  " < " + edge.getSource().getMin());
        EntityFactoryResult result = fab.getEntity(edge.getSource().getTable(), edge, EntityCreationMode.minMax(edge.getDestination().getMin(), edge.getDestination().getMax()));
        result.getEntity().setValue(edge.getColumn().getJavaName(), bp);
        System.out.println("  -> " + result.getEntity() + " | " + edge.getDestination().getMin() +"  - " + edge.getDestination().getMax());
      }

      return false;
    }
    return true;
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
    EntityFactory fab;
    Set<String> generated = new HashSet<String>();

    EdgeHandler(Edge edge, EntityFactory fab) {
      this.edge = edge;
      this.fab = fab;
    }

    void handle()
    {
      System.out.println("Handling: " + edge.getSource().getTable().getName() + " ---> " + edge.getDestination().getTable().getName());

      // Destination is always m..1
      generate(edge.getDestination().getMin(), edge.getSource().getMin());
      generate(edge.getDestination().getMin(), edge.getSource().getMax());
      generate(edge.getDestination().getMax(), edge.getSource().getMin());
      generate(edge.getDestination().getMax(), edge.getSource().getMax());

      if (edge.getDestination().getMin() > 0) {
        System.out.println("*** CHECK IF " + edge.getSource().getTable().getName() + " ENTITIES HAVE TO BE LINKED...");
        for (EntityBlueprint bp : fab.getUnrelatedBlueprints(edge.getSource().getTable(), edge)) {
          EntityFactoryResult result = fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.minMax(edge.getDestination().getMin(), edge.getDestination().getMax()));
          if (result.isAlreadyExisted()) {
            //continue;
          }

          bp.setValue(edge.getColumn().getJavaName(), result.getEntity());
          System.out.println("  Linked " + bp);
        }
      }

      // check if existing entities have valid relations
      // check if the existing entities need a relation
      // iterate over these entities
      // get a relation entity from the fab to link it
      // check if the new relation entity needs further relations ... :-(

      System.out.println();
    }

    void generate(int destBorder, int sourceBorder)
    {
      String id = destBorder + ":" + sourceBorder;
      if (generated.contains(id)) {
        return;
      }

      generated.add(id);
      System.out.println("  Create: " + id);

      if (destBorder == 0) {
        EntityFactoryResult result = fab.getEntity(edge.getSource().getTable(), edge, EntityCreationMode.noRelation());
        System.out.println("  Link " + result.getEntity() + "[1]");
      } else if (sourceBorder == 0) {
        fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.noRelation());
      } else {
        EntityFactoryResult entity = fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.fixed(sourceBorder));
        for (int i = 0; i < sourceBorder; i++) {
          EntityFactoryResult result = fab.getEntity(edge.getSource().getTable(), edge, EntityCreationMode.fixed(1));
          result.getEntity().setValue(edge.getColumn().getJavaName(), entity.getEntity());
          System.out.println("  Linked " + result.getEntity());
        }
      }

      if (destBorder == 0) {
        generate(1, sourceBorder);
      }
      if (sourceBorder == 0) {
        generate(destBorder, 1);
      }
    }

  }

}
