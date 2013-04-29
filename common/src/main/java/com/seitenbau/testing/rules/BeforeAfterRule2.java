package com.seitenbau.testing.rules;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Internal code, use {@link BeforeAfterRule} instead 
 * <br/>
 * BeforeAfterRule for JUnit 4.5 - 4.10
 */
@Deprecated
// Internal code, use {@link BeforeAfterRule} instead
public abstract class BeforeAfterRule2 extends BaseBeforeAfterRule implements
    MethodRule
{
  /**
   * Entry Point for inject rule around the test Statement.
   */
  final public Statement apply(final Statement base,
      final FrameworkMethod method, Object target)
  {
    return createApplyStatement(base, method, null, target);
  }

  /**
   * Use {@link BeforeAfterRule} as base class !
   */
  abstract protected void __DO_NOT_InheritFrom_Here_USE_BeforeAfterRule();
}
