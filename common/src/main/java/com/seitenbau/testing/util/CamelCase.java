package com.seitenbau.testing.util;

public class CamelCase
{

  public static String explode(String group)
  {
    StringBuilder sb = new StringBuilder();
    char[] data = group.toCharArray();
    Character last = null;
    for (int i = 0; i < data.length; i++)
    {
      Character cur = data[i];
      Character next = null;
      if (i + 1 < data.length)
      {
        next = data[i + 1];
      }
      if (cur.equals("_") || cur.charValue() == 95)
      {
        sb.append(" ");
      }
      // insert space before numbers
      else if (!Character.isDigit(cur) && next != null
          && Character.isDigit(next))
      {
        sb.append(cur);
        if (!Character.isUpperCase(cur))
        {
          sb.append(" ");
        }
      }
      else if (!Character.isUpperCase(cur)) // !uppercase to match
                                            // space
      {
        if (last != null && Character.isUpperCase(last)
            && !Character.isDigit(cur))
        {
          sb.append(" ");
        }
        sb.append(cur);
      }
      else if (Character.isUpperCase(cur))
      {
        // last or next is uppercase
        if ((last != null && Character.isUpperCase(last))
            || (next != null && Character.isUpperCase(next)))
        {
          if (last != null && !Character.isUpperCase(last)
              && !(last.equals("_") || last.charValue() == 95))
          {
            sb.append(" ");
          }
          sb.append(cur);
        }
        else
        {
          if (last != null
              && !(cur.equals("_") || last.charValue() == 95))
          {
            if (!(last.equals("_") || last.charValue() == 95))
            {
              sb.append(" ");
            }
          }
          cur = Character.toLowerCase(cur);
          sb.append(cur);
        }
      }
      last = cur;
    }
    return sb.toString();
  }

  /**
   * Just appends the Strings. EachString will start with a uppercase
   * Character. There is no handling for cases where there is no
   * Uppercase character.
   * 
   * @param items
   * @return
   */
  public static String implodeFast(String... items)
  {
    if (items == null)
    {
      return "";
    }
    StringBuffer sb = new StringBuffer();
    for (String item : items)
    {
      sb.append(makeFirstUpperCase(item));
    }
    return sb.toString();
  }

  /**
   * Makes the first Character for the given String lowercase
   * 
   * @param value
   * @return
   */
  public static String makeFirstLowerCase(String value)
  {
    if (value == null)
    {
      return "";
    }
    StringBuffer sb = new StringBuffer();
    if (value.length() > 0)
    {
      sb.append(value.substring(0, 1).toLowerCase());
    }
    if (value.length() > 1)
    {
      sb.append(value.substring(1));
    }
    return sb.toString();
  }

  /**
   * Makes the first Character for the given String UPPERCASE.
   * 
   * @param value
   * @return
   */
  public static String makeFirstUpperCase(String value)
  {
    if (value == null)
    {
      return "";
    }
    StringBuffer sb = new StringBuffer();
    if (value.length() > 0)
    {
      sb.append(value.substring(0, 1).toUpperCase());
    }
    if (value.length() > 1)
    {
      sb.append(value.substring(1));
    }
    return sb.toString();
  }

  /**
   * Just appends the Strings. Each String will start with an uppercase
   * Character and the rest will be lowercase.
   */
  public static String implode(String[] items)
  {
    if (items == null)
    {
      return "";
    }
    StringBuffer sb = new StringBuffer();
    for (String item : items)
    {
      sb.append(makeFirstUpperCase(item.toLowerCase()));
    }
    return sb.toString();
  }

  /**
   * Explodes the given Text and recreates a merged one.
   * VERSION_ID is transformed to VersionId
   * @param name
   * @return
   */
  public static String makeFirstOfBlockUppercase(String name)
  {
    if(name==null) {
      return "";
    }
    String tmp = explode(name);
    String[] items = tmp.split(" ");
    tmp = implode(items);
    return tmp;
  }

}
