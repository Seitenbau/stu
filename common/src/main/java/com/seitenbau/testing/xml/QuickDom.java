package com.seitenbau.testing.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Creates an DOM out of nothing based on XSTream and Regex.
 * 
 * Only use this for inline parsing!
 */
public class QuickDom
{
  static String regex = "<(.*?)( .*?)?>(.*?)</\\1>";

  String name;

  String attibutes;

  String content;

  List<QuickDom> childs;

  String ns;

  public static List<QuickDom> build(String source)
  {
    Matcher matcher = Pattern.compile(regex, Pattern.MULTILINE)
        .matcher(source);
    List<QuickDom> result = new ArrayList<QuickDom>();
    while (matcher.find())
    {
      QuickDom tag = new QuickDom();
      tag.name = matcher.group(1);
      if (tag.name.contains(":"))
      {
        String[] tagNS = tag.name.split(":");
        tag.ns = tagNS[0];
        tag.name = tagNS[1];
      }
      if (matcher.groupCount() == 3)
      {
        tag.attibutes = matcher.group(2);
        tag.content = matcher.group(3);
        tag.childs = QuickDom.build(tag.content);
      }
      else
      {
      }
      result.add(tag);
    }
    return result;
  }

  public String getContent()
  {
    return content;
  }

  public String getAttrib(String attibuteName)
  {
    String attrib = "\\s*?(\\w*?)=[\"\\'](.*?)[\\'\"]";
    Matcher matcher = Pattern.compile(attrib, Pattern.MULTILINE)
        .matcher(attibutes);
    while (matcher.find())
    {
      String name = matcher.group(1);
      String value = matcher.group(2);
      if (name.equals(attibuteName))
      {
        return value;
      }
    }
    return null;
  }

  public String getName()
  {
    return name;
  }

  public String getNs()
  {
    return ns;
  }

  public static QuickDom buildDom(String requestBody)
  {
    QuickDom dom = new QuickDom();
    dom.childs = build(requestBody);
    return dom;
  }

  public QuickDom findTag(Comparable<String> tagName, int depth)
  {
    if (name != null && tagName.compareTo(name) == 0)
    {
      return this;
    }
    if (childs != null && depth != 0)
    {
      for (QuickDom child : childs)
      {
        QuickDom found = child.findTag(tagName, depth - 1);
        if (found != null)
        {
          return found;
        }
      }
    }
    return null;
  }
}