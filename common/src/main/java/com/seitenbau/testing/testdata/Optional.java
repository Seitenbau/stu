package com.seitenbau.testing.testdata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * marks a test data element as optional. 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Optional {
}
