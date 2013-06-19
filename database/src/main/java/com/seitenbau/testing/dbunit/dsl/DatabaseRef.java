package com.seitenbau.testing.dbunit.dsl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Base type for all database Ref types
 */
public class DatabaseRef
{

  /**
   * Helper method for getting a List from a Map of lists. Creates
   * and adds the list, if no existing list was found.
   * @param map The map containing the lists.
   * @param key The key (scope)
   * @return The corresponding list for the key
   * @throws RuntimeException if key is null
   */
  protected <T> List<T> getOrCreateList(Map<Object, List<T>> map, Object key)
  {
    if (key == null)
    {
      throw new RuntimeException("Cannot build reference out of a scope");
    }
    List<T> result = map.get(key);
    if (result == null)
    {
      result = new LinkedList<T>();
      map.put(key, result);
    }
    return result;
  }

}
