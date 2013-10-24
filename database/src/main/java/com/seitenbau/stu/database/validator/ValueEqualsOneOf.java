package com.seitenbau.stu.database.validator;

/**
 * The DB value needs to be one of the following values
 */
public class ValueEqualsOneOf extends AbstractValueValidator
{
  private String[] _any;

  /**
   * The DB value needs to be one of the following values
   */
  public ValueEqualsOneOf(String... anyOf)
  {
    super("fake");
    _any = anyOf;
  }

  public int compareDataSetElementTo(Object objectToCompareTo)
  {
    if (objectToCompareTo == null && _any == null)
    {
      return 0;
    }
    if (objectToCompareTo instanceof String)
    {
      String cs = (String) objectToCompareTo;
      for (String one : _any)
      {
        if (cs.equals(one))
        {
          return 0;
        }
      }
      return -1;
    }
    System.err.println("ValueEqualsOneOf error. Not any of String : " + objectToCompareTo);
    return 1;
  }

  @Override
  public String toString()
  {
    return "Cell should contain any of : '" + _any + "'";
  }
}
