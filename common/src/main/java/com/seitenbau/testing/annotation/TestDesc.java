package com.seitenbau.testing.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description for this Testcase.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestDesc
{

    String[] value();

}
