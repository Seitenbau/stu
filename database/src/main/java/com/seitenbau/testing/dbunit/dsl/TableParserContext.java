package com.seitenbau.testing.dbunit.dsl;

import java.math.BigDecimal;

public class TableParserContext
{

  private static class Context
  {
    final TableParsedRowHandler<?, ?, ?> rowHandler;

    TableRowModel activeRow;

    Context(TableParsedRowHandler<?, ?, ?> rowHandler)
    {
      this.rowHandler = rowHandler;
    }
  }

  private static ThreadLocal<Context> context = new ThreadLocal<Context>();

  /**
   * Called when a new row starts
   */
  public static TableRowModel appendRow(Object value, Object nextValue)
  {
    Context currentContext = context.get();
    TableRowModel lastRow = currentContext.activeRow;
    if (lastRow != null)
    {
      currentContext.rowHandler.handleRow(lastRow);
      lastRow.clearStackTrace();
    }
    TableRowModel row = new TableRowModel(value);
    currentContext.activeRow = row;
    return row.or(nextValue);
  }

  public static void createContext(TableParsedRowHandler<?, ?, ?> callback)
  {
    Context currentContext = new Context(callback);
    context.set(currentContext);
  }

  public static void releaseContext()
  {
    Context currentContext = context.get();
    if (currentContext.activeRow != null)
    {
      currentContext.rowHandler.handleRow(currentContext.activeRow);
      currentContext.activeRow.clearStackTrace();
    }
    context.remove();
  }

  //------ different or(self, arg) methods to override existing operators ----

  public static TableRowModel or(Object self, Object arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Boolean self, Boolean arg)
  {
    return appendRow(self, arg);
  }

  //---------------- INTEGER SELF -----------------------------
  public static TableRowModel or(Integer self, Object arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Integer self, Integer arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Integer self, Long arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Integer self, Float arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Integer self, Double arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Integer self, BigDecimal arg)
  {
    return appendRow(self, arg);
  }

  //---------------- LONG SELF -----------------------------
  public static TableRowModel or(Long self, Object arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Long self, Integer arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Long self, Long arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Long self, Float arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Long self, Double arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Long self, BigDecimal arg)
  {
    return appendRow(self, arg);
  }

  //---------------- FLOAT SELF -----------------------------
  public static TableRowModel or(Float self, Object arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Float self, Integer arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Float self, Long arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Float self, Float arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Float self, Double arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Float self, BigDecimal arg)
  {
    return appendRow(self, arg);
  }

  //---------------- DOUBLE SELF -----------------------------
  public static TableRowModel or(Double self, Object arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Double self, Integer arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Double self, Long arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Double self, Float arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Double self, Double arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Double self, BigDecimal arg)
  {
    return appendRow(self, arg);
  }

  //---------------- BIGDECIMAL SELF -----------------------------
  public static TableRowModel or(BigDecimal self, Object arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(BigDecimal self, Integer arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(BigDecimal self, Long arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(BigDecimal self, Float arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(BigDecimal self, Double arg)
  {
    return appendRow(self, arg);
  }

  public static TableRowModel or(BigDecimal self, BigDecimal arg)
  {
    return appendRow(self, arg);
  }

}
