package com.seitenbau.stu.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Title for this Testcase.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestTitle
{
    String value();
}
