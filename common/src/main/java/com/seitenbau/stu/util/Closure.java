package com.seitenbau.stu.util;

/**
 * Simple anonymous Class for one-Argument methods
 */
public interface Closure<OUT_TYPE, IN_TYPE, EX extends Throwable>
{
  /**
   * Simple method with on argument
   */
  OUT_TYPE call(IN_TYPE input) throws EX;
}
