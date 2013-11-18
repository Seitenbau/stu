package com.seitenbau.stu.database.generator.data;


public class EntityCreationMode
{

  private final int min;

  private final int max;

  private final int hashCode;

  private EntityCreationMode(int min, int max)
  {
    this.min = min;
    this.max = max;

    hashCode = 31 * min + max;
  }

  @Override
  public String toString()
  {
    return "(" + min + ".." + max + ")";
  }
  
  @Override
  public int hashCode()
  {
    return hashCode;
  }

  @Override
  public boolean equals(Object o)
  {
    if (!(o instanceof EntityCreationMode)) {
      return false;
    }

    EntityCreationMode e = (EntityCreationMode)o;
    return min == e.min && max == e.max;
  }

  public boolean isAny()
  {
    return (min == 0) && (max == -1);
  }

  public int getMax()
  {
    return max;
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
    return new EntityCreationMode(-1, -1);
  }

  public static EntityCreationMode minMax(int min, int max)
  {
    return new EntityCreationMode(min, max);
  }

  public static EntityCreationMode minMax(Multiplicity.Border min, Multiplicity.Border max)
  {
    return new EntityCreationMode(min.getValue(), max.getValue());
  }

}
