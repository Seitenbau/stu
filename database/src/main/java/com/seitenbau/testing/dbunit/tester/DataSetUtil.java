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
 * Util class to load/modify dataset instances.
 */
public class DataSetUtil
{
  /**
   * Load the given file as XML dataset.
   * 
   * @param fileName Filename, relative to project root.
   * 
   * @return The dataset read from the file.
   * 
   * @throws IOException File IO failure.
   * 
   * @throws org.dbunit.dataset.DataSetException FError while trying
   *         to interpret content as XMLDataset.
   */
  static public IDataSet getDataSetFromFile(String fileName) throws IOException, org.dbunit.dataset.DataSetException
  {
    return new XmlDataSet(new FileInputStream(fileName));
  }

  /**
   * Load the given file as XML dataset. As root directory hte
   * 'classpath + clazz.package' is used.
   * 
   * @param clazz the class instance the package path is read from.
   * 
   * @param filename Filename of the XML file that should be read.
   *        Relative to 'classpath + packagepath'.
   * 
   * @param modifiers Optional list of modifiers that should be
   *        applied to the dataset after it was read.
   * 
   * @return The read dataset.
   * 
   * @throws Exception Error while reading file.
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
      throw new FileNotFoundException("File " + resUrl + " does not exist.");
    }
    XmlDataSet dataSet = new XmlDataSet(clazz.getResourceAsStream(resUrl));

    return DataSetUtil.modifyDataSet(dataSet, modifiers);
  }

  /**
   * Processes the optional list of modifiers on the given dataset.
   * 
   * @param orginalDataSet The dataset the modifiers should be applied
   *        to.
   * 
   * @param modifiers Optional list of modifiers.
   * 
   * @return A modified dataset. If no modifiers exist or {@code null}
   *         is provided the original dataset without modifications is
   *         returned.
   * 
   * @throws Exception Error while processing modifier.
   */
  static public IDataSet modifyDataSet(IDataSet orginalDataSet, IDataSetModifier... modifiers) throws Exception
  {
    if (modifiers == null)
    {
      return orginalDataSet;
    }
    // Process replacements
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
   * Removes the content from the specified tables for the given
   * dataset.
   * 
   * @param theDataSet The dataset in which tables should be
   *        truncated. Not {@code null}.
   * 
   * @param tableName Optional list of table names that should be
   *        truncated.
   * @return The modified dataset with the truncated tables. If not
   *         list of tablenames is provided or list {@code null} the
   *         datset is returned without modifications.
   * 
   * @throws Exception Error occurred while dataset was modified.
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
   * Writes the given dataset into a file.
   * 
   * @param snapshot The dataset.
   * 
   * @param fileName The target file.
   * 
   * @throws DataSetException Error inside the dataset.
   * 
   * @throws IOException Error while writing file.
   */
  public static void saveDataSet(IDataSet snapshot, String fileName) throws DataSetException, IOException
  {
    OutputStream out = new FileOutputStream(fileName);
    XmlDataSet.write(snapshot, out);
    out.close();
  }

  /**
   * Stores the entire database into a XML file.
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
   * Stores the entire database into a XML file.
   * 
   * @param tester Database that should be dumped.
   * 
   * @param fileName Name of the file.
   * 
   * @throws Exception If an error occurs while storing the database to a file.
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
    for (String tabName : dataset.getTableNames())
    {
      ITable tab = dataset.getTable(tabName);
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
    // Process filers
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
