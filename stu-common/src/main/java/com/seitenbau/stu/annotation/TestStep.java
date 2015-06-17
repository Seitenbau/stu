package com.seitenbau.stu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describes a test steps.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface TestStep
{
  /**
   * (Required) the step description
   */
  String action();

  /**
   * (Optional) the expected result for the executed test step
   */
  String result() default "";

}
