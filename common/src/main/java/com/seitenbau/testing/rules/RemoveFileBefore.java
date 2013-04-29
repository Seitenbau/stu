package com.seitenbau.testing.rules;

import java.io.File;

import org.junit.Assert;

/** @deprecated use FileRule */
@Deprecated // Use FileRule
public class RemoveFileBefore extends BeforeAfterRule
{
  private String fileName;

  private boolean failIfNotExistsAfterTest;

  public RemoveFileBefore(String fileName)
  {
    this.fileName = fileName;
    this.failIfNotExistsAfterTest = false;
  }

  public RemoveFileBefore(String fileName, boolean failIfNotExistsAfterTest)
  {
    this.fileName = fileName;
    this.failIfNotExistsAfterTest = failIfNotExistsAfterTest;
  }

  @Override
  protected void before(ITestMethodDescriptor descriptor) throws Throwable {
    File f = new File(fileName);
    if (f.exists())
    {
      f.delete();
    }
  }

  @Override
  protected void afterSuccess(ITestMethodDescriptor descriptor)
            throws Throwable {
    if (this.failIfNotExistsAfterTest)
    {
      File f = new File(fileName);
      Assert.assertTrue(
          "File didn't exist after Tests Execution : " + fileName, f.exists());
    }
  }
}
