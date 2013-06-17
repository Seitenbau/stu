package com.seitenbau.testing.rules;


import java.util.ArrayList;
import java.util.List;

import org.junit.rules.MethodRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Composite your rules. For specifying the order of the rule Execution.
 */
public class CompositeRule implements MethodRule
{
  private final List<MethodRule> _rules = new ArrayList<MethodRule>();

  /**
   * Add a rule to get executed in order
   */
  public CompositeRule add(MethodRule rule)
  {
    _rules.add(0,rule);
    return this;
  }

  @Override
  public Statement apply(Statement base, FrameworkMethod method, Object target)
  {
    Statement statement = base;
    for (MethodRule rule : _rules)
    {
      statement = create(rule,statement,method,target);
    }
    return statement;
  }

  private Statement create(MethodRule rule,Statement base, FrameworkMethod method,
      Object target)
  {
    final Statement thisRule = rule.apply(base, method, target);
    return thisRule;
  }

  /**
   * Add a rule to get executed in order.</br>
   * Workaround for : https://github.com/KentBeck/junit/issues/383
   */
  public CompositeRule add(final org.junit.rules.TestRule rule)
  {
    return add(new MethodRule() {
      @Override
      public Statement apply(Statement base, FrameworkMethod method, Object target)
      {
        return rule.apply(base, Description.EMPTY);
      }
    });
  }

}
