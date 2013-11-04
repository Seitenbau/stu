package com.seitenbau.stu.database.rule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface DatabasePrepare {

  String NOT_SET = "-not-set- com.seitenbau.stu.database.rule.DatabaseBefore";

  String value() default NOT_SET;

}
