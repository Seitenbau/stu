package com.seitenbau.testing.rules;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class DelegatingRule implements MethodRule
{
  MethodRule delegate;
  
  public DelegatingRule(MethodRule delegateTo) {
    delegate = delegateTo;
  }

  public Statement apply(Statement base, FrameworkMethod method, Object target)
  {
    return delegate.apply(base,method,target);
  }

}
