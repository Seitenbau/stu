package com.seitenbau.stu.database.generator.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.seitenbau.stu.database.generator.AssociativeTable;
import com.seitenbau.stu.database.generator.Column;
import com.seitenbau.stu.database.generator.DatabaseModel;
import com.seitenbau.stu.database.generator.Edge;
import com.seitenbau.stu.database.generator.Table;

public class DataGenerator
{

  private final DatabaseModel model;
  private EntityFactory fab;

  private final Set<Edge> visitedEdges;
  private final Set<Table> visitedTables;

  public DataGenerator(DatabaseModel model)
  {
    this.model = model;
    this.visitedEdges = new HashSet<Edge>();
    this.visitedTables = new HashSet<Table>();
  }

  public Entities generate()
  {
    return generate(new LinkedList<Table>());
  }

  public Entities generate(String startTable)
  {
    return generate(getTable(model, startTable));
  }

  public Entities generate(Table start)
  {
    List<Table> list = new LinkedList<Table>();
    list.add(start);
    return generate(list);
  }

  public Entities generate(List<Table> order)
  {
    fab = new EntityFactory(model);
    visitedEdges.clear();
    visitedTables.clear();

    for (Table table : order) {
      visitTable(table);
    }

    // visit all unvisited Tables...
    for (Table table : getTableOrder(model)) {
      visitTable(table);
    }

    fab.ensureEntityCount();

    int count = 0;
    while (count < 5 && !validateBlueprints(model)) {
      count++;
    }

    Entities result = fab.getEntities();
    result.setLoopCount(count);
    fab = null;
    return result;
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
    for (Edge edge : table.getOutgoingEdges())
    {
      handleEdge(edge, edge.getDestination().getTable());
    }

    for (Edge edge : table.getIncomingEdges())
    {
      handleEdge(edge, edge.getSource().getTable());
    }
  }

  private void handleAssociativeTable(AssociativeTable table)
  {
    Column leftColumn = table.getAssociativeColumns().get(0);
    Column rightColumn = table.getAssociativeColumns().get(1);

    Multiplicity leftMultiplicity = Multiplicity.parse(leftColumn.getRelation().getForeignMultiplicity());
    Multiplicity rightMultiplicity = Multiplicity.parse(rightColumn.getRelation().getForeignMultiplicity());

    generateAssociative(leftColumn, leftMultiplicity.getMin(), rightColumn, rightMultiplicity.getMin());
    generateAssociative(leftColumn, leftMultiplicity.getMax(), rightColumn, rightMultiplicity.getMin());
    generateAssociative(leftColumn, leftMultiplicity.getMin(), rightColumn, rightMultiplicity.getMax());
    generateAssociative(leftColumn, leftMultiplicity.getMax(), rightColumn, rightMultiplicity.getMax());

    // visit the associated tables :-)
    visitTable(leftColumn.getRelation().getForeignColumn().getTable());
    visitTable(rightColumn.getRelation().getForeignColumn().getTable());
  }

  private void generateAssociative(Column leftColumn, Multiplicity.Border leftBorder, Column rightColumn,
      Multiplicity.Border rightBorder)
  {
    Table leftTable = leftColumn.getRelation().getForeignColumn().getTable();
    Table rightTable = rightColumn.getRelation().getForeignColumn().getTable();

    Edge leftEdge = new Edge(leftColumn);
    Edge rightEdge = new Edge(rightColumn);
    visitedEdges.add(leftEdge);
    visitedEdges.add(rightEdge);

    // swap border counts :-)
    int leftCount = getCount(rightBorder, rightColumn);
    int rightCount = getCount(leftBorder, leftColumn);
    //System.out.println(leftColumn.getJavaName() + " <---> " + rightColumn.getJavaName() + ": " + leftCount + ":" + rightCount);

    if (isOptional(rightBorder))
    {
      fab.getEntity(leftTable, leftEdge, EntityCreationMode.noRelation());
    }
    if (isOptional(leftBorder))
    {
      fab.getEntity(leftTable, leftEdge, EntityCreationMode.noRelation());
    }

    EntityBlueprint[] leftBps = new EntityBlueprint[leftCount];
    EntityBlueprint[] rightBps = new EntityBlueprint[rightCount];

    for (int l = 0; l < leftCount; l++)
    {
      leftBps[l] = fab.getEntity(leftTable, leftEdge, EntityCreationMode.fixed(1));
    }
    for (int r = 0; r < rightCount; r++)
    {
      rightBps[r] = fab.getEntity(rightTable, rightEdge, EntityCreationMode.fixed(1));
    }

    for (int l = 0; l < leftCount; l++) {
      for (int r = 0; r < rightCount; r++) {
        EntityBlueprint entity = fab.getEntity(leftColumn.getTable(),  leftEdge,  EntityCreationMode.fixed(1));

        entity.setReference(leftEdge, leftBps[l]);
        entity.setReference(rightEdge, rightBps[r]);
      }
    }
  }

  private boolean isOptional(Multiplicity.Border border)
  {
    return border.getValue() == 0;
  }

  private int getCount(Multiplicity.Border border, Column column)
  {
    if (border.isInfinite()) {
      if (column.getInfinite() != null) {
        return column.getInfinite();
      }
      if (column.getTable().getInfinite() != null) {
        return column.getTable().getInfinite();
      }
      return model.getInfinite();
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

    if (!next.isAssociativeTable()) {
      visitedEdges.add(edge);
      new EdgeHandler(edge, fab).handle();
    }

    visitTable(next);
  }

  private Table getTable(DatabaseModel model, String name)
  {
    for (Table table : model.getTables()) {
      if (table.getJavaName().equalsIgnoreCase(name)) {
        return table;
      }
    }

    return null;
  }

  private List<Table> getTableOrder(DatabaseModel model)
  {
    List<Table> result = new LinkedList<Table>();

    for (Table table : model.getTables()) {
      result.add(table);
    }

    Collections.sort(result, new Comparator<Table>() {

      @Override
      public int compare(Table left, Table right)
      {
        int l = left.getIncomingEdgeCount();
        int r = right.getIncomingEdgeCount();
        return l < r ? -1 : l == r ? 0 : 1;
      }
    });

    return result;
  }

  private boolean validateBlueprints(DatabaseModel model)
  {
    for (Table table : model.getTables()) {
      if (!table.getOutgoingEdges().isEmpty()) {
        List<EntityBlueprint> bps = new LinkedList<EntityBlueprint>(fab.getEntities().getTableBlueprints(table));
        for (EntityBlueprint bp : bps) {
          if (!validateBlueprintOutgoing(bp, table)) {
            return false;
          }
        }
      }
      if (!table.getIncomingEdges().isEmpty()) {
        List<EntityBlueprint> bps = new LinkedList<EntityBlueprint>(fab.getEntities().getTableBlueprints(table));
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
        EntityBlueprint entity= fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.minMax(edge.getSource().getMin(), edge.getSource().getMax()));
        bp.setReference(edge, entity);
        entity.appendLog("used for validation");
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
        EntityBlueprint entity= fab.getEntity(edge.getSource().getTable(), edge, EntityCreationMode.minMax(edge.getDestination().getMin(), edge.getDestination().getMax()));
        entity.setReference(edge, bp);
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
      System.out.println("Handling: " + edge.getSource().getTable().getName() + " ---> " + edge.getDestination().getTable().getName());

      // Destination is always m..1
      generate(edge.getDestination().getMin(), edge.getSource().getMin());
      generate(edge.getDestination().getMin(), edge.getSource().getMax());
      generate(edge.getDestination().getMax(), edge.getSource().getMin());
      generate(edge.getDestination().getMax(), edge.getSource().getMax());

      if (edge.getDestination().getMin().getValue() > 0) {
        for (EntityBlueprint bp : fab.getUnrelatedBlueprints(edge.getSource().getTable(), edge)) {
          EntityBlueprint entity = fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.minMax(edge.getDestination().getMin(), edge.getDestination().getMax()));
          bp.setReference(edge, entity);
        }
      }

      // check if existing entities have valid relations
      // check if the existing entities need a relation
      // iterate over these entities
      // get a relation entity from the fab to link it
      // check if the new relation entity needs further relations ... :-(

      //System.out.println();
    }

    void generate(Multiplicity.Border destBorder, Multiplicity.Border sourceBorder)
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
      System.out.println("  Create: " + id);

      if (destBorder == 0) {
        fab.getEntity(edge.getSource().getTable(), edge, EntityCreationMode.noRelation());
      } else if (sourceBorder == 0) {
        fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.noRelation());
      } else {
        System.out.println("getting left");
        EntityBlueprint entity = fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.fixed(sourceBorder));
        System.out.println("getting right");
        for (int i = 0; i < sourceBorder; i++) {
          EntityBlueprint result = fab.getEntity(edge.getSource().getTable(), edge, EntityCreationMode.fixed(1));
          result.setReference(edge, entity);
        }
      }
      
      System.out.println("  Done Create");

      if (destBorder == 0) {
        generate(1, sourceBorder);
      }
      if (sourceBorder == 0) {
        generate(destBorder, 1);
      }
    }
  }

}
