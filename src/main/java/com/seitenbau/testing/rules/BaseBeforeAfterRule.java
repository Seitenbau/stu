package com.seitenbau.testing.rules;

import java.util.List;

import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.seitenbau.testing.expression4j.TestEvents;
import com.seitenbau.testing.expression4j.TestEvents.TestFlowListener;
import com.seitenbau.testing.rules.impl.JUnitTestMethodDescriptor;

/** Internal code, use {@link BeforeAfterRule} instead */
public abstract class BaseBeforeAfterRule implements ParameterAwareRule, TestFlowListener
{
  /**
   * Use {@link BeforeAfterRule} as base class !
   */
  abstract protected void __DO_NOT_InheritFrom_Here_USE_BeforeAfterRule();

  /**
   * The Target Text Class
   */
  protected Class<?> _targetClass;

  /**
   * Entry Point for inject rule around the test Statement. Entry is
   * used for parameterized tests via {@link TestStateRecorder} and
   * {@link SBParameterized} Runner.
   */
  public Statement applyParamTest(Statement base, FrameworkMethod method, Object target,
      int fParameterSetNumber, List<Object[]> fParameterList)
  {
    return createApplyStatement(base, method, fParameterSetNumber, target);
  }

  /**
   * Internal method to create actual Statement to apply.
   * 
   * @param index index of Test class !=
   *        {@code null] when test is parameterized.
   */
  protected Statement createApplyStatement(final Statement base, final FrameworkMethod method,
      final Integer index,
      final Object target)
  {
    return new Statement()
    {
      @Override
      public void evaluate() throws Throwable
      {
        TestEvents.Listeners.add(BaseBeforeAfterRule.this);
        _targetClass = detectTargetClass(target);
        ITestMethodDescriptor descriptor = buildTestMethodDescriptor(method, index, target);
        parameterTestBegin(descriptor, index);
        before(descriptor);
        try
        {
          base.evaluate();
          afterSuccess(descriptor);
        }
        catch (Throwable t)
        {
          if (afterError(descriptor, t))
          {
            throw t;
          }
        }
        finally
        {
          parameterTestEnd(descriptor, index);
          afterAll(descriptor);
          TestEvents.Listeners.remove(BaseBeforeAfterRule.this);
        }
      }

    };
  }

  protected ITestMethodDescriptor buildTestMethodDescriptor(
      final FrameworkMethod method, final Integer index, final Object target)
  {
    return new JUnitTestMethodDescriptor(method, target, index);
  }

  protected Class<?> detectTargetClass(final Object target)
  {
    return target.getClass();
  }

  /**
   * Before method for Parameterized Tests via {@link SBParameterized}
   * runner. Is called before the
   * {@link #before(FrameworkMethod, Object)} eg
   * {@link #before(FrameworkMethod)} method. So only overwrite if you
   * actually need the index.
   * 
   * @param descriptor test methodDescriptor
   * @param index
   * @throws Throwable
   */
  protected void parameterTestBegin(ITestMethodDescriptor descriptor, Integer index)
  {

  }

  /**
   * Template Method, called ALLWAYS after a Test. Mimics the After
   * Test Method.
   * 
   * @param descriptor Instance of the test class.
   * @throws Throwable can throw anything so the test can be marked as
   *         Failed/error by your rule.
   */
  protected void afterAll(ITestMethodDescriptor descriptor) throws Throwable
  {
  }

  /**
   * AfterAll method for Parameterized Tests via
   * {@link SBParameterized} runner. In case you need the current
   * Index. Got called before the {@link #afterAll(FrameworkMethod)}
   * Method, so use only if you need the index.
   * 
   * @param descriptor the test methodDescriptor
   * @param index index of the test run
   * @throws Throwable can throw any error to signalize erros in the
   *         test
   */
  protected void parameterTestEnd(ITestMethodDescriptor descriptor, Integer index)
  {
  }

  /**
   * Got called after a Throwable was thrown by the test. It the
   * method return true ( default ) then the throwable is rethrown.
   * 
   * @param descriptor The Test methodDescriptor
   * @param target Test instance
   * @param occuredError The actual thrown error message
   * @return if {@code true} (is default) the original exception will
   *         be re-thrown. If {@code false} the exception will be
   *         suppressed (Meaning test will be green), or you have to
   *         throw your own Speicalized-Excpetion.
   * @throws Throwable can throw any other error exception instead of
   *         the one actual occured
   */
  protected boolean afterError(ITestMethodDescriptor descriptor, Throwable t) throws Throwable
  {
    return true;
  }

  /**
   * Template method which got called after a test run with no error.
   * 
   * @param descriptor test methodDescriptor
   * @param target test instance
   * @throws Throwable can throw a error to signalize postcondition
   *         errors
   */
  protected void afterSuccess(ITestMethodDescriptor descriptor) throws Throwable
  {
  }

  /**
   * Called before the Test got executed
   * 
   * @param descriptor The test methodDescriptor
   * @throws Throwable can throw any error to signal errors in the
   *         precondition.
   */
  protected void before(ITestMethodDescriptor descriptor) throws Throwable
  {
  }

  /**
   * Helper to signalize the test will be ignored.
   * 
   * @param message
   */
  protected void doIgnoreTest(String message)
  {
    throw new AssumptionViolatedException(message);
  }

  protected Class<?> getTestClass()
  {
    return _targetClass;
  }

  public void onTestFlowEvent(Object event)
  {
    if(event == null) {
      return;
    }
    if (event == TestEvents.EVENT_PREPARE)
    {
      onTestFlowPrepare();
      return;
    }
    if (event == TestEvents.EVENT_EXECUTE)
    {
      onTestFlowExecute();
      return;
    }
    if (event == TestEvents.EVENT_VERIFY)
    {
      onTestFlowVerify();
      return;
    }
    processTestFlowEvent(event);
  }

  /** called when an unkown testflow event occured */
  protected void processTestFlowEvent(Object event) {}

  /**
   * When your tests is using {@link TestEvents} notifications, this
   * methods gets invoked on the {@linkplain TestEvents#EVENT_VERIFY}
   * event.
   */
  protected void onTestFlowVerify()
  {
  }

  /**
   * When your tests is using {@link TestEvents} notifications, this
   * methods gets invoked on the {@linkplain TestEvents#EVENT_EXECUTE}
   * event.
   */
  protected void onTestFlowExecute()
  {
  }

  /**
   * When your tests is using {@link TestEvents} notifications, this
   * methods gets invoked on the {@linkplain TestEvents#EVENT_PREPARE}
   * event.
   */
  protected void onTestFlowPrepare()
  {
  }

}
