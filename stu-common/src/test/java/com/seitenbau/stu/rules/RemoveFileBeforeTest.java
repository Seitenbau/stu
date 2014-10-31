package com.seitenbau.stu.rules;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RemoveFileBeforeTest
{

  @SuppressWarnings("deprecation")
  @Test
  public void testJustCall() throws Throwable {
      FrameworkMethod method = new FrameworkMethod(getClass().getMethods()[0]);
      Statement base = new Statement() {
          @Override
          public void evaluate() throws Throwable {

          }
      };
     
      String tmpPath = System.getProperty("java.io.tmpdir");
      String filename = tmpPath + "tmpFileName";
      File testFile = new File(filename);
      assertEquals(testFile.exists(), false);
      new RemoveFileBefore(filename).apply(base, method, this).evaluate();
      assertEquals(testFile.exists(), false);
  }
  
  @SuppressWarnings("deprecation")
  @Test(expected = AssertionError.class)
  public void testJustCall_failIfNotExistsAfterTest() throws Throwable {
    FrameworkMethod method = new FrameworkMethod(getClass().getMethods()[0]);
    Statement base = new Statement() {
      @Override
      public void evaluate() throws Throwable {
        
      }
    };
    
    String tmpPath = System.getProperty("java.io.tmpdir");
    String filename = tmpPath + "tmpFileName";
    File testFile = new File(filename);
    assertEquals(testFile.exists(), false);
    new RemoveFileBefore(filename, true).apply(base, method, this).evaluate();

  }

}
