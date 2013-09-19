package com.seitenbau.testing.dbunit.generator.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.Table;

public class Entities
{

  private final DatabaseModel model;

  private final Map<Table, List<EntityBlueprint>> blueprints;

  private int loopCount;

  public Entities(DatabaseModel model)
  {
    this.model = model;
    blueprints = new HashMap<Table, List<EntityBlueprint>>();
  }

//  public void addEntity(Table table, EntityBlueprint entity)
//  {
//
//  }

  public DatabaseModel getModel()
  {
    return model;
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

  public Map<Table, List<EntityBlueprint>> getEntities()
  {
    return blueprints;
  }

  void setLoopCount(int count)
  {
    this.loopCount = count;
  }

  public int getLoopCount()
  {
    return loopCount;
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

    System.out.println();
    for (Table table : tables) {
      List<EntityBlueprint> bps = blueprints.get(table);
      System.out.println(table.getName() + ":\t" + bps.size());
    }
  }

}
