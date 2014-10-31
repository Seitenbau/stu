package com.seitenbau.stu.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface StoredProperty {

  /**
   * Magic object Identity used to identify not set configuration values.
   * If you see this in your code, you probably have a missing key in
   * your test configuration .property file.
   */
  static final String NOT_SET_VALUE =
      "MAGIC MARKER STRING, see JavaDoc at: com.seitenbau.stu.config.StoredProperty -> 'NOT_SET_VALUE for details.";

  String key();

  String defaultValue() default NOT_SET_VALUE;

}