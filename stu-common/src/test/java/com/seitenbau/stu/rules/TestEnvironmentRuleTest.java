package com.seitenbau.stu.rules;

import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class TestEnvironmentRuleTest
{

  @SuppressWarnings("deprecation")
  @Test
  public void testJustCall() throws Throwable {
      FrameworkMethod method = new FrameworkMethod(getClass().getMethods()[0]);
      Statement base = new Statement() {
          @Override
          public void evaluate() throws Throwable {

          }
      };
      new TestEnvironmentRule().apply(base, method, this).evaluate();
  }

}
