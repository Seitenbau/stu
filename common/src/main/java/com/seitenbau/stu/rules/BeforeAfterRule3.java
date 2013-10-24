package com.seitenbau.stu.rules;

import java.lang.reflect.Field;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptor2;

/**
 * Internal code, use {@link BeforeAfterRule} instead 
 * <br/>
 * Future Replacement for BeforeAfterRule class and junit after junit
 * 4.10
 */
@Deprecated
// Internal code, use {@link BeforeAfterRule} instead
public abstract class BeforeAfterRule3 extends BaseBeforeAfterRule implements
    TestRule
{
  /**
   * Use {@link BeforeAfterRule} as base class !
   */
  abstract protected void __DO_NOT_InheritFrom_Here_USE_BeforeAfterRule();

  Object _target;

  Description _description;

  public BeforeAfterRule3(Object testInstance)
  {
    _target = testInstance;
  }

  /**
   * Entry Point for inject rule around the test Statement.
   */
  public Statement apply(Statement base, Description description)
  {
    _description = description;
    return createApplyStatement(base, null, null, _target);
  }

  protected ITestMethodDescriptor buildTestMethodDescriptor(
      FrameworkMethod method, Integer index, Object target)
  {
    return new JUnitTestMethodDescriptor2(index, _target, _description);
  }

  protected <T> T getPrivateField(Statement base, String fieldName)
  {
    try
    {
      Field f = base.getClass().getDeclaredField(fieldName);
      f.setAccessible(true);
      return (T) f.get(base);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  protected static class Wrapper extends Statement implements
      IStatementWithInstance
  {
    Statement _delegate;

    FrameworkMethod _method;

    Object _target;

    public Wrapper(Statement delegate, FrameworkMethod method, Object target)
    {
      _delegate = delegate;
      _method = method;
      _target = target;
    }

    public Object getTargetInstance()
    {
      return _target;
    }

    public FrameworkMethod getFrameworkMethod()
    {
      return _method;
    }

    @Override
    public void evaluate() throws Throwable
    {
      _delegate.evaluate();
    }

  }
}
