package com.seitenbau.testing.dbunit.generator;

import java.util.HashMap;
import java.util.Map;

public class EntityBlueprint
{
  private final Map<String, Object> values;

  private final Map<Edge, EntityCreationMode> relationInformation;

  EntityBlueprint()
  {
    values = new HashMap<String, Object>();
    relationInformation = new HashMap<Edge, EntityCreationMode>();
  }
}
