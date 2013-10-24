package com.seitenbau.stu.expression4j;

/**
 * Autocompletion helper to filter test methods.  
 */
public abstract class TestExpressions
{
  /**
   * executes a verify action on the given ( similar to assertThat )
   */
  public static <T extends Assertable<A>, A extends Object> A verify(
      T assertAbleObj)
  {
    return assertAbleObj.getAssertBuilder();
  }

  /**
   * executes an on the given ( similar to assertThat )
   */
  public static <T extends Actionable<A>, A extends Object> A action(
      T assertAbleObj)
  {
    return assertAbleObj.getActionBuilder();
  }
  
}
