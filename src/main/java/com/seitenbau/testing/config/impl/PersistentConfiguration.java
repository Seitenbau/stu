package com.seitenbau.testing.config.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.seitenbau.testing.config.StoredProperty;
import com.seitenbau.testing.config.ValueProcessor;
import com.seitenbau.testing.config.ValueProvider;

public class PersistentConfiguration implements ValueProvider, ValueProvider.ValuesAsMap, ValueProvider.ValuesPrintable
{
  public static final String CONFIG_PATH_DEFAULT = "/config/test/";

  public static final String CONFIG_FILE = "default-test";

  static final Pattern mapPattern = Pattern.compile("(.*)\\[([^\\]]*)\\]");

  protected Properties _properties;

  protected VariableDslProcessor _dsl;

  protected Map<String, Map<String, String>> _maps;

  protected Map<String, Map<String, String>> _processed;

  protected String _configPath;

  public PersistentConfiguration(VariableDslProcessor variableDslProcessor)
  {
    _dsl = variableDslProcessor;
  }

  public void initValuesFor(String[] environment, ValueProcessor processor)
  {
    try
    {
      loadStateInernal(environment, processor);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  private void loadStateInernal(String[] environments, ValueProcessor processor) throws IOException
  {
    if (_properties != null)
    {
      return;
    }
    // default file
    List<String> filesToLoad = new ArrayList<String>();
    filesToLoad.add(toPath(CONFIG_FILE + ".properties"));
    System.out.print("##### Loading Configuration for Environment : ");
    for (String environment : environments)
    {
      String id = (environment != null ? environment.trim().toLowerCase() : "");
      if (id.length() > 0)
      {
        System.out.print("'" + id + "' ");
        filesToLoad.add(toPath(id + ".properties"));
        filesToLoad.add(toPath("overwrite." + id + ".properties"));
      }
    }
    System.out.println();
    Properties prop = new Properties();
    processor.beforeLoading(prop);
    for (String filename : filesToLoad)
    {
      String fn = filename.toLowerCase();
      System.out.print("# Loading Configuration : " + fn);
      boolean done = loadProperties(prop, fn);
      if (done)
      {
        System.out.print(" [done]");
      }
      System.out.println();
    }
    processor.afterLoading(prop);
    processProperties(prop);
    _properties = prop;
    System.out.println("##### " + prop.size() + " properties loaded");
  }

  private void processProperties(Properties properties)
  {

    Map<String, Map<String, String>> maps = new HashMap<String, Map<String, String>>();
    for (Entry<Object, Object> entry : properties.entrySet())
    {
      String property = (String) entry.getKey();
      Matcher matcher = mapPattern.matcher(property);
      if (matcher.matches())
      {
        String mapName = matcher.group(1);
        String mapKey = matcher.group(2);
        String mapValue = (String) entry.getValue();

        Map<String, String> map = maps.get(mapName);
        if (map == null)
        {
          map = new HashMap<String, String>();
          maps.put(mapName, map);
        }
        map.put(mapKey, mapValue);
      }
    }
    _maps = maps;
    _processed = new HashMap<String, Map<String, String>>();
  }

  String toPath(String part)
  {
    if (_configPath == null)
    {
      return CONFIG_PATH_DEFAULT + part;
    }
    else
    {
      return _configPath + part;
    }
  }

  protected boolean loadProperties(Properties prop, String filename) throws IOException
  {
    InputStream stream = PersistentConfiguration.class.getResourceAsStream(filename);
    if (stream != null)
    {
      prop.load(stream);
      return true;
    }
    return false;
  }

  public Properties getProperties()
  {
    return _properties;
  }

  public String getMapEntry(String mapName, String key, String defaultValue)
  {
    if (_maps == null)
    {
      return StoredProperty.NOT_SET_VALUE;
    }
    Map<String, String> map = _maps.get(mapName);
    if (map == null)
    {
      return StoredProperty.NOT_SET_VALUE;
    }
    String value = map.get(key);
    if (value == null)
    {
      return defaultValue;
    }
    return _dsl.processDsl(getValueMap(), value, value);
  }

  public Map<String, String> getValueMap()
  {
    Map<String, String> map = new HashMap<String, String>();
    for (Entry<Object, Object> x : _properties.entrySet())
    {
      String key = (String) x.getKey();
      String value = (String) x.getValue();
      map.put(key, value);
    }
    return map;
  }

  public Map<String, Object> getValues()
  {
    Map<String, Object> map = new HashMap<String, Object>();
    for (Entry<Object, Object> x : _properties.entrySet())
    {
      String key = (String) x.getKey();
      Object value = x.getValue();
      map.put(key, value);
    }
    return map;
  }

  public String getString(String key)
  {
    return getString(key, null);
  }

  public String getString(String key, String defaultvalue)
  {
    return get(key, defaultvalue);
  }

  private String get(String key)
  {
    return get(key, null);
  }

  private String get(String key, String defaultValue)
  {
    return _dsl.getValue(getValueMap(), key, defaultValue);
  }

  /**
   * @return the value or null if not found
   */
  public Integer getInteger(String key)
  {
    String value = get(key);
    if (value == null)
    {
      return null;
    }
    return Integer.valueOf(value);
  }

  /**
   * @return the value or null if not found
   */
  public Boolean getBoolean(String key)
  {
    String value = get(key, null);
    if (value == null)
    {
      return null;
    }
    return Boolean.valueOf(value);
  }

  public ValueProvider set(String key, String value)
  {
    if (_properties == null)
    {
      throw new IllegalStateException("You didn't load properties first");
    }
    _properties.put(key, value);
    return this;
  }

  public void printValues()
  {
    System.out.println("#### loaded Properties are : ");
    if (_properties == null || _properties.size() == 0)
    {
      System.out.println("Nothing loaded");
      return;
    }
    Enumeration<Object> keys = _properties.keys();
    while (keys.hasMoreElements())
    {
      Object key = keys.nextElement();
      Object val = _properties.get(key);
      System.out.println(key + "=" + val);
    }
    System.out.println("####");
  }

  public Map<String, String> getMap(String key)
  {
    if (_maps == null)
    {
      return null;
    }
    Map<String, String> map = null;
    if (_processed != null)
    {
      map = _processed.get(key);
    }
    if (map == null)
    {
      Map<String, String> tmp = _maps.get(key);
      Map<String, String> neu = new HashMap<String, String>();
      for (Entry<String, String> entry : tmp.entrySet())
      {
        String valueProcessed = _dsl.processDsl(getValueMap(), entry.getValue(), StoredProperty.NOT_SET_VALUE);
        neu.put(entry.getKey(), valueProcessed);
      }
      map = neu;
      _processed.put(key, map);
    }
    return map;
  }

  protected void setMap(String key, Map<String, String> value)
  {
    if (_maps == null)
    {
      _maps = new HashMap<String, Map<String, String>>();
    }
    _maps.put(key, value);
  }

  public void setConfigPath(String sourcePath)
  {
    if (!sourcePath.contains("classpath://"))
    {
      throw new IllegalArgumentException("currently only classpath: is a supported location");
    }
    String path = sourcePath.replace("classpath://", "");
    if (!path.endsWith("/"))
    {
      path = path + "/";
    }
    _configPath = path;
  }

}
