package com.seitenbau.testing.dbunit.validator;

import junitx.framework.Assert;

/**
 * DBUnit Value Validator if a given &lt;value/&gt; is NOT equal to
 * the given Object.
 */
public class ValueNotEquals extends AbstractValueValidator
{

  protected Object fNotEquals;

  /**
   * DBUnit Value Validator if a given <value/> is NOT equal to the
   * given Object.
   * 
   * @param notEquals A Object to which the real Database value will
   *        be compared to via equals.
   */
  public ValueNotEquals(Object notEquals)
  {
    super("not-set");
    fNotEquals = notEquals;
  }

  /**
   * DBUnit Value Validator if a given <value/> is NOT equal to the
   * given Object.
   * 
   * @param markerString Marker in the dbunit XML Dataset File which
   *        will be handled special by this class to Compare the Real
   *        Value from the Database via equals.
   * 
   * @param notEquals A Object to which the real Database value will
   *        be compared to via equals.
   */
  public ValueNotEquals(String markerString, Object notEquals)
  {
    super(markerString);
    fNotEquals = notEquals;
  }

  /**
   * {@inheritDoc}
   */
  public int compareDataSetElementTo(Object objectToCompareTo)
  {
    Assert.assertNotEquals(fNotEquals, objectToCompareTo);
    return IS_EQUAL;
  }

}