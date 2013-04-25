package com.seitenbau.testing.dbunit.extend;

import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.dbunit.tester.DatabaseTesterBase;

/**
 * Interface for running Before and after Clean scripts 
 */
public interface DatabaseTesterCleanAction
{

  /**
   * Got invoked before the cleanInsert / truncate action
   * @param tester
   * @param dataset
   * @throws Exception
   */
  void doCleanDatabase(DatabaseTesterBase<?> tester, IDataSet dataset) throws Exception;
  
  /**
   * Got invoked after the cleanInsert / truncate action
   * @param tester
   * @param dataset
   * @throws Exception
   */
  void doPrepareDatabase(DatabaseTesterBase<?> tester, IDataSet dataset) throws Exception;

}
