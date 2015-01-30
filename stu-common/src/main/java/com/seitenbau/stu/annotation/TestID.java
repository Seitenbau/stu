package com.seitenbau.stu.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Test identifier
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestID
{
    String value();
}

