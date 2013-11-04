package com.seitenbau.stu.database.dsl

import com.seitenbau.stu.database.dsl.DatabaseRef;
import com.seitenbau.stu.database.dsl.TableParsedRowHandler;
import com.seitenbau.stu.database.dsl.TableParserAdapter;
import com.seitenbau.stu.database.dsl.TableParserContext;


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
