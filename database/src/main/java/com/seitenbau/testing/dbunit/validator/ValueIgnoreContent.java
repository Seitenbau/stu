package com.seitenbau.testing.dbunit.validator;

/**
 * Doesn't compare anything. e.g., always returns EQUAL
 */
public class ValueIgnoreContent extends AbstractValueValidator
{

  /**
   * Doesn't compare anything. e.g., always returns EQUAL
   */
  public ValueIgnoreContent()
  {
    super("not-set");
  }

  /**
   * Doesn't compare anything. e.g., always returns EQUAL
   */
  public ValueIgnoreContent(String markerString)
  {
    super(markerString);
  }

  public int compareDataSetElementTo(Object objectToCompareTo)
  {
    return IS_EQUAL;
  }

}
