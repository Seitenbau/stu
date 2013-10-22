package com.seitenbau.testing.util;

import static org.fest.assertions.Assertions.*;

import org.junit.Test;

public class ClosureTest
{
  private Object cap_inObject;

  @Test
  public void parameterPassing() throws Exception
  {
    final Object resultObject = new Object();
    Closure<Object, Object, Exception> c = new Closure<Object, Object, Exception>()
    {
      public Object call(Object input) throws Exception
      {
        cap_inObject = input;
        return resultObject;
      }
    };

    Object inObj = new Object();
    // execute
    Object result = c.call(inObj);

    // verify
    assertThat(result).isSameAs(resultObject);
    assertThat(cap_inObject).isSameAs(inObj);
  }
}
