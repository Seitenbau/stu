package com.seitenbau.stu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the (manual) test steps.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ManualTest
{
  /**
   * (Optional) defines if some prerequisites are required to allow for the test to be executed
   */
  String prerequisite() default "";

  /**
   * (Required) the actual test steps
   */
  TestStep[] steps();
}
