package com.seitenbau.testing.asserts.fest.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.fest.assertions.Assertions;
import org.fest.assertions.MapAssert;

/**
 * Helper for fest-style URL Assertions
 */
public class UriAssert extends UrlsAssert<UriAssert>
{
  URI _uri;

  /**
   * Create new Assertion against a URI
   * 
   * @param uri a URI
   */
  public UriAssert(URI uri)
  {
    _uri = uri;
  }

  /**
   * Check if the given URI has a port
   */
  public UriAssert hasPort()
  {
    isNotNull();
    Assertions.assertThat(_uri.getPort()).as("uri-port").isNotEqualTo(-1);
    return this;
  }

  /**
   * Check if the given URI has no port
   */
  public UriAssert hasNoPort()
  {
    isNotNull();
    Assertions.assertThat(_uri.getPort()).as("uri-port").isEqualTo(-1);
    return this;
  }

  /**
   * Check if the given URI has a fragment
   */
  public UriAssert hasFragment()
  {
    isNotNull();
    Assertions.assertThat(_uri.getFragment()).as("uri-fragment").isNotNull();
    return this;
  }

  /**
   * Check if the given URI has a fragment
   */
  public UriAssert hasNoFragment()
  {
    isNotNull();
    Assertions.assertThat(_uri.getFragment()).as("uri-fragment").isNull();
    return this;
  }

  /**
   * Check if the given URI has a query string
   */
  public UriAssert hasQueryPart()
  {
    isNotNull();
    Assertions.assertThat(_uri.getQuery()).as("uri-query").isNotNull();
    return this;
  }

  /**
   * Check if the given URI has a query string
   */
  public UriAssert hasNoQueryPart()
  {
    isNotNull();
    Assertions.assertThat(_uri.getQuery()).as("uri-qeury").isNull();
    return this;
  }

  /**
   * Check if the given scheme is equal to the scheme component of the
   * URI
   * 
   * @param scheme
   * @return
   */
  public UriAssert hasProtocol(String scheme)
  {
    isNotNull();
    Assertions.assertThat(_uri.getScheme()).isEqualTo(scheme);
    return this;
  }

  /**
   * Check if the given host is equal to the host component of the URI
   * 
   * @param host
   * @return
   */
  public UriAssert containsHost(String host)
  {
    isNotNull();
    Assertions.assertThat(_uri.getHost()).isEqualTo(host);
    return this;
  }

  /**
   * Check if the given port is equal to the port component of the URI
   * 
   * @param port
   * @return
   */
  public UriAssert hasPort(Integer port)
  {
    isNotNull();
    Assertions.assertThat(port).isNotNull();
    Assertions.assertThat(_uri.getPort()).isEqualTo(port);
    return this;
  }

  /**
   * Check if the given path is equal to the path component of the URI
   * 
   * @param port
   * @return
   */
  public UriAssert hasPath(String path)
  {
    isNotNull();
    Assertions.assertThat(_uri.getPath()).isEqualTo(path);
    return this;
  }

  /**
   * Check if the given fragment is equal to the fragment component of
   * the URI
   * 
   * @param port
   * @return
   */
  public UriAssert hasFragment(String fragment)
  {
    isNotNull();
    Assertions.assertThat(_uri.getFragment()).isEqualTo(fragment);
    return this;
  }

  /**
   * Check if the given key value pair is contained in the query
   * string of the URI
   * 
   * @param port
   * @return
   */
  public UriAssert queryIsEqualTo(String expected)
  {
    String query = _uri.getRawQuery();
    Assertions.assertThat(query).isEqualTo(expected);
    return this;
  }

  /**
   * Check if the given key value pair is contained in the query
   * string of the URI
   * 
   * @param port
   * @return
   */
  public UriAssert containsQuery(String key, String value)
  {
    Map<String, String> queries = getQueryMap();
    Assertions.assertThat(queries).includes(MapAssert.entry(key, value));
    return this;
  }

  /**
   * Check if the given key value pairs are contained in the query
   * string of the URI
   * 
   * @param port
   * @return
   */
  public UriAssert containsQueries(Map<String, String> entries)
  {
    Assertions.assertThat(entries).isNotEmpty();
    Set<String> keySet = entries.keySet();

    Map<String, String> queries = getQueryMap();

    for (String key : keySet)
    {
      Assertions.assertThat(queries).includes(
          MapAssert.entry(key, entries.get(key)));
    }
    return this;
  }

  private Map<String, String> getQueryMap()
  {
    isNotNull();
    return UriAssert.getQueryMap(_uri.getRawQuery());
  }

  /**
   * Helper which parses a key value pair map from the given query
   * string.
   * 
   * @param queryString
   * @return
   */
  public static Map<String, String> getQueryMap(String queryString)
  {
    Assertions.assertThat(queryString).isNotNull();
    StringTokenizer tokenizer = new StringTokenizer(queryString, "=&");
    Map<String, String> queries = new HashMap<String, String>();
    while (tokenizer.hasMoreTokens())
    {
      String name = tokenizer.nextToken();
      String val = tokenizer.nextToken();
      queries.put(name, val);
    }
    return queries;
  }

  public UriAssert isEqualTo(String expect)
  {
    URI epxectedUri = asUri(expect);
    Assertions.assertThat(_uri).isEqualTo(epxectedUri);
    return this;
  }

  protected URI asUri(String aUri)
  {
    try
    {
      if (aUri == null)
      {
        return null;
      }
      return new URI(aUri);
    }
    catch (URISyntaxException e)
    {
      throw new RuntimeException(
          "The Expected URI was wrong! Check you Testcode! :" + aUri, e);
    }
  }

  protected String urlAsString()
  {
    return _uri.toString();
  }

  @Override
  protected Object urlAsObject()
  {
    return _uri;
  }
}
