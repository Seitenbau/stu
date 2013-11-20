package com.seitenbau.stu.database.generator.data;


public class EntityCreationMode
{

  public enum Direction { IN, OUT, ANY }

  private final int min;

  private final int max;

  private final Direction direction;

  private final int hashCode;

  private EntityCreationMode(int min, int max, Direction direction)
  {
    this.min = min;
    this.max = max;
    this.direction = direction;

    hashCode = calculateHashcode(min, max, direction);
  }

  private static int calculateHashcode(int min, int max, Direction direction)
  {
    int result = 17;
    result = 31 * result + min;
    result = 31 * result + min;
    result = 31 * result + direction.ordinal();
    return result;
  }

  @Override
  public String toString()
  {
    return "(" + min + ".." + max + ", " + direction + ")";
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
    return min == e.min && max == e.max && direction.equals(e.direction);
  }

  public boolean isAny()
  {
    return (min == 0) && (max == -1);
  }

  public int getMax()
  {
    return max;
  }

  public Direction getDirection()
  {
    return direction;
  }

  public static EntityCreationMode noRelation()
  {
    return new EntityCreationMode(0, 0, Direction.ANY);
  }

  public static EntityCreationMode fixed(int value, Direction direction)
  {
    return new EntityCreationMode(value, value, direction);
  }

  public static EntityCreationMode min(int min, Direction direction)
  {
    return new EntityCreationMode(min, -1, direction);
  }

  public static EntityCreationMode max(int max, Direction direction)
  {
    return new EntityCreationMode(0, max, direction);
  }

  public static EntityCreationMode any()
  {
    return new EntityCreationMode(-1, -1, Direction.ANY);
  }

  public static EntityCreationMode minMax(int min, int max, Direction direction)
  {
    return new EntityCreationMode(min, max, direction);
  }

  public static EntityCreationMode minMax(Multiplicity.Border min, Multiplicity.Border max, Direction direction)
  {
    return new EntityCreationMode(min.getValue(), max.getValue(), direction);
  }

}
