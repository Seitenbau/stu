package com.seitenbau.testing.dbunit.solr;

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.datatype.DataType;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seitenbau.testing.dbunit.SortConfig;
import com.seitenbau.testing.dbunit.extend.DbUnitDatasetFactory;
import com.seitenbau.testing.dbunit.modifier.IDataSetModifier;
import com.seitenbau.testing.dbunit.tester.DBAssertion;

/**
 * Rule für das Einspielen der Testdaten in Solr.
 * 
 * Die <b>solrUrl</b> muss gesetzt sein, und falls nicht alle Felder
 * in Solr für einen Vergleich herangezogen werden sollen, ebenfalls
 * das Attribut <b>tableMetadata</b>.
 * 
 * @author rnoerenberg
 * @version $Id: SolrTesterRule.java 97363 2012-12-04 12:03:58Z
 *          rnoerenberg $
 * 
 */
public class SolrTesterRule implements MethodRule
{
  /** Der Standard-Suchstring für alle Dokumente im Solr-Index. */
  private static final String SOLR_DEFAULT_QUERY = "*:*";

  private static final Logger LOG = LoggerFactory.getLogger(SolrTesterRule.class);

  /**
   * Class der 'Umgebung' Benötigt zum finden des korrekten Packages
   * beim Laden von XML Dateien.
   */
  private Class<?> fClazz;

  /**
   * Das Objekt der Testklasse, in die Objekte injected werden.
   */
  private Object _target;

  /** Der SolrServer für die Verbindung zu Solr. */
  private SolrServer solrServer;

  private String fUrl;

  private List<IDataSetModifier> fDefaultModifierList = new ArrayList<IDataSetModifier>();

  private IDataSet defaultDataset;

  private DbUnitDatasetFactory _defaultDatasetFactory;

  private IDataSet fLastInsertedDataSet;

  protected SortConfig[] _defaultSortConfig;

  private String dataSetTableName;

  /**
   * Die Metadaten der Tabelle, die verglichen werden sollen.
   */
  private DefaultTableMetaData dataSetTableMetadata;

  /**
   * Erzeugt eine Instanz von {@link SolrTesterRule} und gibt diese
   * zurück.
   * 
   * @return die erzeugte Instanz.
   */
  public static SolrTesterRule create()
  {
    return new SolrTesterRule();
  }

  /**
   * Setzt den Namen der Tabelle, die für das Erstellen des DataSets
   * beim Auslesen der Daten aus Solr genutzt wird.
   * 
   * <p>
   * Achtung: Ist der Parameter <b>tableMetadata</b> gesetzt, wird
   * dieser Parameter ignoriert.
   * </p>
   * 
   * @param tableName der Name der Tabelle.
   * @return
   */
  public SolrTesterRule tableName(String tableName)
  {
    this.dataSetTableName = tableName;
    return this;
  }

  /**
   * Erstellt aus dem Tabellennamen und den Spalten die zu
   * verwendenden Tabellen-Metadaten.
   * 
   * @param tableName der Tabellenname.
   * @param columns die Spalten der Tabelle.
   * @return die aktuelle Instanz.
   */
  public SolrTesterRule tableMetadata(String tableName, Column[] columns)
  {
    this.dataSetTableMetadata = new DefaultTableMetaData(tableName, columns);
    return this;
  }

  /**
   * Setzt die URL, die für den Aufbau der Verbindung zu Solr genutzt
   * wird.
   * 
   * @param solrUrl die URL zur Solr-Instanz.
   * @return die aktuelle Instanz.
   */
  public SolrTesterRule solrUrl(String solrUrl)
  {
    if (StringUtils.isBlank(solrUrl))
    {
      throw new IllegalArgumentException("Parameter solrUrl muss " + "gesetzt und darf nicht leer sein!");
    }
    this.fUrl = solrUrl;
    init(null);
    return this;
  }

  /**
   * Fügt die übergebenen Modifier der Liste an Default-Modifiern
   * hinzu.
   * 
   * @param defaultModifiers Die hinzuzufügenden Modifier.
   * @return die aktuelle Instanz.
   */
  public SolrTesterRule defaultModifiers(IDataSetModifier... defaultModifiers)
  {
    addDefaultModifier(defaultModifiers);
    return this;
  }

  private void init(Class<?> clazz)
  {
    if (clazz != null)
    {
      fClazz = clazz;
    }
    else
    {
      doMagicClazzFind();
    }
  }

  public Statement apply(final Statement base, final FrameworkMethod method, Object target)
  {
    _target = target;
    return new Statement()
    {
      @Override
      public void evaluate() throws Throwable
      {
        before(method);
        try
        {
          base.evaluate();
          after(method);
        }
        finally
        {
          close();
        }
      }
    };
  }

  protected void before(FrameworkMethod method) throws Exception
  {
    SolrSetup annotation = method.getAnnotation(SolrSetup.class);
    if (annotation == null)
    {
      SolrSetup classAnnotation = getClazz().getAnnotation(SolrSetup.class);
      if (classAnnotation == null)
      {
        tryDefaultCleanInsert();
        return;
      }
      annotation = classAnnotation;
    }
    if (annotation.suppressInsert())
    {
      return;
    }
    else if (annotation.prepare() != null && !annotation.prepare().equals(DbUnitDatasetFactory.class))
    {
      doCleanInsert(annotation.prepare());
    }
    else
    {
      tryDefaultCleanInsert();
    }
  }

  protected void after(FrameworkMethod method) throws Exception
  {
    SolrSetup annotation = method.getAnnotation(SolrSetup.class);
    if (annotation == null)
    {
      SolrSetup classAnnotation = getClazz().getAnnotation(SolrSetup.class);
      if (classAnnotation == null)
      {
        return;
      }
      annotation = classAnnotation;
    }
    if (annotation.assertNoModification())
    {
      try
      {
        assertDataBaseStillTheSame(getSortConfig());
      }
      catch (AssertionError error)
      {
        throw new AssertionError("DatabasetTesterRule @after failed : " + error.toString());
      }
    }
  }

  /**
   * Schließt die aktuelle Verbindung, falls nötig.
   * 
   * @throws Exception Fehler die beim Schließen aufgetreten sind.
   */
  protected void close() throws Exception
  {
    this.solrServer = null;
  }

  protected Class<?> getClazz()
  {
    if (fClazz == null)
    {
      throw new IllegalStateException("Das Feld fClass ist null. "
          + "Vermutlich wurde dem Konstrkutor keine Class Instanz übergeben.");
    }
    return fClazz;
  }

  /**
   * Versucht die Class-Instanz zu finden von der Aufrufenden Klasse.
   * <p>
   * Hierzu wird der Stack durchlaufen bis eine nicht von
   * DatabaseTester abgeleitete Klasse gefunden wird. Daher schlägt
   * dieser Code fehl wenn die Test-Klasse direkt oder indirekt von
   * DatabaseTester abgeleitet ist!
   * </p>
   */
  protected void doMagicClazzFind()
  {
    Class<?> clazz = getCallerClassViaMagic();
    setClazz(clazz);
  }

  protected static boolean isSubclassFrom(Class<?> thisClazz, Class<?> isOfSubclass)
  {
    try
    {
      thisClazz.asSubclass(isOfSubclass);
      return true;
    }
    catch (ClassCastException cl)
    {
      return false;
    }
  }

  /**
   * Setzen der Clazz Instanz für das Detektieren das Ziel-Package
   * Verzeichnisses
   * 
   * @param clazz Die Class Instanz
   */
  protected void setClazz(Class<?> clazz)
  {
    fClazz = clazz;
  }

  private static synchronized Class<?> getCallerClassViaMagic()
  {
    final String method = "getCallerClassViaMagic() : ";
    LOG.trace(method + "Start");

    Class<?> potentialClazz = null;
    try
    {
      StackTraceElement[] stackTrace = new Throwable().getStackTrace();
      ClassLoader cl = SolrTesterRule.class.getClassLoader();
      for (StackTraceElement item : stackTrace)
      {
        Class<?> clazz = cl.loadClass(item.getClassName());
        if (!isSubclassFrom(clazz, SolrTesterRule.class))
        {
          if (potentialClazz == null)
          {
            potentialClazz = clazz;
          }
          else
          {
            if (isSubclassFrom(clazz, potentialClazz))
            {
              potentialClazz = clazz;
            }
            else
            {
              break;
            }
          }
        }
      }
    }
    catch (Throwable t)
    {
      LOG.debug(method + "Verschluckt.");
      // verschlucken
    }
    return potentialClazz;
  }

  protected void doCleanInsert(Class<? extends DbUnitDatasetFactory> prepare) throws Exception
  {
    DbUnitDatasetFactory factory = newInstance(prepare);
    IDataSet dataset = factory.createDBUnitDataSet();
    cleanInsert(dataset);
    trySetAnnotatedField(factory);
  }

  protected void doCleanInsert(IDataSet dataset) throws Exception
  {
    cleanInsert(dataset);
  }

  protected void tryDefaultCleanInsert() throws Exception
  {
    if (defaultDataset != null)
    {
      doCleanInsert(defaultDataset);
      return;
    }
    if (_defaultDatasetFactory instanceof DbUnitDatasetFactory)
    {
      doCleanInsert(_defaultDatasetFactory.createDBUnitDataSet());
      trySetAnnotatedField(_defaultDatasetFactory);
      return;
    }
  }

  /**
   * Hilfsmethode welche das Laden des DataSets und ein CLEAN_INSERT
   * wrappt.
   * 
   * @param dataset
   * 
   * @param modifiers Zusätzlich werden die Default-modifier zu
   * @throws Exception
   */
  public void cleanInsert(IDataSet dataset, IDataSetModifier... modifiers) throws Exception
  {
    final String method = "cleanInsert() : ";
    LOG.trace(method + "Start");

    trySetAnnotatedField(dataset);

    // connect to solr server
    this.solrServer = getConnection();

    // clean solr index
    cleanSolrIndex();

    // prepare test data
    LOG.debug(method + "Start to prepare test data for solr");
    List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
    ITableIterator it = dataset.iterator();
    while (it.next())
    {
      ITable table = it.getTable();
      int rowCount = table.getRowCount();
      ITableMetaData tableMetadata = table.getTableMetaData();
      Column[] columns = tableMetadata.getColumns();
      if (columns != null)
      {
        for (int i = 0; i < rowCount; i++)
        {
          SolrInputDocument doc = new SolrInputDocument();
          for (Column column : columns)
          {
            String columnName = column.getColumnName();
            doc.setField(columnName, table.getValue(i, columnName));
          }
          docList.add(doc);
        }
      }
    }
    // insert test data
    LOG.debug(method + "Start to insert solr test data");
    if (!docList.isEmpty())
    {
      this.solrServer.add(docList);
      this.solrServer.commit();
    }

    LOG.debug(method + "Set current dataset as last inserted dataset.");
    setLastInsertedDataSet(dataset);
  }

  protected void trySetAnnotatedField(Object set) throws Exception
  {
    Class<?> clazz = _target.getClass();
    trySetAnnotatedField(_target, clazz, set);
  }

  protected void trySetAnnotatedField(Object target, Class<?> clazz, Object loadedDS) throws Exception
  {
    for (Field field : clazz.getDeclaredFields())
    {
      InjectSolrDataSet anno = field.getAnnotation(InjectSolrDataSet.class);
      if (anno != null)
      {
        field.setAccessible(true);
        if (loadedDS == null)
        {
          field.set(target, null);
          return;
        }
        else if (field.getType().isAssignableFrom(loadedDS.getClass()))
        {
          field.set(target, loadedDS);
          return;
        }
      }
    }
    if (!clazz.getSuperclass().equals(Object.class))
    {
      trySetAnnotatedField(target, clazz.getSuperclass(), loadedDS);
    }
  }

  /**
   * Löscht alle Inhalte aus dem Solr-Index.
   * 
   * @throws Exception
   */
  public void truncate() throws Exception
  {
    final String method = "truncate() : ";
    LOG.trace(method + "Start");

    // connect to solr server
    this.solrServer = getConnection();

    // clean solr index
    cleanSolrIndex();

    LOG.trace(method + "End");
  }

  /**
   * Hilfsmethode welche das Laden des DataSets und ein CLEAN_INSERT
   * wrappt.
   * 
   * @param datasetFactory Factory welche das eigentliche Dataset
   *        zurückliefert
   * 
   * @param modifiers Zusätzlich werden die Default-modifier zu
   * @throws Exception
   */
  public void cleanInsert(DbUnitDatasetFactory datasetFactory, IDataSetModifier... modifiers) throws Exception
  {
    cleanInsert(datasetFactory.createDBUnitDataSet(), modifiers);
    trySetAnnotatedField(datasetFactory);
  }

  protected <X> X newInstance(Class<X> prepare)
  {
    try
    {
      return prepare.newInstance();
    }
    catch (InstantiationException e)
    {
      throw new RuntimeException(e);
    }
    catch (IllegalAccessException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Setzt das Default-Datenset.
   * 
   * @param datasetFactory das Default-Datenset.
   * @return
   */
  public SolrTesterRule setDefaultDataSet(DbUnitDatasetFactory datasetFactory)
  {
    _defaultDatasetFactory = datasetFactory;
    defaultDataset = datasetFactory.createDBUnitDataSet();
    return this;
  }

  /**
   * Setzt das übergebene Datenset als zuletzt eingespieltes Datenset.
   * 
   * @param loadedDS die DataSet Instanz des als letzten eingespielten
   *        DataSets.
   */
  protected void setLastInsertedDataSet(IDataSet loadedDS) throws Exception
  {
    fLastInsertedDataSet = loadedDS;
  }

  /**
   * Methoden zum Zugriff auf die default-Modifier.
   * 
   * @param modifiers Optimale zusätzliche Liste and Modifiern
   * 
   * @return Liefert die optimal übergebenen Modifier sowie die evtl.
   *         gesetzen Default Modifier als Array zurück.
   */
  public IDataSetModifier[] getModifiers(IDataSetModifier... modifiers)
  {
    if (fDefaultModifierList == null)
    {
      return modifiers;
    }
    List<IDataSetModifier> list = new ArrayList<IDataSetModifier>();
    list.addAll(fDefaultModifierList);
    if (modifiers != null)
    {
      list.addAll(Arrays.asList(modifiers));
    }
    return list.toArray(new IDataSetModifier[] {});
  }

  /**
   * Fügt einen Modifier zur Liste der Default modifier hinzu.
   * <p>
   * Default Modifier werden immer beim Laden von Datensätzen( bspw.
   * über {@link #getDataSet(String, IDataSetModifier...)} )
   * ausgeführt.
   * </p>
   * 
   * @param aModifier Der hinzuzufügende Modifier.
   */
  public SolrTesterRule addDefaultModifier(IDataSetModifier aModifier)
  {
    if (fDefaultModifierList == null)
    {
      fDefaultModifierList = new ArrayList<IDataSetModifier>();
    }
    fDefaultModifierList.add(aModifier);
    return this;
  }

  /**
   * Fügt einen Modifier zur Liste der Default modifier hinzu.
   * <p>
   * Default Modifier werden immer beim Laden von Datensätzen( bspw.
   * über {@link #getDataSet(String, IDataSetModifier...)} )
   * ausgeführt.
   * </p>
   * 
   * @param modifiers Die hinzuzufügenden Modifier.
   */
  public SolrTesterRule addDefaultModifier(IDataSetModifier... modifiers)
  {
    if (modifiers != null)
    {
      for (IDataSetModifier aModifier : modifiers)
      {
        addDefaultModifier(aModifier);
      }
    }
    return this;
  }

  /**
   * Set the default sorting used when Comparing via the Solr Tester
   * rule. Not used in 'normal' compares.
   * 
   * @param defaultSortConfig the new SortConfig
   * @return this
   */
  public SolrTesterRule setDefaultSortConfig(SortConfig... defaultSortConfig)
  {
    _defaultSortConfig = defaultSortConfig;
    return this;
  }

  /**
   * Override method or set default sort config with
   * {@link #setDefaultSortConfig(SortConfig...)} to specify sorting
   * format.
   * 
   * @return sorting configuration
   * @throws DataSetException
   */
  protected SortConfig[] getSortConfig() throws DataSetException
  {
    return _defaultSortConfig;
  }

  /**
   * Vergleicht die Datenbank mit dem DataSet welches als letztes über
   * die cleanInsert Methode eingefügt wurde. Abgeleitete Klassen
   * könnten den letzten Datensatz aber evlt. auch geändert haben
   * durch einen Aufruf von {@link #setLastInsertedDataSet(IDataSet)}
   * 
   * @throws Exception Fehler im Vergleich
   */
  public void assertDataBaseStillTheSame() throws Exception
  {
    assertDataBaseStillTheSame(null);
  }

  /**
   * Vergleicht die Datenbank mit dem DataSet welches als letztes über
   * die cleanInsert Methode eingefügt wurde. Abgeleitete Klassen
   * könnten den letzten Datensatz aber evlt. auch geändert haben
   * durch einen Aufruf von {@link #setLastInsertedDataSet(IDataSet)}
   * 
   * @throws Exception Fehler im Vergleich
   */
  public void assertDataBaseStillTheSame(SortConfig[] sortConfig) throws Exception
  {
    // Leeres DefaultDataSet erstellen.
    IDataSet expectedDataset = new DefaultDataSet();
    if (fLastInsertedDataSet != null)
    {
      expectedDataset = fLastInsertedDataSet;
    }
    else if (defaultDataset != null)
    {
      expectedDataset = defaultDataset;
    }
    else if (_defaultDatasetFactory != null)
    {
      expectedDataset = _defaultDatasetFactory.createDBUnitDataSet();
    }
    if (sortConfig != null)
    {
      assertDataBaseSorted(expectedDataset, sortConfig, getModifiers());
    }
    else
    {
      assertDataBaseSorted(expectedDataset, getModifiers());
    }
  }

  /**
   * Methode zum vergleichen eines Datasets mit der aktuellen
   * Datenbank
   * 
   * @param expectedDataSet das Datenset mit den in Solr erwarteten
   *        Daten.
   * @param modifiers Die hinzuzufügenden Modifier.
   * @throws Exception
   */
  public void assertDataBase(IDataSet expectedDataSet, IDataSetModifier... modifiers) throws Exception
  {
    DBAssertion.assertDataSet(createDatabaseSnapshot(), expectedDataSet, getModifiers(modifiers));
  }

  /**
   * Methode zum vergleichen eines Datasets mit der aktuellen
   * Datenbank.
   * 
   * @param factory die DatasetFactory, die das Datenset mit den in
   *        Solr erwarteten Daten enthält.
   * @param modifiers Die hinzuzufügenden Modifier.
   * @throws Exception
   */
  public void assertDataBase(DbUnitDatasetFactory factory, IDataSetModifier... modifiers) throws Exception
  {
    assertDataBase(factory.createDBUnitDataSet(), getModifiers(modifiers));
  }

  /**
   * Vergleicht das übergebene Dataset mit den in der Datenbank
   * vorhandenen Daten. Zuvor werden die Daten entsprechend der
   * übergebenen Sortierung sortiert.
   * 
   * @param expectedDataSet das Datenset mit den zu erwartenden Daten.
   * @param config die Sortierung.
   * @param modifiers Die hinzuzufügenden Modifier.
   * @throws Exception
   */
  public void assertDataBaseSorted(IDataSet expectedDataSet, SortConfig[] config, IDataSetModifier... modifiers)
      throws Exception
  {
    DBAssertion.assertDataSet(sortTables(createDatabaseSnapshot(), config), sortTables(expectedDataSet, config),
        getModifiers(modifiers));
  }

  /**
   * Vergleicht das übergebene Dataset mit den in der Datenbank
   * vorhandenen Daten. Zuvor werden die Daten entsprechend sortiert.
   * 
   * @param expectedDataSet das Datenset mit den zu erwartenden Daten.
   * @param modifiers Die hinzuzufügenden Modifier.
   * @throws Exception
   */
  public void assertDataBaseSorted(IDataSet expectedDataSet, IDataSetModifier... modifiers) throws Exception
  {
    DBAssertion.assertDataSet(true, createDatabaseSnapshot(), expectedDataSet, getModifiers(modifiers));
  }

  /**
   * Sortiert ein DataSet anhand der übergebenen Sortierungsangaben.
   * 
   * @param ds das DataSet.
   * @param config die Sortierung.
   * @return das sortierte DataSet, das Tabellen vom Typ SortTable
   *         enthält.
   */
  public IDataSet sortTables(IDataSet ds, SortConfig[] config)
  {
    if (config == null)
    {
      return ds;
    }
    DefaultDataSet dataSet = new DefaultDataSet();
    try
    {
      for (String tableName : ds.getTableNames())
      {
        SortConfig sortConfig = null;
        for (SortConfig cfg : config)
        {
          if (cfg.getTablename().equals(tableName))
          {
            sortConfig = cfg;
          }
        }
        ITable table = ds.getTable(tableName);
        ITable wrapped;
        if (sortConfig == null)
        {
          wrapped = createSortTable(table, null);
        }
        else
        {
          wrapped = createSortTable(table, sortConfig.getColumnOrder());
        }
        dataSet.addTable(wrapped);
      }
    }
    catch (DataSetException e)
    {
      throw new RuntimeException(e);
    }

    return dataSet;
  }

  protected ITable createSortTable(ITable table, String[] columnSortOrder) throws DataSetException
  {
    SortedTable table2;
    if (columnSortOrder == null)
    {
      table2 = new SortedTable(table);
    }
    else
    {
      table2 = new SortedTable(table, columnSortOrder);
    }
    table2.setUseComparable(true);
    return table2;
  }

  /**
   * Erzeugt ein DataSet mit einem live Abzug der Kompletten Datenbank
   * 
   * @return Der Datenbank-dump
   */
  public IDataSet createDatabaseSnapshot() throws Exception
  {
    final String method = "createDatabaseSnapshot() : ";
    LOG.trace(method + "Start");
    // connect to solr server
    this.solrServer = getConnection();

    LOG.debug(method + "Lese vorhandene Daten aus Solr.");
    SolrDocumentList docListInSolr = this.solrServer.query(new SolrQuery(SOLR_DEFAULT_QUERY)).getResults();

    LOG.debug(method + "Erzeuge DataSet.");
    DefaultDataSet dataset = new DefaultDataSet();
    DefaultTable solrDocumentTable = getDefaultTable(docListInSolr);
    insertRowsInTable(solrDocumentTable, docListInSolr);
    dataset.addTable(solrDocumentTable);
    LOG.debug(method + "DataSet erzeugt.");

    LOG.trace(method + "End");
    return dataset;
  }

  private DefaultTable getDefaultTable(SolrDocumentList docListInSolr)
  {
    final String method = "getDefaultTable() : ";
    LOG.trace(method + "Start");

    DefaultTable solrDocumentTable;
    if (dataSetTableMetadata != null)
    {
      solrDocumentTable = new DefaultTable(dataSetTableMetadata);
    }
    else if (dataSetTableName != null)
    {
      solrDocumentTable = new DefaultTable(dataSetTableName, retrieveColumns(docListInSolr));
    }
    else
    {
      throw new IllegalStateException(method + "Entweder tableMetadata oder " + "tableName muss gesetzt sein.");
    }

    LOG.trace(method + "End");
    return solrDocumentTable;
  }

  private Column[] retrieveColumns(SolrDocumentList docListInSolr)
  {
    final String method = "getColumnsInSolr() : ";
    LOG.trace(method + "Start");

    Column[] columns = null;
    if (docListInSolr != null && !docListInSolr.isEmpty())
    {
      SolrDocument doc = docListInSolr.get(0);
      Collection<String> columnNames = doc.getFieldNames();
      columns = new Column[columnNames.size()];
      int i = 0;
      for (String columnName : columnNames)
      {
        columns[i] = new Column(columnName, DataType.UNKNOWN);
        i++;
      }
    }

    LOG.trace(method + "End");
    return columns;
  }

  private void insertRowsInTable(final DefaultTable table, final SolrDocumentList docListInSolr)
      throws DataSetException
  {
    final String method = "insertRowsInTable() : ";
    LOG.trace(method + "Start");

    if (docListInSolr != null)
    {
      int i = 0;
      for (SolrDocument doc : docListInSolr)
      {
        table.addRow();
        for (Map.Entry<String, Object> entry : doc.entrySet())
        {
          if (containsColumn(table.getTableMetaData().getColumns(), entry.getKey()))
          {
            table.setValue(i, entry.getKey(), entry.getValue());
          }
        }
        i++;
      }
    }

    LOG.trace(method + "End");
  }

  private boolean containsColumn(Column[] columns, String columnName)
  {
    final String method = "containsColumn() : ";
    LOG.trace(method + "Start");

    boolean found = false;
    for (Column column : columns)
    {
      if (column.getColumnName().equalsIgnoreCase(columnName))
      {
        found = true;
        LOG.debug(method + "Spalte " + columnName + " gefunden.");
        break;
      }
    }
    if (!found)
    {
      LOG.debug(method + "Spalte " + columnName + " NICHT gefunden.");
    }

    LOG.trace(method + "End");
    return found;
  }

  private void cleanSolrIndex() throws Exception
  {
    final String method = "cleanSolrIndex() : ";
    LOG.trace(method + "Start");

    LOG.debug(method + "Cleaning Solr index");
    this.solrServer.deleteByQuery(SOLR_DEFAULT_QUERY);
    this.solrServer.commit();
    verifyThatIndexIsEmpty();

    LOG.trace(method + "End");
  }

  private void verifyThatIndexIsEmpty() throws Exception
  {
    QueryResponse searchResult = this.solrServer.query(new SolrQuery(SOLR_DEFAULT_QUERY));
    assertThat(searchResult.getResults()).hasSize(0);
  }

  /**
   * Erstellt eine Verbindung zu einer Solr-Instanz unter der
   * hinterlegten URL.
   * 
   * @return die erstellte Connection zum SolrServer.
   */
  private SolrServer getConnection()
  {
    final String method = "getConnection() : ";
    LOG.trace(method + "Start");

    LOG.debug(method + "Connecting to solr server with URL " + this.fUrl);
    SolrServer solrServer = new HttpSolrServer(this.fUrl);
    LOG.debug(method + "Connection established.");

    LOG.trace(method + "End");
    return solrServer;
  }
}
