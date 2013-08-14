package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

public class EntityFactory
{
  private final Map<Table, List<EntityBlueprint>> blueprints;

  public EntityFactory()
  {
    blueprints = new HashMap<Table, List<EntityBlueprint>>();
  }

  /**
   * gets or creates an entity blueprint...
   * @param table
   * @param edge
   * @param mode
   * @return
   */
  public EntityBlueprint getEntity(Table table, Edge edge, EntityCreationMode mode)
  {
    List<EntityBlueprint> list = getTableBlueprints(table);

    for (EntityBlueprint blueprint : list) {
      // check if blueprint matches...
      Optional<EntityCreationMode> creationInformation = blueprint.getCreationInformation(edge);
      if (!creationInformation.isPresent()) {
        // no relation? use it!
        blueprint.setCreationInformation(edge, mode);
        System.out.println("---Returning Entity for " + table.getName() + "---");
        return blueprint;
      }

      //creationInformation.get().
    }

    System.out.println("---Creating Entity (" + (list.size() + 1) + ") for " + table.getName() + "---");

    EntityBlueprint result = new EntityBlueprint();
    result.setCreationInformation(edge, mode);
    list.add(result);
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
}
