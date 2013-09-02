package com.seitenbau.testing.dbunit.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Optional;

public class EntityBlueprint
{
  private final Table table;

  private final Map<String, Object> values;

  private final Map<Edge, EntityCreationMode> relationInformation;

  EntityBlueprint(Table table)
  {
    this.table = table;
    values = new HashMap<String, Object>();
    relationInformation = new HashMap<Edge, EntityCreationMode>();
  }

  Optional<EntityCreationMode> getCreationInformation(Edge edge)
  {
    if (relationInformation.containsKey(edge)) {
      return Optional.of(relationInformation.get(edge));
    }

    return Optional.absent();
  }

  void setCreationInformation(Edge edge, EntityCreationMode mode)
  {
    relationInformation.put(edge, mode);
  }

  public Table getTable()
  {
    return table;
  }

  @Override
  public String toString()
  {
    StringBuilder result = new StringBuilder();
    result.append(table.getJavaName());
    result.append("BP[ ");
    for (Entry<String, Object> entry : values.entrySet()) {
      result.append(entry.getKey());
      result.append(":'");
      if (entry.getValue() instanceof EntityBlueprint) {
        EntityBlueprint bp = (EntityBlueprint)entry.getValue();
        result.append(bp.values.get("_ID"));
      } else {
        result.append(entry.getValue());
      }
      result.append("' ");
    }
    result.append("]");
    return result.toString();
  }

  public void setValue(String key, Object value)
  {
    values.put(key, value);
  }

  public Object getValue(String key)
  {
    return values.get(key);
  }
}
