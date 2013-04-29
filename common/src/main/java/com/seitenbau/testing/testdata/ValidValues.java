package com.seitenbau.testing.testdata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ValidValues {
  String[] value() default {};
}
