package com.seitenbau.stu.util;

public class StringGenerator
{

  public static String gen(String prefix, String fillup, String postifx,
      int length)
  {
    return StringGenerator.gen(prefix, fillup, postifx, length, false);
  }
  
  public static String gen(String prefix, String fillup, String postifx,
      int length, boolean trim)
  {
    if (length < 0)
    {
      throw new IllegalArgumentException("length was < 0 !");
    }
    if (fillup ==null || fillup.length()==0)  
    {
      throw new IllegalArgumentException("fillup was empty !");
    }
    StringBuilder sb = new StringBuilder();
    sb.append(prefix);
    while (sb.length() < length)
    {
      sb.append(fillup);
    }
    int end = length - postifx.length();
    String text = sb.toString();
    if (end < 0 && sb.length() > length)
    {
      text = sb.substring(0, length);
    }
    else
    {
      text = sb.subSequence(0, end) + postifx;
    }
    if (trim) // assure first and last characters are no whithespaces
    {
      text = text.trim();
      while (text.length() < length)
      {
        text += ".";
      }
    }
    return text;
  }

  public static String genCVS(String separator, String duplContent, int times)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(duplContent);
    int i = 1;
    while (i < times)
    {
      sb.append(separator);
      sb.append(duplContent);
      i++;
    }
    return sb.toString();
  }

  public static String genForSeed(int startSeed, int loops)
  {
    StringBuilder sb = new StringBuilder();

    int x = startSeed;
    for (int i = 0; i < loops; i++)
    {
      x -= (loops - i);
      sb.append(x);
    }
    return sb.toString();
  }

  public static String gen(int length)
  {
    return gen("some", "x", "text", length);
  }

  public static String gen(String fillPattern, int length)
  {
    return StringGenerator.gen(fillPattern, length, false);
  }
  
  public static String gen(String fillPattern, int length, boolean trim)
  {
    return gen("", fillPattern, "", length, trim);
  }

}
