package com.seitenbau.stu.database.javadoc;

import groovy.util.JavadocAssertionTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JavaDocCodeExamplesTest extends TestCase
{

  public static Test suite()
  {
    TestSuite testSuite = new TestSuite();
    testSuite.addTest(JavadocAssertionTestSuite.suite("src/main/java"));
    return testSuite;
  }

}
