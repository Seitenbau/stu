package com.seitenbau.testing.dbunit.dsl;

/**
 * Represents a DataSet for the DataSetRegistry context
 * The representation is not bound on the DataSetIdentificator instance,
 * so different objects can represent the same DataSet.
 */
public interface DataSetIdentificator
{

  /**
   * The actual represented DataSet
   * @return The DataSet
   */
  Object getDataSet();
  
  /**
   * Identifies the underlying Database Model
   */
  String getDataSetClassName();
  
}
