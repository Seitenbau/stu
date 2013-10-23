package com.seitenbau.testing.dbunit.validator;

public class ValueStartsWith extends AbstractValueValidator
{
  private String _startsWith;

  public ValueStartsWith(String startsWith)
  {
    super("fake");
    _startsWith = startsWith;
  }

  public int compareDataSetElementTo(Object objectToCompareTo)
  {
    if (objectToCompareTo == null && _startsWith == null)
    {
      return 0;
    }
    if (objectToCompareTo instanceof String)
    {
      String cs = (String) objectToCompareTo;
      return (cs.startsWith(_startsWith) ? 0 : -1);
    }
    System.err.println("ValueStartsWith error. Not of type String : " + objectToCompareTo);
    return 1;
  }

  @Override
  public String toString()
  {
    return "Cell should startWith : '" + _startsWith + "'";
  }
}
