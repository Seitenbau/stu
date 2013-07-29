package com.seitenbau.testing.expression4j;

import org.junit.Test;
import static com.seitenbau.testing.asserts.fest.Assertions.*;

public class TestExpressionsTest
{
  Mock object = new Mock();

  boolean _action;

  boolean _expect;

  boolean _assert;

  @Test
  public void testAction()
  {
    // prepare
    assertThat(_action).isFalse();
    assertThat(_expect).isFalse();
    assertThat(_assert).isFalse();

    // execute
    TestExpressions.action(object).doAction();

    // verify
    assertThat(_action).isTrue();
    assertThat(_expect).isFalse();
    assertThat(_assert).isFalse();
  }

  @Test
  public void testVerify()
  {
    // prepare
    assertThat(_action).isFalse();
    assertThat(_expect).isFalse();
    assertThat(_assert).isFalse();

    // execute
    TestExpressions.verify(object).doAssert();

    // verify
    assertThat(_action).isFalse();
    assertThat(_expect).isFalse();
    assertThat(_assert).isTrue();
  }

  class MockAction
  {
    public void doAction()
    {
      _action = true;
    }
  }

  class MockAssert
  {
    public void doAssert()
    {
      _assert = true;
    }
  }

  class MockExpect
  {
    public void doExpect()
    {
      _expect = true;
    }
  }

  class Mock
      implements
      Actionable<MockAction>,
      Assertable<MockAssert>,
      Expectable<MockExpect>
  {
    public MockAction getActionBuilder()
    {
      return new MockAction();
    }

    public MockExpect getExpectBuilder()
    {
      return new MockExpect();
    }

    public MockAssert getAssertBuilder()
    {
      return new MockAssert();
    }

  }
}
