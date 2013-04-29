package com.seitenbau.testing.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.seitenbau.testing.dsl.requirement.RequirementDsl;
import com.seitenbau.testing.rules.RequiresRule;

/**
 * Used in conjunction with {@link RequiresRule} to ignore tests while features
 * are not completed.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Requires {

    /**
     * A {@link RequirementDsl} compatible String.
     */
    String[] value();

}
