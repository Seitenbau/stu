package com.seitenbau.testing.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * List of Issue ID's implemented caused, and hopefully fixed for this
 * Test.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestIssue
{

    String[] value();

    boolean fixed = false;
}
