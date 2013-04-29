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
   * Assert Tabelle vergleicht eine Tabelle mit den Daten die in einer
   * XML Datei spezifziert wurden.
   * 
   * @param tableName Der Tabellennamen. Darf nicht Null sein und
   *        nicht leer.
   * 
   * @param expectedDataSetFile Die XML Datei mit dem vergleichs
   *        DataSet, darf nicht null oder leer sein.
   * 
   * @throws Exception Wird geworfen wenn ein Fehler beim Vergleich
   *         aufgtetten ist.
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
   * Assert Tabelle vergleicht eine Tabelle mit den Daten die in einer
   * XML Datei spezifziert wurden.
   * 
   * @param tableName Der Tabellennamen. Darf nicht Null sein und
   *        nicht leer.
   * 
   * @param expectedDataSetFile Die XML Datei mit dem vergleichs
   *        DataSet, darf nicht null oder leer sein.
   * 
   * @throws Exception Wird geworfen wenn ein Fehler beim Vergleich
   *         aufgtetten ist.
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
   * Assert Tabelle vergleicht eine Tabelle mit den Daten die in einer
   * XML Datei spezifziert wurden. Weiter wird durch Kommas getrennt
   * die felder die für den Verglaich relevant sind angegeben.
   * 
   * @param tableName Der Tabellen Name. Darf nicht Null oder leer
   *        sein.
   * @param expectedDataSetFile Die Datei die das XML DataSet für den
   *        Vergleich beinhaltet. Darf nicht Null oder leer sein.
   * @param fields Die Felder die für den Vergleich relvant sind durch
   *        Kommas getrennt. Darf nicht Null oder leer sein.
   * @throws Exception Wird geworfen wenn ein Fehler beim Vergleich
   *         aufgetretten ist.
   */
  static public void assertTable(IDatabaseConnection databaseConnection, String tableName, String expectedDataSetFile,
      String... fields) throws Exception
  {
    // Load expected data from an XML dataset
    IDataSet expectedDataSet = new XmlDataSet(new FileInputStream(expectedDataSetFile));
    assertTable(databaseConnection, tableName, expectedDataSet, fields);
  }

  /**
   * Methode erstellt aus einem Array von Strings Einen Komma
   * getrennten String aus createSQLFields("NAME", "Title") wird =>
   * "Name, Title"
   * 
   * @param fields Die einzelenen String Felder
   * @return Als Rückgabe wird ein Komma getrennter String zurück
   *         geben der die übergeben Felder beinhaltet.
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
   * Assert vergleicht eine Tabelle mit Test Daten aus einem DataSet.
   * Wird genutzt wenn nicht alle Felder genutzt werden sollen.
   * 
   * @param tableName Name der zuvergleichenden Tabelle
   * @param expectedDataSet Die Testdaten auf die Tabelle geprüft
   *        wird.
   * @param fields Die Felder für den Filter.
   * @throws Exception Wird geworfen wenn beim Vergleich ein Fehler
   *         aufgtretten ist oder die Tabelle nicht die Testdaten
   *         beinhaltet.
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
   * Assert Tabelle vergleicht eine Tabelle mit den Daten die in einer
   * XML Datei spezifziert wurden.
   * 
   * @param tableName Der Tabellennamen. Darf nicht Null sein und
   *        nicht leer.
   * @param expectedDataSetFile Die XML Datei mit dem vergleichs
   *        DataSet, darf nicht null oder leer sein.
   * @throws Exception Wird geworfen wenn ein Fehler beim Vergleich
   *         aufgtetten ist.
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
   * Überprüft eine Tabelle in der DB mit dem übergebenen DatenSet. Es
   * werden dabei die Spalten gefilter und nur die im DatSet geprüft.
   * 
   * @param tableName Der Name der Tabelle.
   * @param expectedDataSet das Daten mit dem erwartet Zustand der
   *        Tabelle
   * @throws Exception Wird geworfen wenn ein Fehler beim Vergleich
   *         auftritt.
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
   * Überprüft alle Tabellen in der DB gegen das übergebenen DatenSet.
   * Es werden nur die Spalten geprüft welche auch im DatSet vorhanden
   * sind.
   * 
   * @param expectedDataSet das Daten mit dem erwartet Zustand der
   *        Tabelle
   * @param replacers
   * @throws Exception Wird geworfen wenn ein Fehler beim Vergleich
   *         auftritt.
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
   * @param sorted die Tabellen und Spalten sind nicht sortiert und
   *        müssen zuerst sortiert werden
   * @param actualDataSet
   * @param expectedDataSet die Daten mit dem erwartet Zustand der
   *        Tabellen
   * @param modifiers Modifikatoren
   * @throws Exception
   */
  public static void assertDataSet(boolean sorted, IDataSet actualDataSet, IDataSet expectedDataSet,
      IDataSetModifier[] modifiers) throws Exception
  {
    // Ausfiltern nicht konfigurierter Spalten
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
    // das Flag "useComparable" muss für alle Tabellen gesetzt werden
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
