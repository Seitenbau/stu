package com.seitenbau.stu.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * List of Test-ID's of a Test
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestRequiresEnvironment
{
    String[] value();
}

