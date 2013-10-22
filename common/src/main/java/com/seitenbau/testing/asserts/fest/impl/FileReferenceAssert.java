package com.seitenbau.testing.asserts.fest.impl;

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

import static com.seitenbau.testing.asserts.fest.Assertions.assertThat;
import static org.fest.assertions.Formatting.format;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Arrays.isEmpty;
import static org.fest.util.Systems.LINE_SEPARATOR;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;
import org.junit.ComparisonFailure;

import com.seitenbau.testing.asserts.fest.impl.FileContentComparator.LineDiff;
import com.seitenbau.testing.io.files.FileReference;
import com.seitenbau.testing.util.Closure;
import com.seitenbau.testing.util.ReflectionUtils;


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
public class FileReferenceAssert extends GenericAssert<FileReferenceAssert, FileReference>
{
  String separator = System.getProperty("file.separator");

  private final FileContentComparator comparator;

  /**
   * Creates a new <code>FileAssert</code>.
   * @param actual the target to verify.
   */
  public FileReferenceAssert(FileReference actual)
  {
    super(FileReferenceAssert.class, actual);
    comparator = new FileContentComparator();
  }

  /**
   * Verifies that the actual {@code File} does not exist.
   * @return this assertion object.
   * @throws AssertionError if the the actual {@code File} is
   *         {@code null}.
   * @throws AssertionError if the actual {@code File} exists.
   */
  public FileReferenceAssert doesNotExist()
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
  public FileReferenceAssert exists()
  {
    isNotNull();
    assertExists(actual);
    return this;
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
  public FileReferenceAssert hasSameContentAs(final File expected)
  {
    if (expected == null)
      throw new NullPointerException(formattedErrorMessage("File to compare to should not be null"));
    isNotNull();
    // assertExists(actual).assertExists(expected);
    try
    {
      LineDiff[] diffs = actual.withInputStream(new Closure<LineDiff[], InputStream, IOException>()
      {
        public LineDiff[] call(InputStream input) throws IOException
        {
          return comparator.compareContents(input, expected);
        }
      });
      if (!isEmpty(diffs))
        fail(expected, diffs);
    }
    catch (IOException e)
    {
      cannotCompareToExpectedFile(expected, e);
    }
    return this;
  }

  protected FileReferenceAssert hasSameContentAsFileFromClasspath(String classpathLocation)
      throws IOException
  {
    return hasEqualContentAsFileFromClasspath(FileReferenceAssert.class, classpathLocation);
  }

  /** ford LFCR normalization! */
  protected FileReferenceAssert hasEqualContentAsFileFromClasspath(Class relativeTo, String file)
      throws IOException
  {
    if (file == null)
    {
      throw new NullPointerException(formattedErrorMessage("File to compare to should not be null"));
    }
    isNotNull();
    InputStream expected = relativeTo.getResourceAsStream(file);
    return hasEqualContentAs(expected, file);
  }

  /** ford LFCR normalization! */
  protected FileReferenceAssert hasEqualContentAs(InputStream stream, String fileName) throws IOException
  {
    Assertions.assertThat(stream)
        .as("file in classpath does not exist at :" + fileName)
        .isNotNull();

    String expect = IOUtils.toString(stream);
    String act = actual.readContentAsString();
    assertThat(normalizeCrLfToCr(act)).as("file content " + fileName).isEqualTo(normalizeCrLfToCr(expect));
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

  protected FileReferenceAssert assertExists(FileReference file)
  {
    if (file.exists())
      return this;
    failIfCustomMessageIsSet();
    throw failure(format("file:<%s> should exist", file));
  }

  public FileReferenceAssert linesAreEqualTo(String... lines)
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
      final InputStream eis = new ByteArrayInputStream(expected.toString().getBytes());
      LineDiff[] diffs =
          actual.withInputStream(new Closure<LineDiff[], InputStream, IOException>()
          {
            public LineDiff[] call(InputStream input) throws IOException
            {
              LineDiff[] result = comparator.compareContents(input, eis);
              return result;
            }
          });
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
  public void hasContentLike(String fileLocator) throws ClassNotFoundException, IOException
  {
    if (fileLocator.startsWith("classpath:"))
    {
      String file = fileLocator.substring(10);

      Class<?> clazz = ReflectionUtils.magicClassFind("hasContent");
      hasEqualContentAsFileFromClasspath(clazz, file);
    }
    else
    {
      hasSameContentAs(new File(fileLocator));
    }
  }

  public void nameEndsWith(String endWith)
  {
    isNotNull();
    String act = actual.getUri();

    act = act.replace(separator, "/");
    Assertions.assertThat(act)
        .as("absolut file path ends with").endsWith(endWith);
  }

  public void hasContent(InputStream resourceAsStream) throws IOException
  {
    hasEqualContentAs(resourceAsStream, "file-input-stream");
  }

  /**
   * Checks the content
   * @param expected
   */
  public FileReferenceAssert hasContent(String expected) throws IOException
  {
    if (expected == null)
      throw new NullPointerException(formattedErrorMessage("expected should not be null"));
    isNotNull();
    String act = actual.readContentAsString();
    assertThat(act).as("Content of file " + actual).isEqualTo(expected);
    return this;
  }

  /**
   * Checks the content, LFCR normalized
   * @param expected
   * @return 
   * @throws IOException 
   */
  public FileReferenceAssert hasContentEqualsTo(String expected) throws IOException
  {
    if (expected == null)
      throw new NullPointerException(formattedErrorMessage("expected should not be null"));
    isNotNull();
    String act = actual.readContentAsString();
    assertThat(normalizeCrLfToCr(act)).as("Content of file " + actual).isEqualTo(normalizeCrLfToCr(expected));
    return this;
  }

}
