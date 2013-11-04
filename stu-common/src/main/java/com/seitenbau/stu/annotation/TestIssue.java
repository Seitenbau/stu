package com.seitenbau.stu.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * List of Issue ID's implemented caused, and hopfully Fixted for this
 * Test.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestIssue
{

    String[] value();

    boolean fixed = false;
}
