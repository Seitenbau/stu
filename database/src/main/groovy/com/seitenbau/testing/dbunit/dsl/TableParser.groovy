package com.seitenbau.testing.dbunit.dsl

import java.util.List;

import com.seitenbau.testing.dbunit.dsl.TableParser;

import groovy.lang.Closure;

public class TableParser {
	private static class Context {
		IParsedTableRowCallback callback;
		TableRowModel activeRow;
	}

	private static ThreadLocal<Context> context = new ThreadLocal<Context>()

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

	public static void parseTable(Closure rows, Object owner, IParsedTableRowCallback callback) {
		Context currentContext = new Context();
		currentContext.callback = callback;
		context.set(currentContext);
		use(TableParser) {
			rows.delegate = owner;
			//rows.resolveStrategy = Closure.DELEGATE_FIRST
			rows();
		}

		if (currentContext.activeRow != null) {
			callback.parsedRow(currentContext.activeRow);
		}
		context.remove();
	}
}
