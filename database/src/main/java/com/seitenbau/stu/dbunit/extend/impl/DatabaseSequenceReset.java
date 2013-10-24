package com.seitenbau.stu.dbunit.extend.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;

import com.seitenbau.stu.dbunit.extend.DatabaseTesterCleanAction;
import com.seitenbau.stu.dbunit.tester.DatabaseTesterBase;
import com.seitenbau.stu.logger.Logger;
import com.seitenbau.stu.logger.TestLoggerFactory;

public abstract class DatabaseSequenceReset<THAT> implements DatabaseTesterCleanAction
{
  protected String _postfix;

  protected Integer _startIndex = 10000;

  protected List<String> _blacklistedTableNames = new ArrayList<String>();

  protected List<String> _sequencesToClear = new ArrayList<String>();

  protected boolean _stopOnDropSequenceException;

  protected Logger logger = TestLoggerFactory.get(this.getClass());

  protected boolean _logOnInfo;

  /**
   * @return this
   */
  public THAT autoDerivateFromTablename(String postfix)
  {
    _postfix = postfix;
    return that();
  }

  /**
     * 
     */
  public THAT setLogOnLevelInfo(boolean onInfo)
  {
    _logOnInfo = onInfo;
    return that();
  }

  /**
   * By default exception occurring when dropping a sequence get
   * ignored!
   * 
   * @param stop set to true
   * @return
   */
  public THAT stopOnDropSequenceException(boolean stop)
  {
    _stopOnDropSequenceException = stop;
    return that();
  }

  /**
   * Specify the Start index a Sequence will get reseted to.
   * 
   * @param _startIndex Start index. BY default 10000
   * 
   * @return this
   */
  public THAT setStartIndex(Integer _startIndex)
  {
    this._startIndex = _startIndex;
    return that();
  }

  /**
   * Blacklist given TableName's so the derivation via
   * {@code #autoDerivateFromTablename(String)} does not reset them.
   * 
   * @param blacklistedTableName List of tables to reset. {@code null}
   *        to reset.
   * @return this
   */
  public THAT noResetFor(String... blacklistedTableName)
  {
    if (blacklistedTableName == null)
    {
      _blacklistedTableNames = new ArrayList<String>();
      return that();
    }
    this._blacklistedTableNames.addAll(Arrays.asList(blacklistedTableName));
    return that();
  }

  @SuppressWarnings("unchecked")
  protected THAT that()
  {
    return (THAT) this;
  }

  /**
   * List of sequence names to reset.
   * 
   * @param nameOfSequenceToReset list of names, or {@code null} to
   *        reset
   * @return this
   */
  public THAT sequenceName(String... nameOfSequenceToReset)
  {
    if (nameOfSequenceToReset == null)
    {
      _sequencesToClear = new ArrayList<String>();
      return that();
    }
    this._sequencesToClear.addAll(Arrays.asList(nameOfSequenceToReset));
    return that();
  }

  public void doCleanDatabase(DatabaseTesterBase<?> tester, IDataSet dataset) throws Exception
  {
    if (_postfix != null)
    {
      clearByPostfix(tester, dataset);
    }
    for (String sequenceName : _sequencesToClear)
    {
      callResetSequence(tester, sequenceName);
    }
    clearAfter(tester);
  }

  /**
   * Template method to do cleanup code after doCleanDatabase
   * @param tester
   * @throws Exception
   */
  protected void clearAfter(DatabaseTesterBase<?> tester) throws Exception
  {
  }

  protected void clearByPostfix(DatabaseTesterBase<?> tester, IDataSet dataset) throws DataSetException, Exception
  {
    String[] names = dataset.getTableNames();
    if (names == null)
    {
      return;
    }
    for (String tableName : names)
    {
      doCleanSequenceForTable(tester, dataset, tableName);
    }
  }

  protected void doCleanSequenceForTable(DatabaseTesterBase<?> tester, IDataSet dataset, String tableName)
      throws Exception
  {
    if (_blacklistedTableNames.contains(tableName) || _sequencesToClear.contains(tableName))
    {
      return;
    }
    String sequenceName = tableName + _postfix;
    callResetSequence(tester, sequenceName);
  }

  protected boolean isLogOnLevelInfo()
  {
    return _logOnInfo;
  }

  protected void logAction(String message)
  {
    if (isLogOnLevelInfo())
    {
      logger.info(message);
    }
    else
    {
      logger.trace(message);
    }
  }

  protected void callResetSequence(DatabaseTesterBase<?> tester, String sequenceName) throws Exception
  {
    clearSequence(tester, sequenceName);
  }

  /**
   * Template to do cleanup code for one detected Sequence
   * @param tester
   * @throws Exception
   */
  abstract protected void clearSequence(DatabaseTesterBase<?> tester, String sequenceName) throws Exception;

  public void doPrepareDatabase(DatabaseTesterBase<?> tester, IDataSet dataset) throws Exception
  {
  }

  /**
   * Get the start index new sequences will start from.
   */
  public int getStartIndex()
  {
    return _startIndex;
  }

}
