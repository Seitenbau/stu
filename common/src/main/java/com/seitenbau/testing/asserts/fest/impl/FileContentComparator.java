package com.seitenbau.testing.asserts.fest.impl;

/*
 * Copied June 2012, because original code was private !!! 
 * changes:
 *   - made class public
 *   - made private metods protected
 * 
 * Created on Feb 9, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008-2011 the original author or authors.
 */

import static java.lang.String.format;
import static org.fest.util.Closeables.close;
import static org.fest.util.Objects.*;
import static org.fest.util.Strings.quote;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Compares the contents of two files.
 *
 * @author David DIDIER
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FileContentComparator {

  private static final String EOF = "EOF";

  /*
   * Verifies that the given files have the same content. Adapted from <a
   * href="http://junit-addons.sourceforge.net/junitx/framework/FileAssert.html" target="_blank">FileAssert</a> (from
   * <a href="http://sourceforge.net/projects/junit-addons">JUnit-addons</a>.)
   */
  protected LineDiff[] compareContents(File actual, File expected) throws IOException {
    InputStream ais = null;
    InputStream eis = null;
    try {
      ais = new FileInputStream(actual);
      eis = new FileInputStream(expected);
      List<LineDiff> diffs = verifyEqualContent(readerFor(ais), readerFor(eis));
      return diffs.toArray(new LineDiff[diffs.size()]);
    } finally {
      close(eis);
      close(ais);
    }
  }
  
  /*
   * Verifies that the given files have the same content. Adapted from <a
   * href="http://junit-addons.sourceforge.net/junitx/framework/FileAssert.html" target="_blank">FileAssert</a> (from
   * <a href="http://sourceforge.net/projects/junit-addons">JUnit-addons</a>.)
   */
  protected LineDiff[] compareContents(File actual, InputStream expected) throws IOException {
    InputStream ais = null;
    try {
      ais = new FileInputStream(actual);
      List<LineDiff> diffs = verifyEqualContent(readerFor(ais), readerFor(expected));
      return diffs.toArray(new LineDiff[diffs.size()]);
    } finally {
      close(ais);
    }
  }

  public LineNumberReader readerFor(InputStream inputStream) {
    return new LineNumberReader(new BufferedReader(new InputStreamReader(inputStream)));
  }

  protected List<LineDiff> verifyEqualContent(LineNumberReader actual, LineNumberReader expected) throws IOException {
    List<LineDiff> diffs = new ArrayList<LineDiff>();
    while (true) {
      if (!expected.ready() && !actual.ready()) return diffs;
      int lineNumber = expected.getLineNumber();
      String actualLine = actual.readLine();
      String expectedLine = expected.readLine();
      if (areEqual(actualLine, expectedLine)) continue;
      diffs.add(new LineDiff(lineNumber, actualLine, expectedLine));
      if (!actual.ready() && expected.ready()) {
        diffs.add(new LineDiff(lineNumber, EOF, expectedLine));
        return diffs;
      }
      if (actual.ready() && !expected.ready()) {
        diffs.add(new LineDiff(lineNumber, actualLine, EOF));
        return diffs;
      }
    }
  }

  static public class LineDiff {
    final int lineNumber;
    final String actual;
    final String expected;

    protected LineDiff(int lineNumber, String actual, String expected) {
      this.lineNumber = lineNumber;
      this.actual = actual;
      this.expected = expected;
    }

    @Override public int hashCode() {
      final int prime = HASH_CODE_PRIME;
      int result = 1;
      result = prime * result + hashCodeFor(actual);
      result = prime * result + hashCodeFor(expected);
      result = prime * result + lineNumber;
      return result;
    }

    @Override public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      LineDiff other = (LineDiff) obj;
      if (!areEqual(actual, other.actual)) return false;
      if (!areEqual(expected, other.expected)) return false;
      return lineNumber == other.lineNumber;
    }

    @Override public String toString() {
      return format("LineDiff [actual=%s, expected=%s, lineNumber=%d]", quote(actual), quote(expected), lineNumber);
    }
  }
}
