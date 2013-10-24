package com.seitenbau.stu.fest;

import org.fest.assertions.Assert;

import com.seitenbau.stu.util.Future;

public class AssertableBase extends Assert
{
  protected void failIfTrue(boolean value)
  {
    if (value)
    {
      fail("expected false but was " + value);
    }
  }

  protected void failIfTrue(Future<String> message, boolean value)
  {
    if (value)
    {
      fail(message.getFuture());
    }
  }

  protected void failIfTrue(String message, boolean value)
  {
    failIfTrue(LazyResultAdapter.forValue(message), value);
  }

  protected void failIfFalse(boolean value)
  {
    if (!value)
    {
      fail("expected true but was " + value);
    }
  }

  protected void failIfFalse(Future<String> message, boolean value)
  {
    if (!value)
    {
      fail(message.getFuture());
    }
  }

  protected void failIfFalse(String message, boolean value)
  {
    failIfFalse(LazyResultAdapter.forValue(message), value);
  }

  protected void failIfNotEquals(String message, Object actual, Object expected)
  {
    if (!actual.equals(expected))
    {
      fail(message + " actual:'" + actual + "' expected:'" + expected + "'");
    }
  }

  protected void failIfNotEquals(Object actual, Object expected)
  {
    failIfNotEquals("", actual, expected);
  }
}
