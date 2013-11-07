package com.seitenbau.stu.database.dsl;

import groovy.lang.Closure;

import org.codehaus.groovy.runtime.GroovyCategorySupport;

public class TableParser
{

  @SuppressWarnings("serial")
  public static <R, G, D extends DatabaseRef> void parseTable(final Closure<?> rows, final TableParserAdapter<R, G, D> adapter)
  {
    TableParserContext.createContext(new TableParsedRowHandler<R, G, D>(adapter));
    GroovyCategorySupport.use(TableParserContext.class, new Closure<Object>(null)
    {
      @Override
      public Object call()
      {
        rows.setResolveStrategy(Closure.DELEGATE_FIRST); // ensure that column bindings are used
        rows.setDelegate(adapter.getTableContext());
        return rows.call();
      }
    });
    TableParserContext.releaseContext();
  }

}
