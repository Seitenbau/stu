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

  public static void use(IScope scope)
  {
    ThreadLocal<IScope> activeScope = activeScopes.get(scope.getScopeIdentifier());
    if (activeScope == null) {
      activeScope = new ThreadLocal<IScope>();
      activeScopes.put(scope.getScopeIdentifier(), activeScope);
    }
    activeScope.set(scope);
  }

  public static IScope getCurrentScope(String name)
  {
    if (activeScopes.containsKey(name)) {
      return activeScopes.get(name).get();
    }
    return null;
  }
}
