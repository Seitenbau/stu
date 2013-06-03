package com.seitenbau.testing.dbunit.dsl;

public final class ScopeRegistry
{

  private ScopeRegistry()
  {
    throw new IllegalStateException("This class cannot be instantiated");
  }

  public static void use(IScope scope)
  {
    scope.bindRefs();
  }

}
