package com.seitenbau.testing.dbunit.modifier;

/**
 * Hilfs Interface für die {@link DatabaseTesterAwareAssertion}
 * Methoden um das Vergleichen von speziellen Werten abzufangen und
 * manuell zu behandeln.
 * 
 * @param <TYPE>
 */
public interface IDataSetOverwriteCompare extends IDataSetModifier
{
  public static final int IS_EQUAL = 0;

  public int compareDataSetElementTo(Object objectToCompareTo);
}
