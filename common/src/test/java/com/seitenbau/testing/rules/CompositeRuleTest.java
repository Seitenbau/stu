package com.seitenbau.testing.rules;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Stack;

import org.fest.assertions.Fail;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class CompositeRuleTest
{
  boolean _base;

  Stack<Integer> _stack = new Stack<Integer>();

  Statement base = new Statement()
  {

    @Override
    public void evaluate() throws Throwable
    {
      _base = true;
    }
  };

  @Test
  public void testEmpty() throws Throwable
  {
    CompositeRule sut = new CompositeRule();
    sut.apply(base, null, this).evaluate();
    assertThat(_base).isTrue();
  }

  @Test
  public void testOrderOfExecution() throws Throwable
  {
    // prepare
    CompositeRule sut = new CompositeRule();
    sut.add(createRule(1));
    sut.add(createRule(2));
    sut.add(createRule(3));

    // exeucte
    sut.apply(base, null, this).evaluate();

    // verify
    assertThat(_base).isTrue();
    assertThat(_stack).containsExactly(1, 2, 3);
  }

  @Test
  public void testStopExecutionOnError() throws Throwable
  {
    // prepare
    CompositeRule sut = new CompositeRule();
    sut.add(createRule(1));
    sut.add(createRule(2, new IllegalArgumentException()));
    sut.add(createRule(3));

    // exeucte
    try
    {
      sut.apply(base, null, this).evaluate();
      Fail.fail();
    }
    catch (IllegalArgumentException e)
    {
    }

    // verify
    assertThat(_base).isFalse();
    assertThat(_stack).containsExactly(1, 2);
  }

  private MethodRule createRule(int id)
  {
    return createRule(id, null);
  }

  private MethodRule createRule(final int id, final Throwable error)
  {
    return new MethodRule()
    {
      public Statement apply(final Statement base, FrameworkMethod method,
          Object target)
      {
        return new Statement()
        {
          @Override
          public void evaluate() throws Throwable
          {
            _stack.push(id);
            if (error == null)
            {
              base.evaluate();
            }
            else
            {
              throw error;
            }
          }
        };

      }
    };
  }
}
