package com.seitenbau.stu.database.dsl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Optional;

public class TableRowModel
{

  private final List<Object> values = new ArrayList<Object>();

  private final boolean isHeadRow;

  private int refColumnIndex;

  private final List<Integer> identifierColumnIndexes;

  private final Optional<StackTraceElement> stackTraceElement;

  public TableRowModel(Object value)
  {
    refColumnIndex = -1;
    identifierColumnIndexes = new LinkedList<Integer>();
    isHeadRow = value instanceof ColumnBinding;
    stackTraceElement = determineStackTraceElement();

    addValue(value);
  }

  private Optional<StackTraceElement> determineStackTraceElement()
  {
    return getTableStackTraceElement(Thread.currentThread().getStackTrace());
  }

  Optional<StackTraceElement> getStackTraceElement()
  {
    return stackTraceElement;
  }

  public TableRowModel or(Object arg)
  {
    addValue(arg);
    return this;
  }

  private void addValue(Object value)
  {
    if (isHeadRow)
    {
      if (!(value instanceof ColumnBinding))
      {
        throw new RuntimeException("Cannot mix ColumnBindings and values within one row");
      }

      if (values.contains(value))
      {
        throw new RuntimeException("Cannot use a column more than once in a table");
      }

      ColumnBinding<?, ?> column = (ColumnBinding<?, ?>) value;
      if (column.isRefColumn()) {
        refColumnIndex = values.size();
      }
      if (column.isIdentifier()) {
        identifierColumnIndexes.add(Integer.valueOf(values.size()));
      }
    }
    values.add(value);
  }

  public List<Object> getValues()
  {
    return values;
  }

  public int getColumnCount()
  {
    return values.size();
  }

  public boolean isHeadRow()
  {
    return isHeadRow;
  }

  public int getRefColumnIndex()
  {
    if (!isHeadRow)
    {
      throw new RuntimeException("Cannot query REF column index from non-head row");
    }
    return refColumnIndex;
  }

  public List<Integer> getIdentifierColumnIndexes()
  {
    if (!isHeadRow)
    {
      throw new RuntimeException("Cannot query identifier column index from non-head row");
    }
    return identifierColumnIndexes;
  }

  public Object getValue(int index)
  {
    return values.get(index);
  }

  public List<Integer> getTraversableColumns()
  {
    if (!isHeadRow)
    {
      throw new RuntimeException("Cannot query ID column index from non-head row");
    }
    List<Integer> result = new LinkedList<Integer>();
    for (int i = 0; i < values.size(); i++)
    {
      if (i == refColumnIndex || identifierColumnIndexes.contains(Integer.valueOf(i)))
      {
        continue;
      }

      result.add(Integer.valueOf(i));
    }
    return result;
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("[TableRowModel");
    if (getValues().size() > 0)
    {
      builder.append(": ");
      for (Object value : getValues())
      {
        if (value instanceof String)
        {
          builder.append("\"" + value + "\"");
        }
        else if (value instanceof Character)
        {
          builder.append("'" + value + "'");
        }
        else if (value instanceof DatabaseRef)
        {

          builder.append("<" + value.getClass().getSimpleName() + ">");
        } else {
          builder.append(value);
        }
        builder.append(" | ");
      }
      builder.setLength(builder.length() - 3);
    }
    builder.append("]");

    return builder.toString();
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

      if (element.getClassName().equals("com.seitenbau.stu.database.dsl.TableParser"))
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
