package com.seitenbau.testing.util;

import java.util.ArrayList;
import java.util.List;

public class UrlBuilder
{
  private String baseUrl;

  private List<Element> path = new ArrayList<Element>();

  protected UrlBuilder(String baseUrl)
  {
    if(baseUrl == null)
    {
      throw new NullPointerException("Base URL can not be null.");
    }
    this.baseUrl = baseUrl;
  }

  public String get()
  {
    StringBuilder sb = new StringBuilder();
    sb.append(baseUrl);
    boolean paramStarted = false;
    for (Element item : path)
    {
      if (item instanceof PathElement)
      {
        String text = ((PathElement) item).getText();
        if (text != null)
        {
          if (sb.charAt(sb.length() - 1) != '/' && !text.startsWith("/"))
          {
            sb.append("/");
          }
          sb.append(text);
        }
      }
      if (item instanceof ParamElement
          && ((ParamElement) item).getValue() != null)
      {
        if (paramStarted)
        {
          sb.append("&");
        }
        else
        {
          sb.append("?");
        }
        paramStarted = true;
        ParamElement param = (ParamElement) item;
        sb.append(param.getKey());
        sb.append("=");
        sb.append(param.getValue());
      }
    }
    return sb.toString();
  }

  public UrlBuilder param(String key, String value)
  {
    path.add(new ParamElement(key, value, "\""));
    return this;
  }

  public UrlBuilder paramBool(String key, String value)
  {
    path.add(new ParamElement(key, value, null));
    return this;
  }

  public UrlBuilder paramInt(String key, Integer value)
  {
    if (value != null)
    {
      path.add(new ParamElement(key, Integer.toString(value), null));
    }
    return this;
  }

  public UrlBuilder paramLong(String key, Long value)
  {
    if (value != null)
    {
      path.add(new ParamElement(key, Long.toString(value), null));
    }
    return this;
  }

  public UrlBuilder path(String subPathElement)
  {
    path.add(new PathElement(subPathElement));
    return this;
  }

  public UrlBuilder pathInt(Integer subPathElement)
  {
    if (subPathElement != null)
    {
      path.add(new PathElement(Integer.toString(subPathElement)));
    }
    return this;
  }

  public UrlBuilder pathLong(Long subPathElement)
  {
    if (subPathElement != null)
    {
      path.add(new PathElement(Long.toString(subPathElement)));
    }
    return this;
  }

  public static UrlBuilder base(String baseUrl)
  {
    return new UrlBuilder(baseUrl);
  }

  public class Element
  {

  }

  public class ParamElement extends Element
  {

    private String key;

    private String value;

    private String spacer;

    public ParamElement(String key, String value, String spacerChar)
    {
      this.key = key;
      this.value = value;
      this.spacer = spacerChar;
    }

    public String getKey()
    {
      return key;
    }

    public String getSpacer()
    {
      return spacer;
    }

    public String getValue()
    {
      return value;
    }

  }

  public class PathElement extends Element
  {

    private String element;

    public PathElement(String subPathElement)
    {
      element = subPathElement;
    }

    public String getText()
    {
      return element;
    }

  }

}
