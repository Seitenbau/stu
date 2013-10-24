package com.seitenbau.stu.dbunit.validator;

import org.junit.Assert;

import com.seitenbau.stu.dbunit.modifier.IDataSetOverwriteCompare;

/**
 * Extracts the first occurrence of the Value into the lastValue field
 */
public class ValueExtractor extends AbstractValueValidator implements IDataSetOverwriteCompare
{
  protected Object lastValue;

  public Object getLastValue()
  {
    return lastValue;
  }

  public ValueExtractor()
  {
    super("not-set");
  }

  public ValueExtractor(String markerString)
  {
    super(markerString);
  }

  /**
   * {@inheritDoc}
   */
  public int compareDataSetElementTo(Object objectToCompareTo)
  {
    Assert.assertNull("Multiple Values. Cannot decied which should be extracted ", lastValue);
    lastValue = objectToCompareTo;
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
