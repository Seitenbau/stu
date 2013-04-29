package com.seitenbau.testing.dbunit.tester;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.NoSuchTableException;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import com.seitenbau.testing.dbunit.DatabaseTester;
import com.seitenbau.testing.dbunit.modifier.IDataSetFilter;
import com.seitenbau.testing.dbunit.modifier.IDataSetModifier;
import com.seitenbau.testing.dbunit.modifier.IDataSetReplacer;

/**
 * Util-Klasse Laden/Modifizieren von DataSet Instanzen.
 */
public class DataSetUtil
{
  /**
   * Lädt die angegebenen Datei als XML DataSet
   * 
   * @param fileName Dateiname, relativ zum projekt-root.
   * 
   * @return Das gelesene DataSet
   * 
   * @throws IOException Datei-IO Fehler.
   * 
   * @throws org.dbunit.dataset.DataSetException Fehler beim
   *         interpretieren des Inhaltes als XMLDataSet.
   */
  static public IDataSet getDataSetFromFile(String fileName) throws IOException, org.dbunit.dataset.DataSetException
  {
    return new XmlDataSet(new FileInputStream(fileName));
  }

  /**
   * Lädt die angegebenen Datei als XML DataSet. Allerdings wird als
   * 'root'- Verzeichnis der 'Classpath + clazz.package' genutzt.
   * 
   * @param clazz Class Instanz aus dem der Package Pfad ausgelesen
   *        wird.
   * 
   * @param filename Dateiname der zu ladenden XML Datei. Relativ zum
   *        'ClassPath + PackagePath'
   * 
   * @param modifiers Optimale Liste an Modifiern die nach dem Laden
   *        auf dem DataSet angewandt werden.
   * 
   * @return Das geladene DataSet
   * 
   * @throws Exception Fehler beim Laden.
   */
  static public IDataSet getDataSetFromClasspath(Class<?> clazz, String filename, IDataSetModifier... modifiers)
      throws Exception
  {
    String packageName = clazz.getPackage().getName();
    packageName = packageName.replace('.', '/');
    String resUrl = "/" + packageName + "/" + filename;
    if (filename.substring(0, 1).equals("/"))
    {
      resUrl = filename;
    }
    URL url = clazz.getResource(resUrl);
    if (url == null)
    {
      throw new FileNotFoundException("Datei " + resUrl + " existiert nicht");
    }
    XmlDataSet dataSet = new XmlDataSet(clazz.getResourceAsStream(resUrl));

    return DataSetUtil.modifyDataSet(dataSet, modifiers);
  }

  /**
   * Verarbeitet die optimale Liste an Modifiern auf dem gegebenen
   * DataSet.
   * 
   * @param orginalDataSet Das DataSet auf welches die Modifier
   *        angewandt werden sollen.
   * 
   * @param modifiers Optimale Liste an Modifiern.
   * 
   * @return Ein modifiziertes Dataset oder falls keine Modifier
   *         angegeben waren bzw. {@code null} einfach den
   *         orginalDataSet.
   * 
   * @throws Exception Fehler beim Ausführen der Modifier.
   */
  static public IDataSet modifyDataSet(IDataSet orginalDataSet, IDataSetModifier... modifiers) throws Exception
  {
    if (modifiers == null)
    {
      return orginalDataSet;
    }
    // Verarbieten der Replacements
    ReplacementDataSet replacedDataset = new ReplacementDataSet(orginalDataSet);
    for (IDataSetModifier modifier : modifiers)
    {
      if (modifier instanceof IDataSetReplacer)
      {
        IDataSetReplacer dataSetReplacer = (IDataSetReplacer) modifier;
        replacedDataset.addReplacementObject(dataSetReplacer.getMarkerString(), dataSetReplacer.getReplacementObject());
      }

    }
    IDataSet current = runDataSetFilters(replacedDataset, modifiers);

    return current;
  }

  /**
   * Entfernt im übergebenen DataSet den Inhalt aus den angegebenen
   * Tabellen.
   * 
   * @param theDataSet Das DataSet in welchem die Tabellen zu leeren
   *        sind. Nicht {@code null}
   * 
   * @param tableName Optimale Liste an Tabellennamen deren Inhalt
   *        entfernt werden soll.
   * @return Das modifizeirte DataSet mit den geleerten Tabellen.
   *         Wurde keine Liste an Tabellennamen übergeben, oder ist
   *         diese {@code null} dann wird der unmodifizierte DataSet
   *         zurückgeliefert.
   * 
   * @throws Exception Fehler die Fähren der Filterung aufgetreten
   *         sind.
   */
  public static IDataSet filterOutTableRows(IDataSet theDataSet, String... tableName) throws Exception
  {
    if (tableName == null)
    {
      return theDataSet;
    }
    DefaultDataSet ds = new DefaultDataSet();
    for (ITableIterator iter = theDataSet.iterator(); iter.next();)
    {
      String tabName = iter.getTable().getTableMetaData().getTableName();
      boolean specialTreated = false;
      for (String name : tableName)
      {
        if (tabName.equals(name))
        {
          ds.addTable(new DefaultTable(iter.getTable().getTableMetaData()));
          specialTreated = true;
          break;
        }
      }
      if (!specialTreated)
      {
        ds.addTable(iter.getTable());
      }
    }
    return ds;
  }

  /**
   * Schreibt das gegebene DataSet in eine Datei.
   * 
   * @param snapshot Der zu speichernde Abzug
   * 
   * @param fileName Die Ziel-Datei
   * 
   * @throws DataSetException Fehler im DataSet
   * 
   * @throws IOException Fehler beim Schreiben der Datei.
   */
  public static void saveDataSet(IDataSet snapshot, String fileName) throws DataSetException, IOException
  {
    OutputStream out = new FileOutputStream(fileName);
    XmlDataSet.write(snapshot, out);
    out.close();
  }

  /**
   * Speichert die gegebene Datenbank in eine XML Datei.
   * 
   * @param driver
   * @param url
   * @param user
   * @param password
   */
  public static void dumpDatabase(String driver, String url, String user, String password)
  {
    DatabaseTesterBase<DatabaseTester> tester = new DatabaseTester(driver, url, user, password);
    IDataSet snapshot;
    try
    {
      snapshot = tester.createDatabaseSnapshot();
      DataSetUtil.saveDataSet(snapshot, "dump.xml");
    }
    catch (Exception e1)
    {
      e1.printStackTrace();
    }
  }

  /**
   * Speichert die gegebene Datenbank in eine XML Datei.
   * 
   * @param tester Datenbank deren Inhalt gedumpt wird.
   * 
   * @param fileName Dateiename der zu speichernden Datei.
   * 
   * @throws Exception bei einem Fehler.
   */
  public static void dumpDatabase(DatabaseTesterBase<?> tester, String fileName) throws Exception
  {
    IDataSet snapshot = tester.createDatabaseSnapshot();
    DataSetUtil.saveDataSet(snapshot, fileName);
  }

  public static IDataSet syncOder(IDataSet dataset, List<String> targetOrder) throws DataSetException
  {
    DefaultDataSet result = new DefaultDataSet();
    Map<String, Boolean> done = new HashMap<String, Boolean>();
    for (String tableName : targetOrder)
    {
      try
      {
        ITable table = dataset.getTable(tableName);
        result.addTable(table);
        done.put(tableName, true);
      }
      catch (NoSuchTableException e)
      {
        System.out.println("No Table : " + tableName);
      }
    }
    for (ITableIterator iter = dataset.iterator(); iter.next();)
    {
      String name = iter.getTable().getTableMetaData().getTableName();
      if (!done.containsKey(name))
      {
        result.addTable(iter.getTable());
      }
    }
    return result;
  }

  public static void printOrder(IDataSet dataset) throws DataSetException
  {
    int i = 1;
    for (ITable tab : dataset.getTables())
    {
      System.out.println(i++ + " " + tab.getTableMetaData().getTableName());
    }
  }

  public static IDataSet runDataSetFilters(IDataSet orginalDataSet, IDataSetModifier[] modifiers) throws Exception
  {
    if (modifiers == null)
    {
      return orginalDataSet;
    }
    ReplacementDataSet replacedDataset = new ReplacementDataSet(orginalDataSet);
    // Verarbeiten von Filtern
    IDataSet current = replacedDataset;
    for (IDataSetModifier modifier : modifiers)
    {
      if (modifier instanceof IDataSetFilter)
      {
        IDataSetFilter filter = (IDataSetFilter) modifier;
        current = filter.filter(current);
      }
    }
    return current;
  }

}
