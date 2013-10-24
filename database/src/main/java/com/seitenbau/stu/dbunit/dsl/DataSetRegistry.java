package com.seitenbau.stu.dbunit.dsl;

import java.util.HashMap;
import java.util.Map;

public final class DataSetRegistry
{

  private final static Map<String, ThreadLocal<DataSetIdentificator>> activeScopes = new HashMap<String, ThreadLocal<DataSetIdentificator>>();

  private DataSetRegistry()
  {
    throw new IllegalStateException("This class cannot be instantiated");
  }

  /**
   * Sets the current scope for DatabaseRef instances. Only References
   * belonging to the scope one's will be affected.
   * @param identificator The scope to set
   * @return The last active scope
   */
  public static DataSetIdentificator use(DataSetIdentificator identificator)
  {
    if (identificator == null) {
      return null;
    }

    ThreadLocal<DataSetIdentificator> activeScope = activeScopes.get(identificator.getDataSetClassName());
    if (activeScope == null) {
      activeScope = new ThreadLocal<DataSetIdentificator>();
      activeScopes.put(identificator.getDataSetClassName(), activeScope);
    }
    DataSetIdentificator result = activeScope.get();
    activeScope.set(identificator);
    return result;
  }

  public static DataSetIdentificator getCurrentDataSet(String name)
  {
    if (activeScopes.containsKey(name)) {
      return activeScopes.get(name).get();
    }
    return null;
  }
}
