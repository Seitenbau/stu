package com.seitenbau.testing.dbunit.solr;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.seitenbau.testing.dbunit.extend.DbUnitDatasetFactory;

/**
 * Annotation, um das Setup für das Einspielen und Testen der Solr-Testdaten
 * festzulegen.
 * 
 * @author rnoerenberg
 * @version $Id: SolrSetup.java 97135 2012-11-28 15:10:17Z rnoerenberg $
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface SolrSetup
{
  /**
   * Specify alternate dataset to clean Insert before Test.
   */
  Class<? extends DbUnitDatasetFactory> prepare() 
      default DbUnitDatasetFactory.class;

  /**
   * If set to true, the Database will not be cleanInserted before the
   * Test Default:false
   */
  boolean suppressInsert() default false;

  /**
   * If set to true, the Database ist verified after the Test to have
   * no modifications. Default:false
   */
  boolean assertNoModification() default false;
}
