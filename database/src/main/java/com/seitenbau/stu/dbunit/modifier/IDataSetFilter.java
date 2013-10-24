package com.seitenbau.stu.dbunit.modifier;

import org.dbunit.dataset.IDataSet;

/**
 * Interface for all modifiers that should apply a filter to a dataset.
 */
public interface IDataSetFilter extends IDataSetModifier
{
  /**
   * Executes the filtering on the given dataset.
   * 
   * @param current The current DataSet.
   * 
   * @return The filtered DataSet
   * 
   * @throws Exception Failure.
   */
  public IDataSet filter(IDataSet current) throws Exception;
}
