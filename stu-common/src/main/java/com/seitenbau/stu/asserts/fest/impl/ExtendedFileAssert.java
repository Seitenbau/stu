package com.seitenbau.stu.asserts.fest.impl;

/*
 * June 2012 copy of FileAssert to patch compare methods ... why
 * because they used private classes ... fun
 * 
 * Created on Dec 23, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * Copyright @2007-2011 the original author or authors.
 */

import static org.fest.assertions.Formatting.format;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Arrays.isEmpty;
import static org.fest.util.Systems.LINE_SEPARATOR;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.fest.assertions.Assertions;
import org.junit.ComparisonFailure;

import com.seitenbau.stu.asserts.fest.impl.FileContentComparator.LineDiff;

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
public class ExtendedFileAssert extends org.fest.assertions.FileAssert
{
  String separator = System.getProperty("file.separator");

  private final FileContentComparator comparator;

  /**
   * Creates a new <code>FileAssert</code>.
   * @param actual the target to verify.
   */
  public ExtendedFileAssert(File actual)
  {
    super(actual);
    comparator = new FileContentComparator();
  }

  /**
   * Creates a new <code>FileAssert</code>.
   * @param actual the target to verify.
   */
  public ExtendedFileAssert(ProvidesFile actual)
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
  public ExtendedFileAssert doesNotExist()
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
  public ExtendedFileAssert exists()
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
  public ExtendedFileAssert hasSize(long expected)
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
  public ExtendedFileAssert isDirectory()
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
  public ExtendedFileAssert isFile()
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
  public ExtendedFileAssert hasSameContentAs(File expected)
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

  protected ExtendedFileAssert hasSameContentAsFileFromClasspath(String file)
  {
    if (file == null)
      throw new NullPointerException(formattedErrorMessage("File to compare to should not be null"));
    isNotNull();
    InputStream expected = ExtendedFileAssert.class.getResourceAsStream(file);
    return hasSameContentAs(expected, file);
  }

  protected ExtendedFileAssert hasSameContentAs(InputStream stream, String fileName)
  {
    Assertions.assertThat(stream)
        .as("file in classpath does not exist at :" + fileName)
        .isNotNull();
    try
    {
      LineDiff[] diffs = comparator.compareContents(actual, stream);
      if (!isEmpty(diffs))
        fail(stream, diffs);
    }
    catch (IOException e)
    {
      cannotCompareToExpectedFile(stream, e);
    }
    return this;
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
      exp.append("Line " + inBrackets(diff.lineNumber)).append(LINE_SEPARATOR);
      act.append("Line " + inBrackets(diff.lineNumber)).append(LINE_SEPARATOR);
      exp.append(diff.expected).append(LINE_SEPARATOR);
      act.append(diff.actual).append(LINE_SEPARATOR);
    }
    throw new ComparisonFailure("File content comparison failed",exp.toString(),act.toString());
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

  protected ExtendedFileAssert assertExists(File file)
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
  public ExtendedFileAssert isRelative()
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
  public ExtendedFileAssert isAbsolute()
  {
    isNotNull();
    if (actual.isAbsolute())
      return this;
    failIfCustomMessageIsSet();
    throw failure(format("file:<%s> should be an absolute path", actual));
  }

  public ExtendedFileAssert linesAreEqualTo(String... lines)
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

  public interface ProvidesFile
  {
    File getFile();
  }

  public void hasContent(String fileLocator)
  {
    if (fileLocator.startsWith("classpath:"))
    {
      String file = fileLocator.substring(10);
      hasSameContentAsFileFromClasspath(file);
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

  public void hasContent(InputStream resourceAsStream)
  {
    hasSameContentAs(resourceAsStream,"file-input-stream");
  }
}
