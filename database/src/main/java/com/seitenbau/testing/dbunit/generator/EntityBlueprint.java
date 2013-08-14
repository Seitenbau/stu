package com.seitenbau.testing.dbunit.generator;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;

public class EntityBlueprint
{
  private final Map<String, Object> values;

  private final Map<Edge, EntityCreationMode> relationInformation;

  EntityBlueprint()
  {
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
}
