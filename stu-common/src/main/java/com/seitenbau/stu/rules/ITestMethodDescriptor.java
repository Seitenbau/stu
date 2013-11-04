package com.seitenbau.stu.rules;

import java.lang.annotation.Annotation;

/**
 * ParameterObject for {@linkplain BeforeAfterRule} implementations.
 * </br> This ParameterObject provides access to parameters needed in
 * your JUnit Rules.
 */
public interface ITestMethodDescriptor
{
  /**
   * Returns the current test method name.
   * 
   * @return The method name itself, not the FQN!
   */
  String getMethodName();

  /**
   * Returns the current test class name
   * 
   * @return The class name itself, not the FQN!
   */
  String getClassName();

  /**
   * Returns the current Test Class Canonical Name (FQN)
   * 
   * @return The FQN of the TestClass
   */
  String getClassCanoncialName();

  /**
   * Find a Annotation at the Test-Method or in the hierarchy.
   * 
   * @param annotationClass The Annotations Type to search for
   * @param searchHierachy If {@code true} the Annotation can also be
   *        at the Class level.
   * @return The found Annotation instance of {@code null}
   */
  <T extends Annotation> T getAnnotation(Class<T> annotationClass,
      boolean searchHierachy);

  /**
   * If the current Testrunner is parameterized,
   * (runner=SBParameterized.class) then this will be the current
   * Index of the Test.
   * 
   * @return The Index or {@code null}
   */
  Integer getIndex();

  /**
   * Returns the current test instance.
   * 
   * @return The test instance or {@code null} when this is a
   *         classrule.
   */
  Object getTarget();

}
