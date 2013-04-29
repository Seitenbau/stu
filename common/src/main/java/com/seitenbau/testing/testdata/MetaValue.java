package com.seitenbau.testing.testdata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MetaValue {
  
  String type();
  
  String value();
  
}
