package com.seitenbau.stu.rules;

import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.seitenbau.stu.annotation.TestIssue;

public class RecordAnnotationRuleTest
{

  @SuppressWarnings("deprecation")
  @Test
  public void testJustCall() throws Throwable
  {
    FrameworkMethod method = new FrameworkMethod(getClass().getMethods()[0]);
    Statement base = new Statement() {
        @Override
        public void evaluate() throws Throwable {

        }
    };
    new RecordAnnotationRule<TestIssue>(TestIssue.class).apply(base, method, this).evaluate();
  }
  
  @SuppressWarnings("deprecation")
  @Test
  public void testJustCallOtherConstructor() throws Throwable
  {
    FrameworkMethod method = new FrameworkMethod(getClass().getMethods()[0]);
    Statement base = new Statement() {
      @Override
      public void evaluate() throws Throwable {
        
      }
    };
    new RecordAnnotationRule<TestIssue>(TestIssue.class, true).apply(base, method, this).evaluate();
  }

}


