package com.seitenbau.testing.asserts.fest.impl;

import org.fest.assertions.Assert;
import org.fest.assertions.Assertions;

public abstract class UrlsAssert<MY_TYPE> extends Assert
{
  protected abstract String urlAsString();
  protected abstract Object urlAsObject();
  
  /**
   * Check if the given Url starts with this url part
   * @param start
   */
  public MY_TYPE startsWith(String start)
  {
    String actual = urlAsString();
    Assertions.assertThat(actual).startsWith(start);
    return myself();
  }

  /**
   * Check if the given Url ends with this url part
   * @param start
   */
  public MY_TYPE endsWith(String end)
  {
    String actual = urlAsString();
    Assertions.assertThat(actual).endsWith(end);
    return myself();
  }
  
  /**
   * Check if the given Url is null.
   */
  public MY_TYPE isNull()
  {
    Assertions.assertThat(urlAsObject()).isNull();
    return myself();
  }

  /**
   * Check if the given Url is not null.
   */
  public MY_TYPE isNotNull()
  {
    Assertions.assertThat(urlAsObject()).isNotNull();
    return myself();
  }

  @SuppressWarnings("unchecked")
  protected MY_TYPE myself()
  {
    return (MY_TYPE) this;
  }

}
