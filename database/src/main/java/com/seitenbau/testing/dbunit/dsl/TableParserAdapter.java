package com.seitenbau.testing.dbunit.dsl;

/**
 * Allows the TableParserCallback to access Table operations
 *
 * @param <R> Table RowBuilder class
 * @param <G> Table GetWhere class
 * @param <D> Table DatabaseReference class
 */
public interface TableParserAdapter<R, G, D extends DatabaseReference>
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
   * Resolves all relations to the reference by the concrete value 
   * @param reference The reference, which can be resolved now
   * @param row The row corresponding to the reference
   */
  void handleReferences(D reference, R row);

  R getRowByReference(D reference);

  String getTableName();

}
