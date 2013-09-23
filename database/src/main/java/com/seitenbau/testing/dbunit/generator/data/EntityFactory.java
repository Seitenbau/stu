package com.seitenbau.testing.dbunit.generator.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.seitenbau.testing.dbunit.generator.Column;
import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.Edge;
import com.seitenbau.testing.dbunit.generator.Table;
import com.seitenbau.testing.dbunit.generator.values.ValueGenerator;

public class EntityFactory
{
  private final Entities blueprints;

  private final Map<Column, ValueGenerator> valueGenerators;

  private final DatabaseModel model;

  private final long modelSeed;

  public EntityFactory(DatabaseModel model)
  {
    this.model = model;
    modelSeed = model.getSeed();
    blueprints = new Entities(model);
    valueGenerators = new HashMap<Column, ValueGenerator>();
  }

  public Entities getEntities()
  {
    return blueprints;
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
    List<EntityBlueprint> list = blueprints.getTableBlueprints(table);

    for (EntityBlueprint blueprint : list) {
      // check if blueprint matches...
      Optional<EntityCreationMode> creationInformation = blueprint.getCreationInformation(edge);
      if (!creationInformation.isPresent()) {
        // no relation? use it!
        blueprint.setCreationInformation(edge, mode);
        //System.out.println("---Factory: Returning Entity " + blueprint + "---");
        return blueprint;
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
        return blueprint;
      }

      // TODO check if the mode matches with the existing mode
      // if it is an open 0/1..max relation and we want an 0/1..max2 relation, use it ?
      if (mode.getMax() == existingMode.getMax()) {
        int count = blueprint.getReferencedByList(edge).size();
        if (count < mode.getMax()) {
          //return blueprint;
        }
      }
    }

    EntityBlueprint result = createEntity(table);
    result.setCreationInformation(edge, mode);
    return result;
  }

  public void ensureEntityCount()
  {
    for (Table table : model.getTables()) {
      List<EntityBlueprint> list = blueprints.getTableBlueprints(table);
      while (list.size() < table.getMinEntities()) {
        createEntity(table);
      }
    }
  }

  private EntityBlueprint createEntity(Table table)
  {
    List<EntityBlueprint> list = blueprints.getTableBlueprints(table);
    final String refName = table.getJavaName().toUpperCase() + "_" + String.valueOf(list.size() + 1);
    EntityBlueprint result = new EntityBlueprint(table, refName, this);
    list.add(result);
    return result;
  }

  public List<EntityBlueprint> getUnrelatedBlueprints(Table table, Edge edge)
  {
    List<EntityBlueprint> result = new ArrayList<EntityBlueprint>();
    List<EntityBlueprint> list = blueprints.getTableBlueprints(table);
    for (EntityBlueprint bp : list) {
      if (!bp.getCreationInformation(edge).isPresent()) {
        result.add(bp);
      }
    }

    return result;
  }

  public ValueGenerator getValueGenerator(Column column)
  {
    if (valueGenerators.containsKey(column)) {
      return valueGenerators.get(column);
    }

    long seed = modelSeed + column.getTable().getSeed() + column.getSeed();

    ValueGenerator result = column.getGenerator();
    result.initialize(seed);
    valueGenerators.put(column, result);
    return result;
  }

}
