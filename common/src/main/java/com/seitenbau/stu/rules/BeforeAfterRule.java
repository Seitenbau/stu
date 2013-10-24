package com.seitenbau.stu.rules;


/**
 * Helper class for JUnit Rules. This basically provides some
 * convenient methods like {@link #before(ITestMethodDescriptor)} or after* you can overwrite.
 */
public class BeforeAfterRule extends BeforeAfterRule2
{

  /** Yes you did it right ! */
  @Override
  protected void __DO_NOT_InheritFrom_Here_USE_BeforeAfterRule()
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
   * Got called after a Throwable was thrown by the test. It the
   * method return true ( default ) then the throwable is rethrown.
   * 
   * @param descriptor The Test methodDescriptor
   * @param target Test instance
   * @param occuredError The actual thrown error message
   * @return if {@code true} (is default) the original exception will
   *         be re-thrown. If {@code false} the exception will be
   *         suppressed (Meaning test will be green), or you have to
   *         throw your own Specialized-Exception.
   * @throws Throwable can throw any other error exception instead of
   *         the one actual occurred
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

}
