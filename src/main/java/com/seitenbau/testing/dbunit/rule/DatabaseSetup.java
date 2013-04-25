package com.seitenbau.testing.dbunit.rule;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.seitenbau.testing.dbunit.extend.DbUnitDatasetFactory;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseSetup {

  final static String NOT_SET =
      "......... Insert default database xml file ..........";

  /**
   * Specify alternate Dataset.xml file to clean Insert before Test.
   */
  String prepareDS() default NOT_SET;
  
  /**
   * Specify alternate dataset to clean Insert before Test.
   */
  Class<? extends DbUnitDatasetFactory>[] prepare() default DbUnitDatasetFactory.class;

  /**
   * If set to true, the Databast will not be cleanInserted before the
   * Test Default:false
   */
  boolean suppressInsert() default false;

  /**
   * If set to true, the Database ist Verified after the Test to have
   * no modifications. Default:false
   */
  boolean assertNoModification() default false;
  
}
