package com.seitenbau.testing.testdata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TestDataFilters {
  Class<? extends Filter>[] value();
}