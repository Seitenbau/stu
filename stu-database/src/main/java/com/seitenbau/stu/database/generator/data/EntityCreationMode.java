package com.seitenbau.stu.database.generator.data;


public class EntityCreationMode
{

  public enum Direction { IN, OUT, ANY }

  private final BorderValue min;

  private final BorderValue max;

  private final Direction direction;

  private final int hashCode;

  private EntityCreationMode(BorderValue min, BorderValue max, Direction direction)
  {
    this.min = min;
    this.max = max;
    this.direction = direction;

    hashCode = calculateHashcode(min, max, direction);
  }

  private static int calculateHashcode(BorderValue min, BorderValue max, Direction direction)
  {
    int result = 17;
    result = 31 * result + min.hashCode();
    result = 31 * result + min.hashCode();
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
    return (min.getValue() == 0) && (max.isInifinite());
  }

  public BorderValue getMax()
  {
    return max;
  }

  public Direction getDirection()
  {
    return direction;
  }

  public static EntityCreationMode noRelation()
  {
    return new EntityCreationMode(BorderValue.valueOf(0), BorderValue.valueOf(0), Direction.ANY);
  }

  public static EntityCreationMode fixedInt(int value, Direction direction)
  {
    return new EntityCreationMode(BorderValue.valueOf(value), BorderValue.valueOf(value), direction);
  }

  public static EntityCreationMode fixed(BorderValue value, Direction direction)
  {
    return new EntityCreationMode(value, value, direction);
  }

  public static EntityCreationMode min(int min, Direction direction)
  {
    return new EntityCreationMode(BorderValue.valueOf(min), BorderValue.infinite(-1), direction);
  }

  public static EntityCreationMode max(int max, Direction direction)
  {
    return new EntityCreationMode(BorderValue.valueOf(0), BorderValue.valueOf(max), direction);
  }

  public static EntityCreationMode any()
  {
    return new EntityCreationMode(BorderValue.valueOf(-1), BorderValue.valueOf(-1), Direction.ANY);
  }

  public static EntityCreationMode minMax(Multiplicity.Border min, Multiplicity.Border max, Direction direction)
  {
    if (max.isInfinite()) {
      return new EntityCreationMode(BorderValue.valueOf(min.getValue()), BorderValue.infinite(max.getValue()), direction);
    } else {
      return new EntityCreationMode(BorderValue.valueOf(min.getValue()), BorderValue.valueOf(max.getValue()), direction);
    }
  }

}
