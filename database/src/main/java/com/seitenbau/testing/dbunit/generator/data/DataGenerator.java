package com.seitenbau.testing.dbunit.generator.data;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.seitenbau.testing.dbunit.generator.AssociativeTable;
import com.seitenbau.testing.dbunit.generator.Column;
import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.Edge;
import com.seitenbau.testing.dbunit.generator.Table;
import com.seitenbau.testing.dbunit.generator.data.EntityFactory.EntityFactoryResult;

public class DataGenerator
{

  private final EntityFactory fab;
  private final DatabaseModel model;

  private final Set<Edge> visitedEdges;
  private final Set<Table> visitedTables;

  public DataGenerator(EntityFactory fab, DatabaseModel model)
  {
    this.fab = fab;
    this.model = model;
    this.visitedEdges = new HashSet<Edge>();
    this.visitedTables = new HashSet<Table>();
  }

  public void generate()
  {
    Table start = getStartTable(model, "beaufsichtigt");
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

    STUTableOutput output = new STUTableOutput();
    System.out.println(output.create(fab, model));
  }

  private void visitTable(Table table)
  {
    if (visitedTables.contains(table)) {
      // table has been visited already
      return;
    }
    visitedTables.add(table);

    if (table.isAssociativeTable()) {
      handleAssociativeTable((AssociativeTable)table);
    } else {
      handleTable(table);
    }
  }

  private void handleTable(Table table)
  {
    System.out.println("Handle Table " + table.getJavaName());

    for (Edge edge : table.getOutgoingEdges())
    {
      handleEdge(edge, edge.getDestination().getTable());
    }

    for (Edge edge : table.getIncomingEdges())
    {
      handleEdge(edge, edge.getSource().getTable());
    }
  }

  // TODO improve
  private void handleAssociativeTable(AssociativeTable table)
  {
    System.out.println("Handle Associative Table " + table.getJavaName());

    Column leftColumn = table.getAssociativeColumns().get(0);
    Column rightColumn = table.getAssociativeColumns().get(1);

    MultiplicityParser leftMultiplicity = new MultiplicityParser(leftColumn.getRelation().getForeignMultiplicity());
    MultiplicityParser rightMultiplicity = new MultiplicityParser(rightColumn.getRelation().getForeignMultiplicity());

    //System.out.println(leftColumn.getJavaName() + " <---> " + rightColumn.getJavaName());

    generateAssociative(leftColumn, leftMultiplicity.getMin(), rightColumn, rightMultiplicity.getMin());
    generateAssociative(leftColumn, leftMultiplicity.getMax(), rightColumn, rightMultiplicity.getMin());
    generateAssociative(leftColumn, leftMultiplicity.getMin(), rightColumn, rightMultiplicity.getMax());
    generateAssociative(leftColumn, leftMultiplicity.getMax(), rightColumn, rightMultiplicity.getMax());

    // visit the associated tables :-)
    visitTable(leftColumn.getRelation().getForeignColumn().getTable());
    visitTable(rightColumn.getRelation().getForeignColumn().getTable());
  }

  private void generateAssociative(Column leftColumn, MultiplicityBorder leftBorder, Column rightColumn,
      MultiplicityBorder rightBorder)
  {
    Table leftTable = leftColumn.getRelation().getForeignColumn().getTable();
    Table rightTable = rightColumn.getRelation().getForeignColumn().getTable();

    Edge leftEdge = new Edge(leftColumn);
    Edge rightEdge = new Edge(rightColumn);
    visitedEdges.add(leftEdge);
    visitedEdges.add(rightEdge);

    // swap border counts :-)
    int leftCount = getCount(rightBorder);
    int rightCount = getCount(leftBorder);
    //System.out.println(leftColumn.getJavaName() + " <---> " + rightColumn.getJavaName() + ": " + leftCount + ":" + rightCount);

    EntityBlueprint[] leftBps = new EntityBlueprint[leftCount];
    EntityBlueprint[] rightBps = new EntityBlueprint[rightCount];

    for (int l = 0; l < leftCount; l++) {
      leftBps[l] = fab.getEntity(leftTable, leftEdge, EntityCreationMode.fixed(1)).getEntity();
    }
    for (int r = 0; r < rightCount; r++) {
      rightBps[r] = fab.getEntity(rightTable, rightEdge, EntityCreationMode.fixed(1)).getEntity();
    }

    for (int l = 0; l < leftCount; l++) {
      for (int r = 0; r < rightCount; r++) {
        EntityFactoryResult result = fab.getEntity(leftColumn.getTable(),  leftEdge,  EntityCreationMode.fixed(1));
        if (result.isAlreadyExisted()) {
          //System.out.println("EXISTED");
        }

        EntityBlueprint entity = result.getEntity();
        entity.setReference(leftEdge, leftBps[l]);
        entity.setReference(rightEdge, rightBps[r]);

        //System.out.println("  " + entity);
      }
    }
  }

  private int getCount(MultiplicityBorder border)
  {
    if (border.isInfinite()) {
      return 2;
    }

    if (border.getValue() == 0) {
      return 1;
    }

    return border.getValue();
  }

  private void handleEdge(Edge edge, Table next)
  {
    if (visitedEdges.contains(edge)) {
      return;
    }

    if (next.isAssociativeTable()) {
      handleAssociativeTable((AssociativeTable)next);
      return;
    }

    visitedEdges.add(edge);
    new EdgeHandler(edge, fab).handle();
    visitTable(next);
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
      if (edge.getDestination().getMin().getValue() == 0) {
        continue;
      }

      Column col = edge.getColumn();
      if (bp.getValue(col.getJavaName()) == null) {
        EntityFactoryResult result = fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.minMax(edge.getSource().getMin(), edge.getSource().getMax()));
        bp.setReference(edge, result.getEntity());
        result.getEntity().appendLog("used for validation");
        //System.out.println("Incomplete: " + bp + " (fixed) " + edge.getSource().getMin() + " - " + edge.getSource().getMax());
        return false;
      }
    }

    return true;
  }

  private boolean validateBlueprintIncoming(EntityBlueprint bp, Table table)
  {
    if (table.isAssociativeTable()) {
      return true;
    }

    // source: professor
    // destination: raum2
    for (Edge edge : table.getIncomingEdges()) {
      if (edge.getSource().getTable().isAssociativeTable()) {
        return true;
      }

      int min = edge.getSource().getMin().getValue();

      if (min == 0) {
        continue;
      }

      List<EntityBlueprint> referencedByList = bp.getReferencedByList(edge);
      int count = referencedByList.size();
      if (count >= min) {
        continue;
      }

      for (int i = count; i < min; i++) {
        //System.out.println(" Creating " + i + "/" + min + ": " + bp);
        //System.out.println("Incomplete: " + bp + " [" + edge.getSource().getTable().getJavaName() + "]: " + i +  " < " + edge.getSource().getMin());
        EntityFactoryResult result = fab.getEntity(edge.getSource().getTable(), edge, EntityCreationMode.minMax(edge.getDestination().getMin(), edge.getDestination().getMax()));
        result.getEntity().setReference(edge, bp);
        //System.out.println("  -> " + result.getEntity() + " | " + edge.getDestination().getMin() +"  - " + edge.getDestination().getMax());
      }

      return false;
    }
    return true;
  }

  static class TableGenerationData {
    final Table table;

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
      //System.out.println("Handling: " + edge.getSource().getTable().getName() + " ---> " + edge.getDestination().getTable().getName());

      // Destination is always m..1
      generate(edge.getDestination().getMin(), edge.getSource().getMin());
      generate(edge.getDestination().getMin(), edge.getSource().getMax());
      generate(edge.getDestination().getMax(), edge.getSource().getMin());
      generate(edge.getDestination().getMax(), edge.getSource().getMax());

      if (edge.getDestination().getMin().getValue() > 0) {
        //System.out.println("*** CHECK IF " + edge.getSource().getTable().getName() + " ENTITIES HAVE TO BE LINKED...");
        for (EntityBlueprint bp : fab.getUnrelatedBlueprints(edge.getSource().getTable(), edge)) {
          EntityFactoryResult result = fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.minMax(edge.getDestination().getMin(), edge.getDestination().getMax()));
          if (result.isAlreadyExisted()) {
            //continue;
          }

          bp.setReference(edge, result.getEntity());
          //System.out.println("  Linked " + bp);
        }
      }

      // check if existing entities have valid relations
      // check if the existing entities need a relation
      // iterate over these entities
      // get a relation entity from the fab to link it
      // check if the new relation entity needs further relations ... :-(

      //System.out.println();
    }

    void generate(MultiplicityBorder destBorder, MultiplicityBorder sourceBorder)
    {
      int dbValue = destBorder.getValue();
      int sbValue = sourceBorder.getValue();
      if (destBorder.isInfinite()) {
        dbValue = 5;
      }
      if (sourceBorder.isInfinite()) {
        sbValue = 5;
      }
      generate(dbValue, sbValue);
    }

    void generate(int destBorder, int sourceBorder)
    {
      String id = destBorder + ":" + sourceBorder;
      if (generated.contains(id)) {
        return;
      }

      generated.add(id);
      //System.out.println("  Create: " + id);

      if (destBorder == 0) {
        EntityFactoryResult result = fab.getEntity(edge.getSource().getTable(), edge, EntityCreationMode.noRelation());
        //System.out.println("  Link " + result.getEntity() + "[1]");
      } else if (sourceBorder == 0) {
        fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.noRelation());
      } else {
        EntityFactoryResult entity = fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.fixed(sourceBorder));
        for (int i = 0; i < sourceBorder; i++) {
          EntityFactoryResult result = fab.getEntity(edge.getSource().getTable(), edge, EntityCreationMode.fixed(1));
          result.getEntity().setReference(edge, entity.getEntity());
          //System.out.println("  Linked " + result.getEntity());
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
