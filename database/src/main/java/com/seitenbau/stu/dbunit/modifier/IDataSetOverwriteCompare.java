package com.seitenbau.stu.dbunit.modifier;

/**
 * Helping interface for {@link DatabaseTesterAwareAssertion} methods
 * to enable manual handling for comparison of particular values.
 * 
 * @param <TYPE>
 */
public interface IDataSetOverwriteCompare extends IDataSetModifier
{
  public static final int IS_EQUAL = 0;

  public int compareDataSetElementTo(Object objectToCompareTo);
}
