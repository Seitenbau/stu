package com.seitenbau.testing.dbunit.dsl;

/**
 * Allows the TableParserCallback to access Table operations
 *
 * @param <R> Table RowBuilder class
 * @param <G> Table GetWhere class
 * @param <D> Table DatabaseRef class
 */
public interface TableParserAdapter<R, G, D extends DatabaseRef>
{

  /**
   * Inserts a new row into the table
   * @return The rowbuilder for the new row
   */
  R insertRow();

  /**
   * Returns the getWhere instance for the table
   * @return The getWhere instance
   */
  G getWhere();

  /**
   * Binds the row to the reference under the table's dataset
   * @param reference The reference
   * @param row The row which shall be bound
   */
  void bindToDataSet(D reference, R row);

  /**
   * Gets a row by a Database Reference. Returns null if no row is
   * bound.
   * @param reference The corresponding reference
   * @return The corresponding rowbuilder or null if there is no match
   */
  R getRowByReference(D reference);

  /**
   * Returns the table context, which is used as delegate when the
   * table closure is evaluated, in fact the instance of the table adapter class.
   * @return The table context
   */
  Object getTableContext();

  /**
   * Returns the name of the table (uses for error messages)
   * @return The table's name
   */
  String getTableName();

}
