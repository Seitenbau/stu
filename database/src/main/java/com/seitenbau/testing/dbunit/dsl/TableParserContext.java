package com.seitenbau.testing.dbunit.dsl;

public class TableParserContext
{
  private static class Context
  {
    final IParsedTableRowCallback callback;
    TableRowModel activeRow;
    
    Context(IParsedTableRowCallback callback) {
      this.callback = callback;
    }
  }
  
  private static ThreadLocal<Context> context = new ThreadLocal<Context>();

  public static TableRowModel or(Object self, Object arg) {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Integer self, Integer arg) {
    return appendRow(self, arg);
  }

  public static TableRowModel or(Boolean self, Boolean arg) {
    return appendRow(self, arg);
  }

  /**
   * Called when a new row starts
   */
  public static TableRowModel appendRow(Object value, Object nextValue) {
    Context currentContext = context.get();
    TableRowModel lastRow = currentContext.activeRow;
    if (lastRow != null) {
      currentContext.callback.parsedRow(lastRow);
    }
    TableRowModel row = new TableRowModel(value);
    currentContext.activeRow = row;
    return row.or(nextValue);
  }
  
  public static void createContext(IParsedTableRowCallback callback) 
  {
    Context currentContext = new Context(callback);
    context.set(currentContext);
  }

  public static void releaseContext()
  {
    Context currentContext = context.get();
    if (currentContext.activeRow != null) {
      currentContext.callback.parsedRow(currentContext.activeRow);
    }
    context.remove();
  }
}
