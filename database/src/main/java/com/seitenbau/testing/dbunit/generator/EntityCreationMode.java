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

  public static EntityCreationMode noRelation()
  {
    return new EntityCreationMode(0, 0);
  }

  public static EntityCreationMode fixed(int value)
  {
    return new EntityCreationMode(value, value);
  }

  public static EntityCreationMode min(int min)
  {
    return new EntityCreationMode(min, -1);
  }

  public static EntityCreationMode max(int max)
  {
    return new EntityCreationMode(0, max);
  }

  public static EntityCreationMode any()
  {
    return new EntityCreationMode(0, -1);
  }
}
