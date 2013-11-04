package com.seitenbau.stu.database.rule;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marker to inject the actual Dataset into this field.
 * 
 * Used by DatabaseTesterRule. Either when replay a default Dataset.
 * or by a manual call of cleanInsert()
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectDataSet {

}
