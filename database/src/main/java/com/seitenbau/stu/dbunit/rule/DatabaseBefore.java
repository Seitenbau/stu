package com.seitenbau.stu.dbunit.rule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
/**
 * Annotate a method do be executed before an dataset got injected. 
 */
public @interface DatabaseBefore {

  String NOT_SET = "-not-set- com.seitenbau.stu.dbunit.rule.DatabaseBefore";

  /**
   * defines an id for the before method, when set this method will
   * only be called when the id was specified in an
   * {@link DatabasePrepare} at the test.
   */
  String id() default NOT_SET;

}
