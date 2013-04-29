package com.seitenbau.testing.dbunit.extend;

import org.dbunit.operation.DatabaseOperation;

/**
 * Factory to replace the actually used factory methods by the
 * DatabaseTester.
 */
public interface DatabaseOperationFactory
{
  /**
   * @return a truncate Operation used to truncate all Tables E.g. :
   *         {@link DatabaseOperation#TRUNCATE_TABLE}
   */
  DatabaseOperation truncateOperation();

  /**
   * @return the delete Operation used to delete Tables contents E.g.
   *         : {@link DatabaseOperation#DELETE_ALL}
   */
  DatabaseOperation deleteOperation();

  /**
   * @return the insert operation used to insert content into tables.
   *         E.g. : {@link DatabaseOperation#INSERT}
   */
  DatabaseOperation insertOperation();

  /**
   * @return a delete all and truncate/delete Operation used for
   *         cleanInsert E.g. : {@link DatabaseOperation#CLEAN_INSERT}
   */
  DatabaseOperation cleanInsertOperation();

}
