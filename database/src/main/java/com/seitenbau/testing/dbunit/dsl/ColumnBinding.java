package com.seitenbau.testing.dbunit.dsl;

import com.google.common.base.Optional;
import com.seitenbau.testing.dbunit.generator.DataType;
import com.seitenbau.testing.util.Future;

/**
 * Base class for ColumnBindings
 *
 * @param <R> Table Rowbuilder class
 * @param <G> Table GetWhere class
 */
public abstract class ColumnBinding<R, G>
{

  public abstract DataType getDataType();

  /**
   * Allows to set a value for the corresponding column on a row
   * @param row The row on which the value shall be set
   * @param value The value to be set
   */
  public abstract void set(R row, Object value);

  /**
   * Sets a lazy value on the corresponding column. Will be evaluated
   * every time, the row column value is accessed.
   * @param row The row on which the value shall be set
   * @param value The lazy value to be set
   * @throws RuntimeException if the column does not support lazy values
   */
  public void setFutureValue(R row, Future<Object> value)
  {
    throw new RuntimeException("Setting a a lazy value is not supported for this column");
  }

  /**
   * Sets a reference to the corresponding column.
   * @param row The row on which the reference shall be set
   * @param reference The reference to be set
   * @throws RuntimeException if the column is not a reference column
   */
  public void setReference(R row, DatabaseRef reference)
  {
    throw new RuntimeException("Setting a reference is not supported for this column");
  }

  public boolean isRefColumn()
  {
    return false;
  }

  public boolean isUnique()
  {
    return false;
  }

  /**
   * Queries the table on the column. Only returns one row as result.
   * @param getWhere The getWhere instance
   * @param value The value to be searched
   * @return An Optional containing the result row on success, absent if no row was found.
   */
  public Optional<R> getWhere(G getWhere, Object value)
  {
    return Optional.<R> absent();
  }

}
