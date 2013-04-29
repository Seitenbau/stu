package com.seitenbau.testing.rules;

import static com.seitenbau.testing.asserts.fest.Assertions.assertThat;

import java.io.File;
import java.io.InputStream;

import org.junit.Assert;

import com.seitenbau.testing.asserts.fest.impl.ExtendedFileAssert.ProvidesFile;

/**
 * Simple Rule to cleanup files created by your SUT or Test. <br/>
 * If used as Rule, the given file will be removed before the test. If
 * the file does not exist it will not fail. If the file does not
 * exist after your test execution by default this rule will also not
 * fail! Use {@link #FileRule(String, boolean)} when for other
 * behaviours <br/>
 * You can also use this directly in your test to cleanup the file.
 * See {@link #prepareTest()}
 */
public class FileRule extends BeforeAfterRule implements ProvidesFile
{
  protected String fileName;

  protected boolean failIfNotExistsAfterTest;

  protected String compareAfter;

  /**
   * Ensures the given file does not exist before your test. (e.g.
   * will be removed if the file exists) <br/>
   * @param fileName
   */
  public FileRule(String fileName)
  {
    this.fileName = fileName;
    this.failIfNotExistsAfterTest = false;
  }

  /**
   * Ensures the given file does not exist before your test. (e.g.
   * will be removed if the file exists) <br/>
   * 
   * @param fileName
   * @param failIfNotExistsAfterTest if true the rule will fail if the
   *        files does not exist after test execution. Because by
   *        default the rule will not fail!
   */
  public FileRule(String fileName, boolean failIfNotExistsAfterTest)
  {
    this.fileName = fileName;
    this.failIfNotExistsAfterTest = failIfNotExistsAfterTest;
  }

  @Override
  protected void before(ITestMethodDescriptor descriptor) throws Throwable
  {
    prepareTest();
  }

  /**
   * Use this to cleanup possible existing files before your test. If
   * FileRule is used as rule, this is automatically called by JUnit,
   * otherwies call this in your test prepare phase.
   */
  public void prepareTest()
  {
    File f = new File(fileName);
    if (f.exists())
    {
      Assert.assertTrue("Not able to delete file before test : " + fileName, f.delete());
    }
  }

  @Override
  protected void afterSuccess(ITestMethodDescriptor descriptor)
      throws Throwable
  {
    if (this.failIfNotExistsAfterTest)
    {
      File f = new File(fileName);
      Assert.assertTrue(
          "File didn't exist after Tests Execution : " + fileName, f.exists());
    }
    if (compareAfter != null)
    {
      if (compareAfter.startsWith("classpath:"))
      {
        String fn = compareAfter.substring(10);
        InputStream stream = descriptor.getTarget().getClass().getResourceAsStream(fn);
        assertThat(this).hasContent(stream);
      }
      else
      {
        assertThat(this).hasContent(fileName);
      }
    }
  }

  public File getFile()
  {
    return new File(fileName);
  }

  public FileRule checkFileExistsAfterTest()
  {
    failIfNotExistsAfterTest = true;
    return this;
  }

  public FileRule compareAfter(String file)
  {
    compareAfter = file;
    return this;
  }

}
