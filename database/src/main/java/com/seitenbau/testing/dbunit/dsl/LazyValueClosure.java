package com.seitenbau.testing.dbunit.dsl;

import groovy.lang.Closure;

public class LazyValueClosure implements LazyValue
{
  
  private final Closure<?> closure;
  
  public LazyValueClosure(Closure<?> closure)
  {
    this.closure = closure;
  }

  public Object getValue()
  {
    return closure.call();
  }
  
}
