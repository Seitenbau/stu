package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Optional;

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
    for (Entry<Table, List<EntityBlueprint>> entry : blueprints.entrySet()) {
      System.out.println(entry.getKey().getName() + ": " + entry.getValue().size());
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
        System.out.println("---Returning Entity for " + table.getName() + "---");
        return new EntityFactoryResult(blueprint, true);
      }

      EntityCreationMode existingMode = creationInformation.get();

      if (existingMode.getMax() == 0) {
        continue;
      }

      if (mode.isAny() && existingMode.isAny()) {
        System.out.println("---Returning Entity for " + table.getName() + " [ANY-ANY] ---");
        return new EntityFactoryResult(blueprint, true);
      }

      // TODO check if the mode matches with the existing mode
      // if it is an open 0/1..max relation and we want an 0/1..max2 relation, use it ?
    }

    System.out.println("---Creating Entity (" + (list.size() + 1) + ") for " + table.getName() + "---");

    EntityBlueprint result = new EntityBlueprint();
    result.setCreationInformation(edge, mode);
    list.add(result);
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

  private List<EntityBlueprint> getTableBlueprints(Table table)
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
