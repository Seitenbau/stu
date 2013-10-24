package com.seitenbau.stu.database.dsl;

import org.codehaus.groovy.runtime.StackTraceUtils;

public class TableParserException extends RuntimeException
{

  private static final long serialVersionUID = 1L;

  public TableParserException(String message, TableRowModel row)
  {
    super(message);
    updateStackTrace(row);
  }

  public TableParserException(String message, TableRowModel row, Throwable cause)
  {
    super(message, cause);
    updateStackTrace(row);
  }

  private void updateStackTrace(TableRowModel row)
  {
    StackTraceUtils.sanitize(this);

    if (row.getStackTraceElement().isPresent()) {
      insertStackTraceElement(row.getStackTraceElement().get());
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

}
