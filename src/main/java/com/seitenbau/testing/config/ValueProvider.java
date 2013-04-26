package com.seitenbau.testing.config;

import java.util.Map;

/**
 * Provider for Key-Value pairs
 */
public interface ValueProvider
{
  String getString(String key);

  Boolean getBoolean(String key);

  Integer getInteger(String key);

  ValueProvider set(String key, String value);

  void initValuesFor(String[] environment, ValueProcessor processor);

  Map<String, String> getValueMap();

  String getMapEntry(String mapName, String key, String defaultValue);

  interface ValuesAsMap
  {
    Map<String, Object> getValues();
  }

  interface ValuesPrintable
  {
    void printValues();
  }

  String getString(String key, String defaultvalue);

  Map<String, String> getMap(String key);

}
