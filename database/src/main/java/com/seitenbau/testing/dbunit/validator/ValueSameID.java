package com.seitenbau.testing.dbunit.validator;

/**
 * Compares Cross Reference IDs between Table Values. In both Values
 * must be a equal object. Which Value it has is not of consideration.
 * The First compare will always be correct, the second compare will
 * compare the Value with the first one.
 */
public class ValueSameID extends AbstractValueValidator
{
  protected Object firstID;

  /**
   * Compares Cross Reference IDs between Table Values. In both Values
   * must be a equal object. Which Value it has is not of
   * consideration. The First compare will always be correct, the
   * second compare will compare the Value with the first one.
   */
  public ValueSameID()
  {
    super("not-set");
  }

  /**
   * Compares Cross Reference IDs between Table Values. In both Values
   * must be a equal object. Which Value it has is not of
   * consideration. The First compare will always be correct, the
   * second compare will compare the Value with the first one.
   * @param markerString
   */
  public ValueSameID(String markerString)
  {
    super(markerString);
  }

  public int compareDataSetElementTo(Object objectToCompareTo)
  {
    if (firstID == null)
    {
      firstID = objectToCompareTo;
    }
    else if (!firstID.equals(objectToCompareTo))
    {
      throw new AssertionError("ID's are not equal = " + firstID + " was = " + objectToCompareTo);
    }
    return IS_EQUAL;
  }

}
