package com.seitenbau.testing.dbunit.generator.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Optional;
import com.seitenbau.testing.dbunit.generator.Edge;
import com.seitenbau.testing.dbunit.generator.Table;

public class EntityBlueprint
{
  private final Table table;

  private String refName;

  private final Map<String, Object> values;

  private final Map<Edge, EntityCreationMode> relationInformation;

  // TODO remove again :-)
  private final StringBuilder log;

  EntityBlueprint(Table table, String refName)
  {
    this.refName = refName;
    this.table = table;
    values = new HashMap<String, Object>();
    relationInformation = new HashMap<Edge, EntityCreationMode>();
    log = new StringBuilder();
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

  public String getRefName()
  {
    return refName;
  }

  public void setRefName(String refName)
  {
    this.refName = refName;
  }

  @Override
  public String toString()
  {
    StringBuilder result = new StringBuilder();
    result.append(table.getJavaName());
    result.append("BP[ ");
    result.append("REF: ");
    result.append(refName);
    result.append(" ");
    for (Entry<String, Object> entry : values.entrySet()) {
      result.append(entry.getKey());
      result.append(":'");
      if (entry.getValue() instanceof EntityBlueprint) {
        EntityBlueprint bp = (EntityBlueprint)entry.getValue();
        result.append(bp.getRefName());
      } else {
        result.append(entry.getValue());
      }
      result.append("' ");
    }
    if (log.length() > 0) {
      result.append("LOG: ");
      result.append(log);
      result.append(" ");
    }
    result.append("]");
    return result.toString();
  }

  public void setValue(String key, Object value)
  {
    Object old = values.put(key, value);
    if (old != null) {
      throw new IllegalStateException("Overwrite " + key + " with " + value + " (" + this + ", " + old + ")");
    }
  }

  public Object getValue(String key)
  {
    return values.get(key);
  }

  public void appendLog(String text)
  {
    if (log.length() > 0) {
      log.append(", ");
    }
    log.append(text);
  }
}
