package com.seitenbau.testing.dbunit.dsl


public class TableParser {

  public static <R, G, D extends DatabaseRef> void parseTable(Closure rows, TableParserAdapter<R, G, D> adapter) {
    TableParserContext.createContext(new TableParsedRowHandler<R, G, D>(adapter));

    // TODO NM/CB Is it possible to achieve this Groovy feature in Java?
    use(TableParserContext) {
      rows.resolveStrategy = Closure.DELEGATE_FIRST // ensure that column bindings are used
      rows.setDelegate(adapter.getTableContext());
      rows.call();
    }

    TableParserContext.releaseContext();
  }
}
