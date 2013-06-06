package com.seitenbau.testing.dbunit.dsl;

import java.util.HashMap;
import java.util.Map;

public final class ScopeRegistry
{

  private final static Map<String, ThreadLocal<IScope>> activeScopes = new HashMap<String, ThreadLocal<IScope>>();
  
  private ScopeRegistry()
  {
    throw new IllegalStateException("This class cannot be instantiated");
  }

  /**
   * Sets the current scope for DatabaseReference instances. Only References
   * beloning to the scope's  will be affected.
   * @param scope The scope to set
   * @return The last active scope
   */
  public static IScope use(IScope scope)
  {
    if (scope == null) {
      return null;
    }
    
    ThreadLocal<IScope> activeScope = activeScopes.get(scope.getScopeIdentifier());
    if (activeScope == null) {
      activeScope = new ThreadLocal<IScope>();
      activeScopes.put(scope.getScopeIdentifier(), activeScope);
    }
    IScope result = activeScope.get();
    activeScope.set(scope);
    return result;
  }

  public static IScope getCurrentScope(String name)
  {
    if (activeScopes.containsKey(name)) {
      return activeScopes.get(name).get();
    }
    return null;
  }
}
