package com.seitenbau.stu.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.seitenbau.stu.dsl.requirement.RequirementDsl;
import com.seitenbau.stu.rules.RequiresRule;

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
