package com.seitenbau.stu.database.validator;

import static org.junit.Assert.assertNotNull;

/**
 * DBUnit Value Validator if a given &lt;value/&gt; is NOT Null.
 */
public class ValueNotNull extends AbstractValueValidator
{
  /**
   * DBUnit Value Validator if a given <value/> is NOT equal to the
   * given Object.
   * 
   */
  public ValueNotNull()
  {
    super("not-set");
  }

  /**
   * DBUnit Value Validator if a given <value/> is NOT equal to the
   * given Object.
   * 
   * @param markerString Marker in the dbunit XML Dataset File which
   *        will be handled special by this Class to ensure the Real
   *        value from the Database will not be null.
   */
  public ValueNotNull(String markerString)
  {
    super(markerString);
  }

  public int compareDataSetElementTo(Object objectToCompareTo)
  {
    assertNotNull(objectToCompareTo);
    return IS_EQUAL;
  }

}