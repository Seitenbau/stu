package com.seitenbau.testing.dbunit.dsl

import java.util.List;

import com.seitenbau.testing.dbunit.dsl.TableParser;

import groovy.lang.Closure;

public class TableParser {

	public static void parseTable(Closure rows, Object delegate, IParsedTableRowCallback callback) {
		TableParserContext.createContext(callback);
    
    // TODO NM/CB Is it possible to achieve this Groovy feature in Java?
		use(TableParserContext) {
      rows.resolveStrategy = Closure.DELEGATE_FIRST // ensure that column bindings are used
			rows.setDelegate(delegate);
			rows.call();
		}
    
    TableParserContext.releaseContext();
	}
}
