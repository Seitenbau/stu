package com.seitenbau.testing.testdata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TextValue {
  
  Cardinality cardinality() default Cardinality.OneToOne;
  
  int maxLength() default 100;

}
