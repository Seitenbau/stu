package com.seitenbau.testing.dbunit.generator.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.seitenbau.testing.dbunit.generator.Edge;
import com.seitenbau.testing.dbunit.generator.Table;

public class EntityFactory
{
  private final Map<Table, List<EntityBlueprint>> blueprints;

  public EntityFactory()
  {
    blueprints = new HashMap<Table, List<EntityBlueprint>>();
  }

  public void printStats()
  {
    System.out.println();

    List<Table> tables = new ArrayList<Table>(blueprints.keySet());
    Collections.sort(tables, new Comparator<Table>() {

      @Override
      public int compare(Table arg0, Table arg1)
      {
        return arg0.getJavaName().compareTo(arg1.getJavaName());
      }

    });

    for (Table table : tables) {
      List<EntityBlueprint> bps = blueprints.get(table);
      for (EntityBlueprint bp : bps) {
        System.out.println(bp);
      }
    }

    System.out.println();
    for (Table table : tables) {
      List<EntityBlueprint> bps = blueprints.get(table);
      System.out.println(table.getName() + ":\t" + bps.size());
    }
  }

  /**
   * gets or creates an entity blueprint...
   * @param table
   * @param edge
   * @param mode
   * @return
   */
  public EntityFactoryResult getEntity(Table table, Edge edge, EntityCreationMode mode)
  {
    List<EntityBlueprint> list = getTableBlueprints(table);

    for (EntityBlueprint blueprint : list) {
      // check if blueprint matches...
      Optional<EntityCreationMode> creationInformation = blueprint.getCreationInformation(edge);
      if (!creationInformation.isPresent()) {
        // no relation? use it!
        blueprint.setCreationInformation(edge, mode);
        //System.out.println("---Factory: Returning Entity " + blueprint + "---");
        return new EntityFactoryResult(blueprint, true);
      }

      EntityCreationMode existingMode = creationInformation.get();

      if (existingMode.getMax() == 0) {
        continue;
      }

      if (existingMode.getMax() > 0 && blueprint.getReferencedByList(edge).size() >= existingMode.getMax()) {
        continue;
      }

      // TODO any does not check if constraints are valid...
      if (mode.isAny() && existingMode.isAny()) {
        System.out.println("---Factory: Returning Entity " + blueprint + " [ANY-ANY] ---");
        return new EntityFactoryResult(blueprint, true);
      }

      // TODO check if the mode matches with the existing mode
      // if it is an open 0/1..max relation and we want an 0/1..max2 relation, use it ?
      if (mode.getMax() == existingMode.getMax()) {
        int count = blueprint.getReferencedByList(edge).size();
        if (count < mode.getMax()) {
          //System.out.println("---Factory: Returning Entity " + blueprint + " [MAX:" + mode.getMax() + ", count:" + count + "] --- " + edge.getColumn().getTable().getJavaName() + " " + edge.getColumn().getJavaName() + " " + blueprint);
          //return new EntityFactoryResult(blueprint, true);
        }
      }
    }


    final String refName = table.getJavaName().toUpperCase() + "_" + String.valueOf(list.size() + 1);
    EntityBlueprint result = new EntityBlueprint(table, refName);
    result.setCreationInformation(edge, mode);
    list.add(result);

    //System.out.println("---Factory: Creating Entity " + result + "---");
    return new EntityFactoryResult(result, false);
  }


  public List<EntityBlueprint> getUnrelatedBlueprints(Table table, Edge edge)
  {
    List<EntityBlueprint> result = new ArrayList<EntityBlueprint>();
    List<EntityBlueprint> list = getTableBlueprints(table);
    for (EntityBlueprint bp : list) {
      if (!bp.getCreationInformation(edge).isPresent()) {
        result.add(bp);
      }
    }

    return result;
  }

  public List<EntityBlueprint> getTableBlueprints(Table table)
  {
    if (blueprints.containsKey(table)) {
      return blueprints.get(table);
    }

    List<EntityBlueprint> list = new ArrayList<EntityBlueprint>();
    blueprints.put(table, list);
    return list;
  }


  public static class EntityFactoryResult {

    private final EntityBlueprint entity;

    private final boolean alreadyExisted;

    public EntityFactoryResult(EntityBlueprint entity, boolean alreadyExisted)
    {
      this.entity = entity;
      this.alreadyExisted = alreadyExisted;
    }

    public EntityBlueprint getEntity()
    {
      return entity;
    }

    public boolean isAlreadyExisted()
    {
      return alreadyExisted;
    }

  }


}
