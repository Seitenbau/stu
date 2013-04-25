package com.seitenbau.testing.dbunit.validator;

/**
 * Dosn't Compare Anyithing. eg, Allways Returnts EQUAL
 */
public class ValueIgnoreContent extends AbstractValueValidator
{

  /**
   * Dosn't Compare Anything. eg, Allways Returnts EQUAL
   */
  public ValueIgnoreContent()
  {
    super("not-set");
  }

  /**
   * Dosn't Compare Anything. eg, Allways Returnts EQUAL
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
