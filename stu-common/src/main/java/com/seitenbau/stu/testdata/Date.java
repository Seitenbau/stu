package com.seitenbau.stu.testdata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Date {

  @Retention(RetentionPolicy.RUNTIME)
  public @interface Day {
  }

  @Retention(RetentionPolicy.RUNTIME)
  public @interface Month {
  }

  @Retention(RetentionPolicy.RUNTIME)
  public @interface Year {
  }

}
