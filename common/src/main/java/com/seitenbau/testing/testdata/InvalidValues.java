package com.seitenbau.testing.testdata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InvalidValues {
  String[] value() default {};
  Value[] values() default {};
}
