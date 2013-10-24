package com.seitenbau.stu.dbunit.tester;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.ComparisonFailure;

import org.dbunit.Assertion;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.XmlDataSet;

import com.seitenbau.stu.dbunit.internal.DateColumnCompareTableDecorator;
import com.seitenbau.stu.dbunit.internal.DateColumnRangeTableDecorator;
import com.seitenbau.stu.dbunit.internal.DateComparator;
import com.seitenbau.stu.dbunit.internal.TableCompareDecorator;
import com.seitenbau.stu.dbunit.modifier.IDataSetModifier;
import com.seitenbau.stu.util.DateUtil;

public class DBAssertion
{
  /** option to suppress dump of tables */
  public static boolean dumpTablesOnError = true;

  /**
   * Compares the content of a table with the dataset specified inside
   * a XML file.
   * 
   * @param tableName The name of the table. Must not be {@code null}
   *        or empty.
   * 
   * @param expectedDataSetFile The XML file with the dataset that
   *        should be used for comparison. Must not be {@code null} or
   *        empty.
   * 
   * @throws Exception If an error occurs during comparison.
   */
  static public void assertTable(IDatabaseConnection databaseConnection, DateComparator comparator, String tableName,
      String expectedDataSetFile) throws Exception
  {
    IDataSet databaseDataSet;
    databaseDataSet = databaseConnection.createDataSet();

    List<String> dateColumns = new ArrayList<String>();
    ITable actualTable = databaseDataSet.getTable(tableName);
    ITable expectedTable = createExpectedTable(tableName, expectedDataSetFile);
    Column[] columns = actualTable.getTableMetaData().getColumns();
    for (Column column : columns)
    {
      if (column.getDataType().isDateTime())
      {
        dateColumns.add(column.getColumnName());
      }
    }

    for (String column : dateColumns)
    {
      expectedTable = new DateColumnRangeTableDecorator(comparator, column, expectedTable);
      actualTable = new DateColumnCompareTableDecorator(column, actualTable);
    }
    Assertion.assertEquals(expectedTable, actualTable);
  }

  private static ITable createExpectedTable(String tableName, String expectedDataSetFile) throws DataSetException,
      FileNotFoundException
  {
    IDataSet expectedDataSet = new XmlDataSet(new FileInputStream(expectedDataSetFile));
    return expectedDataSet.getTable(tableName);
  }

  /**
   * Compares the content of a table with the dataset specified inside
   * a XML file.
   * 
   * @param tableName The name of the table. Must not be {@code null}
   *        or empty.
   * 
   * @param expectedDataSetFile The XML file with the dataset that
   *        should be used for comparison. Must not be {@code null} or
   *        empty.
   * 
   * @throws Exception If an error occurs during comparison.
   */
  static public void assertTable(IDatabaseConnection databaseConnection, String tableName, String expectedDataSetFile)
      throws Exception
  {
    IDataSet databaseDataSet;
    databaseDataSet = databaseConnection.createDataSet();

    ITable actualTable = databaseDataSet.getTable(tableName);

    ITable createExpectedTable = createExpectedTable(tableName, expectedDataSetFile);

    // Assert actual database table match expected table
    Assertion.assertEquals(createExpectedTable, actualTable);
  }

  /**
   * Compares the content of a table with the dataset specified inside
   * a XML file.
   * 
   * @param tableName The name of the table. Must not be {@code null}
   *        or empty.
   * @param expectedDataSetFile The XML file with the dataset that
   *        should be used for comparison. Must not be {@code null} or
   *        empty.
   * @param fields The fields that should be considered for
   *        comparison.Mus not be {@code null} or empty.
   * @throws Exception If an error occurs during comparison.
   */
  static public void assertTable(IDatabaseConnection databaseConnection, String tableName, String expectedDataSetFile,
      String... fields) throws Exception
  {
    // Load expected data from an XML dataset
    IDataSet expectedDataSet = new XmlDataSet(new FileInputStream(expectedDataSetFile));
    assertTable(databaseConnection, tableName, expectedDataSet, fields);
  }

  /**
   * Creates a comma separated String from an array of Strings.
   * createSQLFields("NAME", "Title") => "Name, Title"
   * 
   * @param fields the single field Strings
   * @return The fields as comma sepatated String.
   */
  static protected String createSQLFields(String... fields)
  {
    String result = "*";
    if (fields != null && fields.length > 0)
    {
      StringBuffer stringBuffer = new StringBuffer();
      for (String field : fields)
      {
        stringBuffer.append(field);
        stringBuffer.append(",");
      }
      result = stringBuffer.deleteCharAt(stringBuffer.length() - 1).toString();
    }
    return result;
  }

  /**
   * Compares the content of a table with the dataset specified inside
   * a XML file. Should be used if not all fields should be compared.
   * 
   * @param tableName The name of the table. Must not be {@code null}
   *        or empty.
   * @param expectedDataSet The testdata that should be used for
   *        comparison.
   * @param fields The fields for the filter.
   * @throws Exception If an error occurs during comparison or table
   *         does not contain the testdata.
   * 
   */
  static public void assertTable(IDatabaseConnection databaseConnection, String tableName, IDataSet expectedDataSet,
      String... fields) throws Exception
  {

    // Fetch database data after executing your code
    String sqlFields = createSQLFields(fields);
    ITable actualTable;
    // Fetch database data after executing your code
    String sql = "SELECT " + sqlFields + " FROM " + tableName;
    actualTable = databaseConnection.createQueryTable(tableName, sql);

    ITable expectedTable = expectedDataSet.getTable(tableName);

    // Assert actual database table match expected table
    Assertion.assertEquals(new TableCompareDecorator(expectedTable), new TableCompareDecorator(actualTable));
  }

  /**
   * Compares the content of a table with the dataset specified inside
   * a XML file.
   * 
   * @param tableName The name of the table. Must not be {@code null}
   *        or empty.
   * @param expectedDataSetFile The XML file with the dataset that
   *        should be used for comparison. Must not be {@code null} or
   *        empty.
   * @throws Exception If an error occurs during comparison.
   */
  static public void assertTable(IDatabaseConnection databaseConnection, String tableName, IDataSet dataSet)
      throws Exception
  {
    IDataSet databaseDataSet;
    databaseDataSet = databaseConnection.createDataSet();

    ITable actualTable = databaseDataSet.getTable(tableName);

    ITable expectedTable = dataSet.getTable(tableName);

    // Assert actual database table match expected table
    Assertion.assertEquals(new TableCompareDecorator(expectedTable), new TableCompareDecorator(actualTable));

  }

  /**
   * Compares the content of a table with the provided dataset. The
   * columns are filtered and only columns that exist inside the
   * dataset are evaluated.
   * 
   * @param tableName The name of the table.
   * @param expectedDataSet the data with the expected state of the table.
   * 
   * @throws Exception If an error occurs during comparison.
   */
  static public void assertDataSetTable(IDataSet actualDataSet, String tableName, IDataSet expectedDataSet)
      throws Exception
  {
    ITable table = actualDataSet.getTable(tableName);
    ITable expectedTable = expectedDataSet.getTable(tableName);
    ITable filteredTable = DefaultColumnFilter.includedColumnsTable(table, expectedTable.getTableMetaData()
        .getColumns());

    Assertion.assertEquals(new TableCompareDecorator(expectedTable), new TableCompareDecorator(filteredTable));
  }

  /**
   * Compares the content of a table with the provided dataset. The
   * columns are filtered and only columns that exist inside the
   * dataset are evaluated.
   * 
   * @param actualDataSet the actual data set
   * @param expectedDataSet the data with the expected state of the
   *        table.
   * @param modifiers the modifiers
   * @throws Exception If an error occurs during comparison.
   */
  static public void assertDataSet(IDataSet actualDataSet, IDataSet expectedDataSet, IDataSetModifier... modifiers)
      throws Exception
  {
    assertDataSet(false, actualDataSet, expectedDataSet, modifiers);
  }

  public static ITable getActualTableForExpectedTable(IDataSet actualDataSet, ITable expectedTable)
      throws DataSetException
  {
    ITable actualTable = actualDataSet.getTable(expectedTable.getTableMetaData().getTableName());
    return actualTable;
  }

  /**
   * @see AbstractDBUnitTests#assertDataSetTable(String, IDataSet)
   */
  static public void assertDataSetTable(IDataSet actualDataSet, String tableName, String expectedDataSet)
      throws DataSetException, Exception
  {
    FileInputStream stream = null;
    try
    {
      stream = new FileInputStream(expectedDataSet);
      assertDataSetTable(actualDataSet, tableName, new XmlDataSet(stream));
    }
    finally
    {
      if (stream != null)
      {
        stream.close();
      }
    }
  }

  /**
   * @param sorted determines if the tables and columns are sorted. If
   *        false the data is sorted before assertion.
   * @param actualDataSet the actual dataset
   * @param expectedDataSet the dataset with the expected state of the
   *        tables.
   * @param modifiers the optional modifiers.
   * @throws Exception If an error occurs during comparison.
   */
  public static void assertDataSet(boolean sorted, IDataSet actualDataSet, IDataSet expectedDataSet,
      IDataSetModifier[] modifiers) throws Exception
  {
    IDataSet expect = null;
    IDataSet actual = null;
    try
    {
      // Filter columns not configured
      DefaultDataSet actualFilteredDataSet = new DefaultDataSet();
      DefaultDataSet expectedFilteredDataSet = new DefaultDataSet();
      for (ITableIterator tableIterator = expectedDataSet.iterator(); tableIterator.next();)
      {
        ITable expectedTable = tableIterator.getTable();
        ITable actualTable = getActualTableForExpectedTable(actualDataSet, expectedTable);
        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable, expectedTable.getTableMetaData()
            .getColumns());

        actualFilteredDataSet.addTable(new TableCompareDecorator(filteredTable));
        expectedFilteredDataSet.addTable(new TableCompareDecorator(expectedTable));
      }
      IDataSet expectedFilteredDataSet2 = expectedFilteredDataSet;
      IDataSet actualFilteredDataSet2 = actualFilteredDataSet;
      if (modifiers != null)
      {
        expectedFilteredDataSet2 = DataSetUtil.modifyDataSet(expectedFilteredDataSet2, modifiers);
        actualFilteredDataSet2 = DataSetUtil.runDataSetFilters(actualFilteredDataSet2, modifiers);
      }
      if (sorted)
      {
        expect = createSortedDataSet(expectedFilteredDataSet2);
        actual = createSortedDataSet(actualFilteredDataSet2);

      }
      else
      {
        expect = expectedFilteredDataSet2;
        actual = actualFilteredDataSet2;
      }
      Assertion.assertEquals(expect, actual);
    }
    catch (ComparisonFailure error)
    {
      throwPrettyError(error, expect, actual);
      throw error;
    }
  }

  protected static void throwPrettyError(ComparisonFailure error, IDataSet expect, IDataSet actual)
      throws DataSetException
  {
    if (expect == null || actual == null || !dumpTablesOnError || error == null)
    {
      return;
    }
    StringBuffer act = new StringBuffer();
    StringBuffer exp = new StringBuffer();
    try
    {
      String tableName = findTable(error);
      if (tableName == null)
      {
        return;
      }

      printTable(exp, expect, tableName);
      printTable(act, actual, tableName);

    }
    catch (Throwable t)
    {
      System.err.println("Inner error occured, suppresss because this is just for debugging : " + t.toString());
    }
    if (act != null && exp != null && act.length() > 0 && exp.length() > 0)
    {
      throw new ComparisonFailure(error.toString(), exp.toString(), act.toString());
    }
  }

  private static String findTable(ComparisonFailure error)
  {
    String msg = error.getMessage();
    Pattern p = Pattern.compile(".*?table=(.*?),.*");
    Matcher m = p.matcher(msg);
    if (!m.find())
    {
      return null;
    }
    else
    {
      String table = m.group(1);
      return table;
    }
  }

  protected static String formatTables(ComparisonFailure error, IDataSet expect, IDataSet actual)
      throws DataSetException
  {
    if (expect == null || actual == null || !dumpTablesOnError)
    {
      return null;
    }
    String msg = error.getMessage();
    Pattern p = Pattern.compile(".*?table=(.*?),.*");
    Matcher m = p.matcher(msg);
    if (m.find())
    {
      return null;
    }
    else
    {
      String table = m.group(1);
      StringBuffer sb = new StringBuffer();
      sb.append("Tables of compared Datasets were ( tableName = " + table + " )\r\n");
      sb.append("--- expected ---\r\n");
      printTable(sb, expect, table);
      sb.append("--- actual ---\r\n");
      printTable(sb, actual, table);
      return sb.toString();
    }
  }

  protected static void printTable(StringBuffer sb, IDataSet dataSet, String table) throws DataSetException
  {
    ITable t = dataSet.getTable(table);
    int c = t.getRowCount();
    Column[] cols = t.getTableMetaData().getColumns();
    for (int row = 0; row < c; row++)
    {
      sb.append(row);
      sb.append("='");
      for (Column col : cols)
      {
        Object v = t.getValue(row, col.getColumnName());
        sb.append(formatCell(v));
        sb.append("','");
      }
      sb.append("\r\n");
    }
  }
  
  public static String formatCell(Object value)
  {
    if (value == null)
    {
      return "null";
    }
    if (value instanceof Date)
    {
      return DateUtil.formatDate((Date) value, "dd.MM.yyyy HH:mm:ss.SSS");
    }
    return value.toString();
  }

  private static IDataSet createSortedDataSet(IDataSet originalDataSet) throws DataSetException
  {
    //  the Flag "useComparable" must be set for all tables
    DefaultDataSet resultDataSet = new DefaultDataSet();
    for (ITableIterator i = originalDataSet.iterator(); i.next();)
    {
      SortedTable table = new SortedTable(i.getTable());
      table.setUseComparable(true);
      resultDataSet.addTable(table);
    }
    return resultDataSet;
  }
}
