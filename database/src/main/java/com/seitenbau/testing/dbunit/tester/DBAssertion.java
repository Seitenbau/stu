package com.seitenbau.testing.dbunit.tester;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

import com.seitenbau.testing.dbunit.internal.DateColumnCompareTableDecorator;
import com.seitenbau.testing.dbunit.internal.DateColumnRangeTableDecorator;
import com.seitenbau.testing.dbunit.internal.DateComparator;
import com.seitenbau.testing.dbunit.internal.TableCompareDecorator;
import com.seitenbau.testing.dbunit.modifier.IDataSetModifier;

public class DBAssertion
{
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
   * @param sorted determines if the tables and columns are sorted. If false the data is sorted before assertion.
   * @param actualDataSet the actual dataset
   * @param expectedDataSet the dataset with the expected state of the tables.
   * @param modifiers the optional modifiers.
   * @throws Exception If an error occurs during comparison.
   */
  public static void assertDataSet(boolean sorted, IDataSet actualDataSet, IDataSet expectedDataSet,
      IDataSetModifier[] modifiers) throws Exception
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
      Assertion
          .assertEquals(createSortedDataSet(expectedFilteredDataSet2), createSortedDataSet(actualFilteredDataSet2));
    }
    else
    {
      Assertion.assertEquals(expectedFilteredDataSet2, actualFilteredDataSet2);
    }

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
