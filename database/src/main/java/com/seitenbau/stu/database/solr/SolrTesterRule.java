package com.seitenbau.stu.database.solr;

import static org.fest.assertions.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.params.ClientParamBean;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.solr.client.solrj.SolrQuery;
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

import com.seitenbau.stu.database.SortConfig;
import com.seitenbau.stu.database.extend.DbUnitDatasetFactory;
import com.seitenbau.stu.database.modifier.IDataSetModifier;
import com.seitenbau.stu.database.tester.DBAssertion;

/**
 * Rule to insert the testdata into Solr.
 * 
 * The <b>solrUrl</b> must be set.
 * <p>
 * If not all fields should be used inside Solr for comparison the
 * <b>tableMetadata</b> attribute must be set in addition.
 * 
 */
public class SolrTesterRule implements MethodRule
{
  /** Default search string for all documents inside the Solr index. */
  private static final String SOLR_DEFAULT_QUERY = "*:*";

  private static final Logger LOG = LoggerFactory.getLogger(SolrTesterRule.class);

  /**
   * Class of the 'environment'. Required to find related packages
   * while loading XML files.
   */
  private Class<?> fClazz;

  /**
   * Object of the test class. Other objects are injected.
   */
  private Object _target;

  /**
   * The Solr server to establish the connection to Solr.
   * */
  private HttpSolrServer solrServer;

  /** URL of the solr server. */
  private String fUrl;

  /** Username, if solr is protected with HTTP basic auth.*/
  private String fBasicAuthUsername;

  /** Password, if solr is protected with HTTP basic auth.*/
  private String fBasicAuthPassword;
  
  private List<IDataSetModifier> fDefaultModifierList = new ArrayList<IDataSetModifier>();

  private IDataSet defaultDataset;

  private DbUnitDatasetFactory _defaultDatasetFactory;

  private IDataSet fLastInsertedDataSet;

  protected SortConfig[] _defaultSortConfig;

  private String dataSetTableName;

  /**
   * The metadata of the table that should be compared.
   */
  private DefaultTableMetaData dataSetTableMetadata;

  /**
   * Creates an instance of {@link SolrTesterRule} and returns it.
   * 
   * @return the new instance
   */
  public static SolrTesterRule create()
  {
    return new SolrTesterRule();
  }

  /**
   * Sets the table name, that is used for creation of the data set while reading data from Solr.
   * 
   * <p>
   * Attention: If the param <b>tableMetadata</b> is set, this param is ignored.
   * </p>
   * 
   * @param tableName the name of the table.
   * @return
   */
  public SolrTesterRule tableName(String tableName)
  {
    this.dataSetTableName = tableName;
    return this;
  }

  /**
   * Creates table metadata based on the table name and corresponding
   * columns.
   * 
   * @param tableName the table name.
   * @param columns the columns of the table.
   * @return the current instance.
   */
  public SolrTesterRule tableMetadata(String tableName, Column[] columns)
  {
    this.dataSetTableMetadata = new DefaultTableMetaData(tableName, columns);
    return this;
  }

  /**
   * Sets the URL, that is used to establish the connection to Solr.
   * 
   * @param solrUrl the URL to the Solr instance.
   * @return the current instance.
   */
  public SolrTesterRule solrUrl(String solrUrl)
  {
    if (StringUtils.isBlank(solrUrl))
    {
      throw new IllegalArgumentException("Parameter solrUrl must be set and must not be empty!");
    }
    this.fUrl = solrUrl;
    init(null);
    close();
    return this;
  }
  
  /**
   * Sets the username if solr is protected with HTTP basic auth.
   * 
   * @param username the username for HTTP basic auth.
   * 
   * @return the current instance.
   */
  public SolrTesterRule solrBasicAuthUsername(String username)
  {
    this.fBasicAuthUsername = username;
    close();
    return this;
  }
  
  /**
     * Sets the password if solr is protected with HTTP basic auth.
   * 
   * @param password the password for HTTP basic auth.
   * 
   * @return the current instance.
   */
  public SolrTesterRule solrBasicAuthPassword(String password)
  {
    this.fBasicAuthPassword = password;
    close();
    return this;
  }

  /**
   * Adds the given modifiers to the list of default modifiers.
   * 
   * @param defaultModifiers the modifiers that should be added.
   * @return the current instance.
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
   * Closes the current connection if required.
   * 
   */
  protected void close()
  {
    if (solrServer != null)
    {
      solrServer.shutdown();
      solrServer = null;
    }
  }

  protected Class<?> getClazz()
  {
    if (fClazz == null)
    {
      throw new IllegalStateException("The field fClass is null. "
          + "Probably no class instance was passed to the constructor.");
    }
    return fClazz;
  }

  /**
   * Tries to find the class instance of the calling class.
   * <p>
   * Traverses the stack until a class is found that does not derive
   * from DatabcaeTester class. The method fails if the test class
   * derives from DatabaseTester class directly or indirectly.
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
   * Sets the clazz instance to detect the target package directory.
   * 
   * @param clazz The Class instance.
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
      LOG.debug(method + "Swallowed up.");
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
   * Helper method that wraps dataset loading and the CLEAN_INSERT.
   * 
   * @param dataset
   * 
   * @param modifiers additional modifiers.
   * @throws Exception
   */
  public void cleanInsert(IDataSet dataset, IDataSetModifier... modifiers) throws Exception
  {
    final String method = "cleanInsert() : ";
    LOG.trace(method + "Start");

    trySetAnnotatedField(dataset);

    connectToSolr();

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
   * Removes all content of the Solr index.
   * 
   * @throws Exception
   */
  public void truncate() throws Exception
  {
    final String method = "truncate() : ";
    LOG.trace(method + "Start");

    connectToSolr();

    // clean solr index
    cleanSolrIndex();

    LOG.trace(method + "End");
  }

  /**
   * Helper method that wraps dataset loading and the CLEAN_INSERT.
   * 
   * @param datasetFactory Factory that returns the actual dataset.
   * 
   * @param modifiers additional modifiers.
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
   * Sets the default dataset.
   * 
   * @param datasetFactory the default dataset.
   * @return
   */
  public SolrTesterRule setDefaultDataSet(DbUnitDatasetFactory datasetFactory)
  {
    _defaultDatasetFactory = datasetFactory;
    defaultDataset = datasetFactory.createDBUnitDataSet();
    return this;
  }

  /**
   * Sets the given ddataset as the last inserted dataset.
   * 
   * @param loadedDS the dataset instance of the last inserted dataset.
   */
  protected void setLastInsertedDataSet(IDataSet loadedDS) throws Exception
  {
    fLastInsertedDataSet = loadedDS;
  }

  /**
   * Method to access the default modifiers.
   * 
   * @param modifiers optional additional list of modifiers.
   * 
   * @return Returns the provided modifiers and the default modifiers
   *         if set.
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
   * Adds a modifier to the list of default modifiers.
   * <p>
   * Default modifiers are used while loading datasets (e.g.
   * {@link #getDataSet(String, IDataSetModifier...)}).
   * </p>
   * 
   * @param aModifier The modifier that should be added.
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
   * Adds a modifier to the list of default modifiers.
   * <p>
   * Default modifiers are used while loading datasets (e.g.
   * {@link #getDataSet(String, IDataSetModifier...)}).
   * </p>
   * 
   * @param aModifier The modifier that should be added.
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
   * Compares tha database with the last inserted dataset that was
   * inserted via the cleanInsert method. Maybe derived classes
   * changed the last dataset by calling
   * {@link #setLastInsertedDataSet(IDataSet)}.
   * 
   * @throws Exception Error during comparison.
   */
  public void assertDataBaseStillTheSame() throws Exception
  {
    assertDataBaseStillTheSame(null);
  }

  /**
   * Compares tha database with the last inserted dataset that was
   * inserted via the cleanInsert method. Maybe derived classes
   * changed the last dataset by calling
   * {@link #setLastInsertedDataSet(IDataSet)}.
   * 
   * @throws Exception Error during comparison.
   */
  public void assertDataBaseStillTheSame(SortConfig[] sortConfig) throws Exception
  {
    // create empty DefaultDataSet.
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
   * Method to compare a dataset to the actual database.
   * 
   * @param expectedDataSet the dataset with the expected Solr data.
   * @param modifiers the modifiers that should be added.
   * @throws Exception
   */
  public void assertDataBase(IDataSet expectedDataSet, IDataSetModifier... modifiers) throws Exception
  {
    DBAssertion.assertDataSet(createDatabaseSnapshot(), expectedDataSet, getModifiers(modifiers));
  }

  /**
   * Method to compare a dataset to the actual database.
   * 
   * @param factory the dataset factory that contains the dataset with the expected Solr data.
   * @param modifiers the modifiers that should be added.
   * @throws Exception
   */
  public void assertDataBase(DbUnitDatasetFactory factory, IDataSetModifier... modifiers) throws Exception
  {
    assertDataBase(factory.createDBUnitDataSet(), getModifiers(modifiers));
  }

  /**
   * Compares the given dataset with the actual data in the database.
   * The data is sorted according to the given sorting configuration.
   * 
   * @param expectedDataSet the dataset with the expected data.
   * @param config the sorting config.
   * @param modifiers the additional modifiers.
   * @throws Exception
   */
  public void assertDataBaseSorted(IDataSet expectedDataSet, SortConfig[] config, IDataSetModifier... modifiers)
      throws Exception
  {
    DBAssertion.assertDataSet(sortTables(createDatabaseSnapshot(), config), sortTables(expectedDataSet, config),
        getModifiers(modifiers));
  }

  /**
   * Compares the given dataset with the actual data in the database.
   * 
   * @param expectedDataSet the dataset with the expected data.
   * @param modifiers the additional modifiers.
   * @throws Exception
   */
  public void assertDataBaseSorted(IDataSet expectedDataSet, IDataSetModifier... modifiers) throws Exception
  {
    DBAssertion.assertDataSet(true, createDatabaseSnapshot(), expectedDataSet, getModifiers(modifiers));
  }

  /**
   * Sort the dataset based on the given sorting configuration.
   * 
   * @param ds the dataset.
   * @param config the sorting condiguration.
   * @return the sorted dataset, that contains tables of type
   *         SortTable.
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
   * Creates a dataset with a live snapshot of the entire database.
   * 
   * @return the database dump.
   */
  public IDataSet createDatabaseSnapshot() throws Exception
  {
    final String method = "createDatabaseSnapshot() : ";
    LOG.trace(method + "Start");
    
    connectToSolr();

    LOG.debug(method + "Read existing data from Solr.");
    SolrDocumentList docListInSolr = this.solrServer.query(new SolrQuery(SOLR_DEFAULT_QUERY)).getResults();

    LOG.debug(method + "Creating dataset.");
    DefaultDataSet dataset = new DefaultDataSet();
    DefaultTable solrDocumentTable = getDefaultTable(docListInSolr);
    insertRowsInTable(solrDocumentTable, docListInSolr);
    dataset.addTable(solrDocumentTable);
    LOG.debug(method + "Dataset created.");

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
      throw new IllegalStateException(method + "tableMetadata or tableName must be set.");
    }

    LOG.trace(method + "End");
    return solrDocumentTable;
  }

  private Column[] retrieveColumns(SolrDocumentList docListInSolr)
  {
    final String method = "retrieveColumns() : ";
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
        LOG.debug(method + "Found column " + columnName + ".");
        break;
      }
    }
    if (!found)
    {
      LOG.debug(method + "Could NOT find column " + columnName + ".");
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
   * Establish the connection to the solr instance.
   * 
   * @return creates the connection to the solr server.
   */
  private void connectToSolr()
  {
    final String method = "connectToSolr() : ";
    LOG.trace(method + "Start");

    if (this.solrServer == null)
    {
      LOG.debug(method + "Connecting to solr server with URL " + this.fUrl);
      solrServer = new HttpSolrServer(this.fUrl);
      if (fBasicAuthPassword != null && fBasicAuthUsername != null)
      {
        LOG.debug(method + "Configuring username " + this.fBasicAuthUsername
            + " and password (not shown) via Basic Auth");
        DefaultHttpClient httpClient = (DefaultHttpClient) solrServer.getHttpClient();
        String credentials = fBasicAuthUsername + ":" + fBasicAuthPassword;
        try
        {
          credentials = Base64.encodeBase64String(credentials.getBytes("ISO-8859-1"));
        }
        catch (UnsupportedEncodingException e)
        {
          throw new RuntimeException(e);
        }
        credentials = credentials.replace("\r", "").replace("\n", "");
        Header header = new BasicHeader("Authorization", "Basic " + credentials);
        List<Header> headerList = new ArrayList<Header>();
        headerList.add(header);
        new ClientParamBean(httpClient.getParams()).setDefaultHeaders(headerList);
      }
      LOG.debug(method + "Connection established.");
    }
    else
    {
      LOG.trace(method + "Reusing existing connection.");
    }

    LOG.trace(method + "End");
  }
  
  public HttpSolrServer getSolrServer()
  {
    return solrServer;
  }
}
