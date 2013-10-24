package com.seitenbau.stu.dbunit.dsl;

import groovy.lang.Closure;

import com.seitenbau.stu.util.Future;

/**
 * Wrapper for a Closure into a Future value. Allows to call the
 * Closure as a lazy evaluated value.
 */
public class FutureClosure implements Future<Object>
{

  private final Closure<?> closure;

  public FutureClosure(Closure<?> closure)
  {
    this.closure = closure;
  }

  @Override
  public Object getFuture()
  {
    return closure.call();
  }

}
