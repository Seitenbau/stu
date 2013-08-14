package com.seitenbau.testing.dbunit.generator;

public class EntityCreationMode
{

  private final int min;

  private final int max;

  private EntityCreationMode(int min, int max)
  {
    this.min = min;
    this.max = max;
  }

  static EntityCreationMode noRelation()
  {
    return new EntityCreationMode(0, 0);
  }

  static EntityCreationMode fixed(int value)
  {
    return new EntityCreationMode(value, value);
  }

  static EntityCreationMode min(int min)
  {
    return new EntityCreationMode(min, -1);
  }

  static EntityCreationMode max(int max)
  {
    return new EntityCreationMode(0, max);
  }

  static EntityCreationMode any()
  {
    return new EntityCreationMode(0, -1);
  }
}
