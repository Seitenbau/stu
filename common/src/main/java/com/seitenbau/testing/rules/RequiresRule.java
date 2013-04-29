package com.seitenbau.testing.rules;

import java.lang.reflect.Method;

import com.seitenbau.testing.annotation.Requires;
import com.seitenbau.testing.dsl.requirement.RequirementDsl;
import com.seitenbau.testing.dsl.tickets.TicketDsl;

/**
 * Processes the {@link Requires} annotation. This can ignore tests
 * when certain features not set. <br/>
 * Annotation can contain {@link RequirementDsl} or a
 * {@link TicketDsl}. Only fixed tickets will allow TestExecution.
 */
public class RequiresRule extends BeforeAfterRule
{

  boolean _debug;

  /**
   * Activate extended debug outputs
   * 
   * @return just this
   */
  public RequiresRule debug()
  {
    _debug = true;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected void before(ITestMethodDescriptor descriptor) throws Throwable
  {
    Requires anno = descriptor.getAnnotation(Requires.class, true);
    String why = isMethodToIgnore(anno);
    if (why != null)
    {
      doIgnoreTest(why);
    }
  }

  /**
   * Check if the given Methods requires an environment
   * @param method the method to check
   * @return null if method can run in current Environment, otherwise.
   *         Message why the method cannot be executed.
   */
  public static String isMethodToIgnore(Method method)
  {
    return isMethodToIgnore(method.getAnnotation(Requires.class));
  }

  /**
   * Check if the annotation meets the requirements.
   * 
   * @param anno the annotation to check
   * @return null if method can run in current Environment, otherwise.
   *         Message why the method cannot be executed.
   */
  public static String isMethodToIgnore(Requires anno)
  {
    if (anno == null)
    {
      return null;
    }
    return isMethodToIgnore(anno.value());
  }

  /**
   * Check if the string dsl meet the requirements.
   * 
   * @param activeOnFeature the RequirementDSL Strings to check
   * @return null if method can run in current Environment, otherwise.
   *         Message why the method cannot be executed.
   */
  public static String isMethodToIgnore(String... activeOnFeature)
  {
    if (activeOnFeature == null)
    {
      return null;
    }
    for (String fString : activeOnFeature)
    {
      if (RequirementDsl.isRequirement(fString))
      {
        RequirementDsl feature = new RequirementDsl(fString);
        if (feature.isNotAvaiable())
        {
          String msg = "Test ignored because feature '"
              + feature.getFeatureId()
              + "' is not active.";
          return msg;
        }
      }
      else if (TicketDsl.isTicketDSL(fString))
      {
        TicketDsl ticket = new TicketDsl(fString);
        if (!ticket.isSolved())
        {
          String msg = "Test ignored because ticket '" + ticket.getId()
              + "' is not solved. Is " + ticket.getState();
          return msg;
        }
      }
    }
    return null;
  }

}
