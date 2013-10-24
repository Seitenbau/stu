package com.seitenbau.stu.asserts.fest.impl;

import java.net.URL;

public class UrlAssert extends UrlsAssert<UrlAssert>
{
  URL _url;

  public UrlAssert(URL url)
  {
    _url = url;
  }

  @Override
  protected String urlAsString()
  {
    return _url.toString();
  }

  @Override
  protected Object urlAsObject()
  {
    return _url;
  }

}
