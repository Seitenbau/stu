package com.seitenbau.testing.dbunit.dsl;

import org.codehaus.groovy.runtime.StackTraceUtils;

import com.google.common.base.Optional;

public class TableParserException extends RuntimeException
{

  private static final long serialVersionUID = 1L;

  public TableParserException(String message, TableRowModel row)
  {
    super(message);
    updateStackTrace(row.getStackTrace());
  }

  public TableParserException(String message, TableRowModel row, Throwable cause)
  {
    super(message, cause);
    updateStackTrace(row.getStackTrace());
  }

  private void updateStackTrace(StackTraceElement[] stackTrace)
  {
    StackTraceUtils.sanitize(this);

    Optional<StackTraceElement> element = getTableStackTraceElement(stackTrace);
    if (element.isPresent()) {
      insertStackTraceElement(element.get());
    }
  }

  private void insertStackTraceElement(StackTraceElement element)
  {
    StackTraceElement[] elements = getStackTrace();
    StackTraceElement[] newElements = new StackTraceElement[elements.length + 1];

    newElements[0] = element;
    System.arraycopy(elements, 0, newElements, 1, elements.length);

    setStackTrace(newElements);
  }

  private Optional<StackTraceElement> getTableStackTraceElement(StackTraceElement[] stack)
  {
    for (int i = 0; i < stack.length; i++) {
      StackTraceElement element = stack[i];

      if (element.getFileName() == null || element.getClassName() == null)
      {
        continue;
      }

      if (element.getClassName().startsWith("org.codehaus.groovy"))
      {
        continue;
      }

      if (element.getClassName().equals("com.seitenbau.testing.dbunit.dsl.TableParser"))
      {
        continue;
      }

      if (!element.getFileName().endsWith(".groovy"))
      {
        continue;
      }

      if (!element.getMethodName().startsWith("doCall"))
      {
        continue;
      }
      return Optional.of(element);
    }

    return Optional.<StackTraceElement>absent();
  }

}
