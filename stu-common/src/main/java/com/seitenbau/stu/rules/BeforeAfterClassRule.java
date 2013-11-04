package com.seitenbau.stu.rules;

/**
 * Future base class for JUnit ClassRules. 
 */
public class BeforeAfterClassRule extends BeforeAfterRule3
{
  public BeforeAfterClassRule()
  {
    super(null);
  }

  @Override
  protected void before(ITestMethodDescriptor descriptor) throws Throwable
  {
    if (descriptor.getTarget() != null)
    {
      throw new RuntimeException(
          "This is a class rule! User @ClassRule instead of @Rule");
    }
    beforeClass(descriptor);
  }

  protected void beforeClass(ITestMethodDescriptor descriptor)  throws Throwable
  {
  }

  protected boolean afterError(ITestMethodDescriptor descriptor, Throwable t)
      throws Throwable
  {
    return afterClassError(descriptor, t);
  }

  @Override
  protected void afterSuccess(ITestMethodDescriptor descriptor)
      throws Throwable
  {
    afterClassSuccess(descriptor);
  }

  @Override
  protected void afterAll(ITestMethodDescriptor descriptor) throws Throwable
  {
    afterClassFinal(descriptor);
  }

  protected void afterClassFinal(ITestMethodDescriptor descriptor)
      throws Throwable
  {

  }

  protected void afterClassSuccess(ITestMethodDescriptor descriptor)
      throws Throwable
  {
  }

  protected boolean afterClassError(ITestMethodDescriptor descriptor,
      Throwable t) throws Throwable
  {
    return true;
  }

  /** Yep you did it right */
  @Override
  protected void __DO_NOT_InheritFrom_Here_USE_BeforeAfterRule()
  {  
  }
}
