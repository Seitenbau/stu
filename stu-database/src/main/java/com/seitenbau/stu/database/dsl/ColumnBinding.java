package com.seitenbau.stu.database.dsl;

import com.google.common.base.Optional;
import com.seitenbau.stu.database.generator.DataType;

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
   * Allows to set a value for the corresponding column on a row.
   * Value may be a concrete value or a Future value, a Database Modifier, ...
   * @param row The row on which the value shall be set
   * @param value The value to be set
   */
  public abstract void set(R row, Object value);

  public boolean isRefColumn()
  {
    return false;
  }

  public boolean isIdentifier()
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
