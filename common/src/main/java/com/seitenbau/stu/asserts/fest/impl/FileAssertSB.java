package com.seitenbau.stu.asserts.fest.impl;

import static com.seitenbau.stu.asserts.fest.Assertions.assertThat;
import static org.fest.assertions.Formatting.format;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Arrays.isEmpty;
import static org.fest.util.Systems.LINE_SEPARATOR;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.fest.assertions.Assertions;
import org.fest.assertions.FileAssert;
import org.junit.ComparisonFailure;

import com.seitenbau.stu.asserts.fest.impl.FileContentComparator.LineDiff;
import com.seitenbau.stu.util.ReflectionUtils;

/**
 * <p>
 * Copy Of FileAssert from FEST, because some inner classes were
 * private ...
 * <p>
 * Assertions for <code>{@link File}</code>.
 * <p>
 * To create a new instance of this class invoke
 * <code>{@link Assertions#assertThat(File)}</code>.
 * </p>
 * 
 * @author David DIDIER
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FileAssertSB extends FileAssert
{
  String separator = System.getProperty("file.separator");

  private final FileContentComparator comparator;
  
  /**
   * Creates a new <code>FileAssert</code>.
   * @param actual the target to verify.
   */
  public FileAssertSB(File actual)
  {
    super(actual);
    comparator = new FileContentComparator();
  }

  /**
   * Creates a new <code>FileAssert</code>.
   * @param actual the target to verify.
   */
  public FileAssertSB(ProvidesFile actual)
  {
    super(actual.getFile());
    comparator = new FileContentComparator();
  }

  /**
   * Verifies that the actual {@code File} does not exist.
   * @return this assertion object.
   * @throws AssertionError if the the actual {@code File} is
   *         {@code null}.
   * @throws AssertionError if the actual {@code File} exists.
   */
  public FileAssertSB doesNotExist()
  {
    isNotNull();
    if (!actual.exists())
      return this;
    failIfCustomMessageIsSet();
    throw failure(format("file:<%s> should not exist", actual));
  }

  /**
   * Verifies that the actual {@code File} does exist.
   * @return this assertion object.
   * @throws AssertionError if the the actual {@code File} is
   *         {@code null}.
   * @throws AssertionError if the actual {@code File} does not exist.
   */
  public FileAssertSB exists()
  {
    isNotNull();
    assertExists(actual);
    return this;
  }

  /**
   * Verifies that the size of the actual {@code File} is equal to the
   * given one.
   * @param expected the expected size of the actual {@code File}.
   * @return this assertion object.
   * @throws AssertionError if the the actual {@code File} is
   *         {@code null}.
   * @throws AssertionError if the size of the actual {@code File} is
   *         not equal to the given one.
   */
  public FileAssertSB hasSize(long expected)
  {
    isNotNull();
    long size = actual.length();
    if (size == expected)
      return this;
    failIfCustomMessageIsSet();
    throw failure(format("size of file:<%s> expected:<%s> but was:<%s>", actual, expected, size));
  }

  /**
   * Verifies that the actual {@code File} is a directory.
   * @return this assertion object.
   * @throws AssertionError if the the actual {@code File} is
   *         {@code null}.
   * @throws AssertionError if the actual {@code File} is not a
   *         directory.
   */
  public FileAssertSB isDirectory()
  {
    isNotNull();
    if (actual.isDirectory())
      return this;
    failIfCustomMessageIsSet();
    throw failure(format("file:<%s> should be a directory", actual));
  }

  /**
   * Verifies that the actual {@code File} is a regular file.
   * @return this assertion object.
   * @throws AssertionError if the the actual {@code File} is
   *         {@code null}.
   * @throws AssertionError if the actual {@code File} is not a
   *         regular file.
   */
  public FileAssertSB isFile()
  {
    isNotNull();
    if (actual.isFile())
      return this;
    failIfCustomMessageIsSet();
    throw failure(format("file:<%s> should be a file", actual));
  }

  /**
   * Verifies that the content of the actual {@code File} is equal to
   * the content of the given one. Adapted from <a href=
   * "http://junit-addons.sourceforge.net/junitx/framework/FileAssert.html"
   * target="_blank">FileAssert</a> (from <a
   * href="http://sourceforge.net/projects/junit-addons"
   * >JUnit-addons</a>.)
   * @param expected the given {@code File} to compare the actual
   *        {@code File} to.
   * @return this assertion object.
   * @throws NullPointerException if the file to compare to is
   *         {@code null}.
   * @throws AssertionError if the the actual {@code File} is
   *         {@code null}.
   * @throws AssertionError if the content of the actual {@code File}
   *         is not equal to the content of the given one.
   */
  public FileAssertSB hasSameContentAs(File expected)
  {
    if (expected == null)
      throw new NullPointerException(formattedErrorMessage("File to compare to should not be null"));
    isNotNull();
    assertExists(actual).assertExists(expected);
    try
    {
      LineDiff[] diffs = comparator.compareContents(actual, expected);
      if (!isEmpty(diffs))
        fail(expected, diffs);
    }
    catch (IOException e)
    {
      cannotCompareToExpectedFile(expected, e);
    }
    return this;
  }

  protected FileAssertSB hasSameContentAsFileFromClasspath(String classpathLocation) throws IOException
  {
    return hasSameContentAsFileFromClasspath(FileAssertSB.class, classpathLocation);
  }

  protected FileAssertSB hasSameContentAsFileFromClasspath(Class relativeTo, String file) throws IOException
  {
    if (file == null)
    {
      throw new NullPointerException(formattedErrorMessage("File to compare to should not be null"));
    }
    isNotNull();
    InputStream expected = relativeTo.getResourceAsStream(file);
    return hasSameContentAs(expected, file);
  }

  protected FileAssertSB hasSameContentAs(InputStream stream, String fileName) throws IOException
  {
    Assertions.assertThat(stream)
        .as("file in classpath does not exist at :" + fileName)
        .isNotNull();

    String expect = IOUtils.toString(stream);
    FileInputStream in = new FileInputStream(actual);
    try
    {
      String act = IOUtils.toString(in);
      assertThat(normalizeCrLfToCr(act)).as("file content " + fileName).isEqualTo(normalizeCrLfToCr(expect));
    }
    finally
    {
      if (in != null)
      {
        in.close();
      }
    }
    return this;
  }

  protected String normalizeCrLfToCr(String in)
  {
    if (in == null)
    {
      return null;
    }
    String out = in.replace("\r\n", "\r");
    return out;
  }

  protected void fail(Object expected, LineDiff[] diffs)
  {
    failIfCustomMessageIsSet();
    StringBuilder b = new StringBuilder();
    b.append("file:").append(inBrackets(actual)).append(" and file:").append(inBrackets(expected))
        .append(" do not have same contents:");

    StringBuffer exp = new StringBuffer();
    StringBuffer act = new StringBuffer();
    for (LineDiff diff : diffs)
    {
      // b.append(LINE_SEPARATOR).append("line:").append(inBrackets(diff.lineNumber))
      // .append(", expected:").append(inBrackets(diff.expected)).append(" but was:")
      // .append(inBrackets(diff.actual));
      exp.append("Line " + inBrackets(diff.lineNumber)).append(LINE_SEPARATOR);
      act.append("Line " + inBrackets(diff.lineNumber)).append(LINE_SEPARATOR);
      exp.append(diff.expected).append(LINE_SEPARATOR);
      act.append(diff.actual).append(LINE_SEPARATOR);
    }
    throw new ComparisonFailure("File content comparison failed", exp.toString(), act.toString());
    // fail(b.toString());
  }

  protected void fail(LineDiff[] diffs)
  {
    failIfCustomMessageIsSet();
    StringBuilder b = new StringBuilder();
    StringBuilder e = new StringBuilder();
    StringBuilder a = new StringBuilder();
    b.append("file:").append(inBrackets(actual)).append(" and expected filecontent ")
        .append(" do not have same contents:");
    e.append("[linenumber] : line-content");
    a.append("[linenumber] : line-content");
    for (LineDiff diff : diffs)
    {
      e.append(LINE_SEPARATOR)
          .append("[" + diff.lineNumber + "]")
          .append(" :")
          .append(diff.expected);
      a.append(LINE_SEPARATOR)
          .append("[" + diff.lineNumber + "]")
          .append(" :")
          .append(diff.actual);
      b.append(LINE_SEPARATOR)
          .append("line:")
          .append(inBrackets(diff.lineNumber))
          .append(", expected:")
          .append(inBrackets(diff.expected))
          .append(" but was:")
          .append(inBrackets(diff.actual));
    }
    throw new ComparisonFailure(b.toString(), e.toString(), a.toString());
    // fail(b.toString());
  }

  protected void cannotCompareToExpectedFile(Object expected, Exception e)
  {
    failIfCustomMessageIsSet(e);
    String message = format("unable to compare contents of files:<%s> and <%s>", actual, expected);
    fail(message, e);
  }

  protected void cannotCompareToExpectedFile(Exception e)
  {
    failIfCustomMessageIsSet(e);
    String message = format("unable to compare contents of files:<%s> and filecontent", actual);
    fail(message, e);
  }

  protected FileAssertSB assertExists(File file)
  {
    if (file.exists())
      return this;
    failIfCustomMessageIsSet();
    throw failure(format("file:<%s> should exist", file));
  }

  /**
   * Verifies that the actual {@code File} is a relative path.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code File} is not a
   *         relative path.
   */
  public FileAssertSB isRelative()
  {
    isNotNull();
    if (!actual.isAbsolute())
      return this;
    failIfCustomMessageIsSet();
    throw failure(format("file:<%s> should be a relative path", actual));
  }

  /**
   * Verifies that the actual {@code File} is an absolute path.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code File} is not an
   *         absolute path.
   */
  public FileAssertSB isAbsolute()
  {
    isNotNull();
    if (actual.isAbsolute())
      return this;
    failIfCustomMessageIsSet();
    throw failure(format("file:<%s> should be an absolute path", actual));
  }

  public FileAssertSB linesAreEqualTo(String... lines)
  {
    exists();
    if (lines == null)
      throw new NullPointerException(formattedErrorMessage("Content to compare to should not be null"));

    try
    {
      StringBuffer expected = new StringBuffer();
      for (String line : lines)
      {
        if (expected.length() != 0)
        {
          expected.append(LINE_SEPARATOR);
        }
        expected
            .append(line);
      }
      InputStream eis = new ByteArrayInputStream(expected.toString().getBytes());
      LineDiff[] diffs = comparator.compareContents(actual, eis);
      if (!isEmpty(diffs))
        fail(diffs);
    }
    catch (IOException e)
    {
      cannotCompareToExpectedFile(e);
    }
    return this;
  }

  public static interface ProvidesFile
  {
    File getFile();
  }

  /**
   * supports classpath: locations
   * @param fileLocator
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public void hasContent(String fileLocator) throws ClassNotFoundException, IOException
  {
    if (fileLocator.startsWith("classpath:"))
    {
      String file = fileLocator.substring(10);
      
      Class<?> clazz = ReflectionUtils.magicClassFind("hasContent");
//      StackTraceElement[] trace = Thread.currentThread().getStackTrace();
//      Class<?> clazz = null;
//      for (int i = 1; i < trace.length; i++)
//      {
//        StackTraceElement el = trace[i];
//        if (el.getMethodName().contains("hasContent"))
//        {
//          continue;
//        }
//        clazz = Class.forName(el.getClassName());
//        break;
//      }
      hasSameContentAsFileFromClasspath(clazz, file);
    }
    else
    {
      hasSameContentAs(new File(fileLocator));
    }
  }

  public void nameEndsWith(String endWith)
  {
    isNotNull();
    String act = actual.getAbsolutePath();

    act = act.replace(separator, "/");
    Assertions.assertThat(act)
        .as("absolut file path ends with").endsWith(endWith);
  }

  public void hasContent(InputStream resourceAsStream) throws IOException
  {
    hasSameContentAs(resourceAsStream, "file-input-stream");
  }
}
