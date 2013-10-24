package com.seitenbau.stu.dbunit.validator;

import junitx.framework.Assert;

import com.seitenbau.stu.dbunit.modifier.IDataSetOverwriteCompare;
import com.seitenbau.stu.dbunit.modifier.Replacer;

/**
 * 
 * Ensures the Value from the Database equals the given Value. This is
 * just a convenient Wrapper around the {@link Replacer} class. ( This
 * class therefore can also be used to Replace a Value in a Dataset )
 * 
 */
public class ValueEquals extends AbstractValueValidator
{
  protected Object _Equals;

  /**
   * Ensures the Value from the Database equals the given Value. This
   * is just a convenient Wrapper around the {@link Replacer} class. (
   * This class therefore can also be used to Replace a Value in a
   * Dataset )
   * 
   * @param expectedValueToEqual The value the real Database Value
   *        must be equal to.
   */
  public ValueEquals(Object expectedValueToEqual)
  {
    super("not-in-use");
    _Equals = expectedValueToEqual;
  }

  /**
   * * Ensures the Value from the Database equals the given Value.
   * This is just a convenient Wrapper around the {@link Replacer}
   * class. ( This class therefore can also be used to Replace a Value
   * in a Dataset )
   * 
   * @param markerString String Constant which ist replaced in the
   *        Dataset with the given equal value
   * 
   * @param expectedValueToEqual The value the real Database Value
   *        must be equal to.
   */
  public ValueEquals(String markerString, Object expectedValueToEqual)
  {
    super(markerString);
    _Equals = expectedValueToEqual;
  }

  /**
   * {@inheritDoc}
   */
  public int compareDataSetElementTo(Object objectToCompareTo)
  {
    Assert.assertEquals(_Equals, objectToCompareTo);
    return IS_EQUAL;
  }

  /**
   * Overwrites the compare Method. Use This only if you know the
   * Dataset will not be inserted anywhere, bcause this will then
   * fail.
   * @param compareTo
   */
  public void setCompareTo(final Comparable<Object> compareTo2)
  {
    setReplaceValue(new IDataSetOverwriteCompare()
    {
      public int compareDataSetElementTo(Object objectToCompareTo)
      {
        return compareTo2.compareTo(objectToCompareTo);
      }
    });
  }
}
