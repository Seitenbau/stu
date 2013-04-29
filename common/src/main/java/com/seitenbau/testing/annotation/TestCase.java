package com.seitenbau.testing.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * List of TestCase-ID's implemented by this Test.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestCase
{
    String[] value();
}
