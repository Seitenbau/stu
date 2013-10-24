package com.seitenbau.stu.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * List of TestCase-ID's implemented by this Test.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestCase
{
  /**
   * get the values for the annotation
   * @return values in String Array
   */
    String[] value();
}
