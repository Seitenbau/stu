package com.seitenbau.stu.database.tester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.DelegatingConnection;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

import com.seitenbau.stu.config.StoredProperty;
import com.seitenbau.stu.config.TestConfiguration;
import com.seitenbau.stu.database.DatabaseTester;
import com.seitenbau.stu.database.SortConfig;
import com.seitenbau.stu.database.TestConfigDatabase;
import com.seitenbau.stu.database.dsl.DataSetIdentificator;
import com.seitenbau.stu.database.dsl.DataSetRegistry;
import com.seitenbau.stu.database.extend.DatabaseOperationFactory;
import com.seitenbau.stu.database.extend.DatabaseTesterCleanAction;
import com.seitenbau.stu.database.extend.DbUnitDatasetFactory;
import com.seitenbau.stu.database.extend.impl.DefaultDbUnitDatabaseOperationFactory;
import com.seitenbau.stu.database.modifier.IDataSetModifier;
import com.seitenbau.stu.util.Future;

public class DatabaseTesterBase<MY_TYPE>
{

  /**
   * Class of the 'environment'. Required to find related packages
   * while loading XML files.
   */
  private Class<?> fClazz;

  protected IDatabaseConnection fDatabaseConnection;

  protected List<IDataSetModifier> fDefaultModifierList = new ArrayList<IDataSetModifier>();

  protected String fDriverName;

  protected IDataSet fLastInsertedDataSet;

  protected boolean fKeepLastInsertedDataSet = true;

  protected String fPassword;

  protected String fPrefixClasspath = "";

  protected String fUrl;

  protected String fUsername;

  protected String fSchema;

  protected IDataTypeFactory _registerTypeFactory;

  protected Future<DataSource> _lazySource;

  protected List<DatabaseTesterCleanAction> _cleanActions = new ArrayList<DatabaseTesterCleanAction>();

  protected Map<String, Boolean> _dbUnitFeatures;

  protected Map<String, Object> _dbUnitProperty;

  protected DatabaseOperationFactory _databaseOperationFactory;

  /**
   * Constructor that sets connection data to the database.
   *
   * <code><pre>
   *     dbTester = new DatabaseTester(
   *        "org.gjt.mm.mysql.Driver",
   *        "jdbc:mysql://192.168.0.42:3306/my_database_name",
   *        "user",
   *        "password"
   *       );
   * </pre></code>
   *
   * @param driverName Class name of the database driver.
   *
   * @param url URL of the database.
   *
   * @param username Database user.
   *
   * @param password Database user password.
   */
  public DatabaseTesterBase(String driverName, String url, String username, String password,
      IDataSetModifier... defaultModifiers)
  {
    fDriverName = driverName;
    fUrl = url;
    fUsername = username;
    fPassword = password;
    init(defaultModifiers, null);
  }

  /**
   * Constructor that sets connection data to the database.
   *
   * <code><pre>
   *     dbTester = new DatabaseTester(
   *        "org.gjt.mm.mysql.Driver",
   *        "jdbc:mysql://192.168.0.42:3306/my_database_name",
   *        "user",
   *        "password",
   *        getClass()
   *       );
   * </pre></code>
   *
   * @param driverName Class name of the database driver.
   * @param url URL of the database.
   * @param username Database user.
   * @param clazz calls object that is used to determine the proper
   *        directory to load XML dataset files.
   * @param password Database user password.
   */
  public DatabaseTesterBase(String driverName, String url, String username, String password, Class<?> clazz,
      IDataSetModifier... defaultModifiers)
  {
    fDriverName = driverName;
    fUrl = url;
    fUsername = username;
    fPassword = password;
    init(defaultModifiers, clazz);
  }

  /**
   * Constructor that sets connection data to the database.
   *
   * <code><pre>
   *     dbTester = new DatabaseTester(
   *        "org.gjt.mm.mysql.Driver",
   *        "jdbc:mysql://192.168.0.42:3306/my_database_name",
   *        "user",
   *        "password",
   *        getClass()
   *       );
   * </pre></code>
   *
   * @param driverName Class name of the database driver.
   * @param url URL of the database.
   * @param username Database user.
   * @param password Database user password.
   * @param schema Database schema.
   */
  public DatabaseTesterBase(String driverName, String url, String username, String password, String schema,
      IDataSetModifier... defaultModifiers)
  {
    fDriverName = driverName;
    fUrl = url;
    fUsername = username;
    fPassword = password;
    fSchema = schema;
    init(defaultModifiers, null);
  }

  public DatabaseTesterBase(Class<? extends TestConfigDatabase> configClass, IDataSetModifier... defaultModifiers)
  {
    this(TestConfiguration.getString("db.driver"), TestConfiguration.getString("db.url"), TestConfiguration
        .getString("db.username"), TestConfiguration.getString("db.password"), defaultModifiers);
    String schema = TestConfiguration.getString("db.schema");
    if (schema != null && schema != StoredProperty.NOT_SET_VALUE && !schema.isEmpty())
    {
      setSchema(schema);
    }
  }

  public DatabaseTesterBase(DataSource ds, String schema, IDataSetModifier... defaultModifiers)
  {
    init(defaultModifiers, null);
    setSchema(schema);
    setConnection(ds);
  }

  public DatabaseTesterBase(Future<DataSource> lazySource, IDataSetModifier... defaultModifiers)
  {
    addDefaultModifier(defaultModifiers);
    init(defaultModifiers, null);
    _lazySource = lazySource;
  }

  public DatabaseTesterBase(BasicDataSource dataSource, IDataSetModifier... defaultModifiers)
  {
    init(defaultModifiers, null);
    setConnection(dataSource);
  }

  /**
   * Sets a type factory that is registered on a new connection.
   *
   * @param registerTypeFactory
   */
  public MY_TYPE setTypeFactoryToRegister(IDataTypeFactory registerTypeFactory)
  {
    this._registerTypeFactory = registerTypeFactory;
    registerTypeFactory(fDatabaseConnection);
    return myself();
  }

  /**
   * Sets the schema for the database connection.
   * @param schema
   * @return
   */
  public MY_TYPE setSchema(String schema)
  {
    this.fSchema = schema;
    return myself();
  }

  /**
   * Initialize the instance.
   * @param defaultModifiers array of modifiers for the dataset
   * @param clazz a class to find out the path
   */
  public void init(IDataSetModifier[] defaultModifiers, Class<?> clazz)
  {
    setDbUnitFeature("http://www.dbunit.org/features/caseSensitiveTableNames", false);
    addDefaultModifier(defaultModifiers);
    if (clazz != null)
    {
      fClazz = clazz;
    }
    else
    {
      doMagicClazzFind();
    }
  }

  /**
   * Adds a modifier to the list of the default modifiers.
   * <p>
   * The default modifiers are applied whenever datasets are loaded
   * (e.g. via {@link #getDataSet(String, IDataSetModifier...)}).
   * </p>
   *
   * @param aModifier the additional modifier.
   * @return instance of the generic type
   */
  public MY_TYPE addDefaultModifier(IDataSetModifier aModifier)
  {
    if (fDefaultModifierList == null)
    {
      fDefaultModifierList = new ArrayList<IDataSetModifier>();
    }
    fDefaultModifierList.add(aModifier);
    return myself();
  }

  /**
   * Adds a modifier to the list of the default modifiers.
   * <p>
   * The default modifiers are applied whenever datasets are loaded
   * (e.g. via {@link #getDataSet(String, IDataSetModifier...)}).
   * </p>
   *
   * @param modifiers the additional modifiers.
   * @return instance of the generic type
   */
  public MY_TYPE addDefaultModifier(IDataSetModifier... modifiers)
  {
    if (modifiers != null)
    {
      for (IDataSetModifier aModifier : modifiers)
      {
        fDefaultModifierList.add(aModifier);
      }
    }
    return myself();
  }

  /**
   * Method that compares a dataset to the actual database.
   *
   * @param expectedDataSet the expected dataset
   * @param modifiers to modify the dataset
   * @throws Exception if the assertion fails
   */
  public void assertDataBase(IDataSet expectedDataSet, IDataSetModifier... modifiers) throws Exception
  {
    DBAssertion.assertDataSet(createDatabaseSnapshot(), expectedDataSet, getModifiers(modifiers));
  }

  /**
   * Method that compares a dataset to the actual database.
   *
   * @param factory factory for the dataset
   * @param modifiers to modify the dataset
   * @throws Exception if the assertion fails
   */
  public void assertDataBase(DbUnitDatasetFactory factory, IDataSetModifier... modifiers) throws Exception
  {
    DBAssertion.assertDataSet(createDatabaseSnapshot(), factory.createDBUnitDataSet(), getModifiers(modifiers));
  }

  public void assertDataBaseSorted(DbUnitDatasetFactory factory, SortConfig[] config, IDataSetModifier... modifiers)
      throws Exception
  {
    assertDataBaseSorted(factory.createDBUnitDataSet(), config, modifiers);
  }

  public void assertDataBaseSorted(IDataSet expectedDataSet, SortConfig[] config, IDataSetModifier... modifiers)
      throws Exception
  {
    DBAssertion.assertDataSet(sortTables(createDatabaseSnapshot(), config), sortTables(expectedDataSet, config),
        getModifiers(modifiers));
  }

  public void assertDataBaseSorted(DbUnitDatasetFactory factory, IDataSetModifier... modifiers) throws Exception
  {
    assertDataBaseSorted(factory.createDBUnitDataSet(), modifiers);
  }

  public void assertDataBaseSorted(IDataSet expectedDataSet, IDataSetModifier... modifiers) throws Exception
  {
    DBAssertion.assertDataSet(true, createDatabaseSnapshot(), expectedDataSet, getModifiers(modifiers));
  }

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
          if (cfg.getTablename().equalsIgnoreCase(tableName))
          {
            sortConfig = cfg;
          }
        }
        try
        {
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
        catch (DataSetException ex)
        {
          // do nothing - snapshot may contain more tables than
          // necessary
        }
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
   * Compares the current state of the database with the given XML
   * file. Convenient method. For more database assertion methods see
   * {@link DBAssertion}.
   *
   * @param xmlFileRelativToClass XML file relative to the package. If
   *        a prefix is set via {@link #setClassPathPrefix(String)} it
   *        is put in front.
   *
   * @param modifiers Optional list of {@link IDataSetModifier} that
   *        can manipulate the content of the loaded dataset.
   *
   * @throws Exception Error that occurs while loading or comparison.
   */
  public void assertDataBase(String xmlFileRelativToClass, IDataSetModifier... modifiers) throws Exception
  {
    IDataSet expectedDS = getDataSet(xmlFileRelativToClass, getModifiers(modifiers));
    DBAssertion.assertDataSet(createDatabaseSnapshot(), expectedDS, modifiers);
  }

  public void assertDataBaseSorted(String xmlFileRelativToClass, SortConfig[] config, IDataSetModifier... modifiers)
      throws Exception
  {
    IDataSet expectedDS = getDataSet(xmlFileRelativToClass, getModifiers(modifiers));
    DBAssertion.assertDataSet(sortTables(createDatabaseSnapshot(), config), sortTables(expectedDS, config), modifiers);
  }

  /**
   * Compares the database with the dataset that was last added with
   * the cleanInsert method. Derived classes may have manipulate the
   * last dataset via {@link #setLastInsertedDataSet(IDataSet)}.
   *
   * @throws Exception Error during comparison
   */
  public void assertDataBaseStillTheSame() throws Exception
  {
    assertDataBaseStillTheSame(null);
  }

  /**
   * Compares the database with the dataset that was last added with
   * the cleanInsert method. The asserion is done according to the
   * given sorting configuration. Derived classes may have manipulate
   * the last dataset via {@link #setLastInsertedDataSet(IDataSet)}.
   *
   * @param sortConfig the sorting configuration
   * @throws Exception Error during comparison
   */
  public void assertDataBaseStillTheSame(SortConfig[] sortConfig) throws Exception
  {
    if (!fKeepLastInsertedDataSet)
    {
      throw new AssertionError("Didn't keep the old dataset. Unable to compare!");
    }
    IDataSet lastDS = getLastInsertedDataSet();
    if (lastDS == null)
    {
      throw new AssertionError("Last dataset was null. Did you call a insert first?");
    }
    if (sortConfig == null)
    {
      assertDataBaseSorted(lastDS);
    }
    else
    {
      assertDataBaseSorted(lastDS, sortConfig);
    }
  }

  /**
   * Util method that wraps the loading of the dataset and the
   * CLEAN_INSERT.
   *
   * @param datasetFactory creates the actual dataset on the fly
   *
   * @param modifiers additional modifiers.
   * @throws Exception
   */
  public void prepare(DbUnitDatasetFactory datasetFactory, DatabaseOperation operation, IDataSetModifier... modifiers)
      throws Exception
  {
    prepare(datasetFactory.createDBUnitDataSet(), operation, modifiers);
  }

  /**
   * Util method that wraps the loading of the dataset and the
   * CLEAN_INSERT.
   *
   * @param dataset
   *
   * @param modifiers additional modifiers.
   * @throws Exception
   */
  public void prepare(IDataSet dataset, DatabaseOperation operation, IDataSetModifier... modifiers) throws Exception
  {
    IDataSet loadedDS = DataSetUtil.modifyDataSet(dataset, getModifiers(modifiers));

    setLastInsertedDataSet(loadedDS);

    operation.execute(getConnection(), loadedDS);
  }

  /**
   * Util method that wraps the loading of the dataset and the
   * CLEAN_INSERT.
   *
   * @param dataset
   *
   * @param modifiers additional modifiers.
   * @throws Exception
   */
  public void cleanInsert(IDataSet dataset, IDataSetModifier... modifiers) throws Exception
  {
    doCleanAction(dataset);
    prepare(dataset, getOperationFactory().cleanInsertOperation(), modifiers);
    doPrepareAction(dataset);
    trySetAnnotatedField(dataset);
  }

  /**
   * Util method that wraps the loading of the dataset and the
   * CLEAN_INSERT.
   *
   * @param datasetFactory Factory that return the actual dataset.
   *
   * @param modifiers additional modifiers.
   * @throws Exception
   */
  public void cleanInsert(DbUnitDatasetFactory datasetFactory, IDataSetModifier... modifiers) throws Exception
  {
    if (datasetFactory instanceof DataSetIdentificator)
    {
      DataSetIdentificator scope = (DataSetIdentificator) datasetFactory;
      DataSetRegistry.use(scope);
    }
    cleanInsert(datasetFactory.createDBUnitDataSet(), modifiers);
    trySetAnnotatedField(datasetFactory);
  }

  /**
   * Util method for truncate all data set tables.
   * @param datasetFactory the dataset Factory which create the actual
   *        Dataset for the truncate.
   * @throws Exception
   */
  public void truncate(DbUnitDatasetFactory datasetFactory) throws Exception
  {
    truncate(datasetFactory.createDBUnitDataSet());
    trySetAnnotatedField(datasetFactory);
  }

  /**
   * Util method for truncate all data set tables.
   * @param dataset the dataset for the truncate.
   * @throws Exception
   */
  public void truncate(IDataSet dataset) throws Exception
  {
    doCleanAction(dataset);
    prepare(dataset, getOperationFactory().truncateOperation());
    doPrepareAction(dataset);
    trySetAnnotatedField(dataset);
  }

  public void cleanInsert(String xmlFileRelativToClass, IDataSetModifier... modifiers) throws Exception
  {
    IDataSet loadedDS = getDataSet(xmlFileRelativToClass, getModifiers(modifiers));
    doCleanAction(loadedDS);
    setLastInsertedDataSet(loadedDS);
    prepare(loadedDS, getOperationFactory().cleanInsertOperation(), modifiers);
    trySetAnnotatedField(loadedDS);
  }

  public void insert(String xmlFileRelativToClass, IDataSetModifier... modifiers) throws Exception
  {
    IDataSet loadedDS = getDataSet(xmlFileRelativToClass, getModifiers(modifiers));
    doCleanAction(loadedDS);
    setLastInsertedDataSet(loadedDS);
    prepare(loadedDS, getOperationFactory().insertOperation(), modifiers);
    trySetAnnotatedField(loadedDS);
  }

  public void truncate(String xmlFileRelativToClass, IDataSetModifier... modifiers) throws Exception
  {
    IDataSet loadedDS = getDataSet(xmlFileRelativToClass, getModifiers(modifiers));
    doCleanAction(loadedDS);
    setLastInsertedDataSet(loadedDS);
    prepare(loadedDS, getOperationFactory().truncateOperation(), modifiers);
    trySetAnnotatedField(loadedDS);
  }

  public void truncateAndInsert(String xmlFileRelativToClass, IDataSetModifier... modifiers) throws Exception
  {
    truncate(xmlFileRelativToClass, modifiers);
    insert(xmlFileRelativToClass, modifiers);
  }

  /**
   * Closes the connection to the database.
   *
   * @throws Exception Error that occurs while closing the connection.
   */
  public void close() throws Exception
  {
    if (fDatabaseConnection != null)
    {
      fDatabaseConnection.close();
    }
    fDatabaseConnection = null;
  }

  /**
   * Creates a dataset with a live snapshot of the entire database.
   *
   * @return the database dump.
   */
  public IDataSet createDatabaseSnapshot() throws Exception
  {
    return getConnection().createDataSet();
  }

  /**
   * Returns the connection to the database or throws an exception.
   *
   * @return The connction to the database-
   *
   * @throws ClassNotFoundException Database driver could not be
   *         loaded.
   *
   * @throws SQLException Error while connecting to database.
   * @throws DatabaseUnitException
   */
  public IDatabaseConnection getConnection() throws ClassNotFoundException, SQLException, DatabaseUnitException
  {
    if (fDatabaseConnection != null)
    {
      return fDatabaseConnection;
    }
    if (fDriverName != null)
    {
      Class.forName(fDriverName);
    }
    if (_lazySource == null)
    {
      Connection dConn = DriverManager.getConnection(fUrl, fUsername, fPassword);
      return setConnection(dConn, fSchema);
    }
    return setConnection(getLazyDatasource());
  }

  protected DataSource getLazyDatasource()
  {
    return _lazySource.getFuture();
  }

  public IDatabaseConnection setConnection(DataSource dataSource)
  {
    try
    {
      Connection dconn = null;
      if (dataSource instanceof BasicDataSource)
      {
        ((BasicDataSource) dataSource).setAccessToUnderlyingConnectionAllowed(true);
        dconn = ((DelegatingConnection) dataSource.getConnection()).getInnermostDelegate();
        if (null == dconn)
        {
          dconn = ((DelegatingConnection) dataSource.getConnection()).getDelegate();
        }
      }
      if (null == dconn)
      {
        dconn = dataSource.getConnection();
      }
      if (null == dconn)
      {
        throw new RuntimeException("Unable to get Connection from datasource");
      }
      String schema = dconn.getCatalog();
      if (schema == null)
      {
        schema = fSchema;
      }
      return setConnection(dconn, schema);
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
    catch (DatabaseUnitException e)
    {
      throw new RuntimeException(e);
    }
  }

  protected IDatabaseConnection setConnection(Connection conn)
  {
    closeSilent();
    try
    {
      fDatabaseConnection = new DatabaseConnection(conn);
    }
    catch (DatabaseUnitException e)
    {
      throw new RuntimeException(e);
    }
    registerTypeFactory(fDatabaseConnection);
    return fDatabaseConnection;
  }

  public IDatabaseConnection setConnection(Connection dconn, String schema) throws DatabaseUnitException
  {
    closeSilent();
    fDatabaseConnection = new DatabaseConnection(dconn, schema);
    registerTypeFactory(fDatabaseConnection);
    registerFeatures(fDatabaseConnection);
    registerProperties(fDatabaseConnection);
    return fDatabaseConnection;
  }

  protected void closeSilent()
  {
    try
    {
      close();
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Load the dataset from the given XML file relative to the package
   *
   * @param xmlFileRelativToClass Filename. In addition the optiomal
   *        {@link #setClassPathPrefix(String)} dcoulf be put in
   *        front.
   *
   * @param modifiers Optional list of modifiers that manipulate the
   *        dataset. In addition to the optional modifiers the global
   *        default modifiers are applied.
   *
   * @return The dataset of the XML file. Could be manipulated by the
   *         modifiers.
   *
   * @throws Exception Error while reading file or applying a
   *         modifier.
   */
  public IDataSet getDataSet(String xmlFileRelativToClass, IDataSetModifier... modifiers) throws Exception
  {
    return DataSetUtil.getDataSetFromClasspath(getClazz(), fPrefixClasspath + xmlFileRelativToClass,
        getModifiers(modifiers));
  }

  /**
   * Util method for the last inserted dataset.
   *
   * @return Returns the last via
   *         {@link #cleanInsert(IDataSet, IDataSetModifier...)}
   *         inserted dataset. {@code null} if no dataset inserted
   *         yet.
   */
  public IDataSet getLastInsertedDataSet()
  {
    return fLastInsertedDataSet;
  }

  /**
   * Method to access the default modifiers.
   *
   * @param modifiers optional list of additional modifiers.
   *
   * @return Returns the provided optional modifiers and the default
   *         modifiers as array.
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
    return list.toArray(new IDataSetModifier[list.size()]);
  }

  /**
   * Sets the prefix in front of all file read operations.
   *
   * @param prefixForClasspath The prefix. Put in front of every
   *        filename. Can consist of a path ("Test-02-resources/"). If
   *        {@code null} is provided the prefix is ignored.
   */
  public MY_TYPE setClassPathPrefix(String prefixForClasspath)
  {
    fPrefixClasspath = prefixForClasspath;
    if (fPrefixClasspath == null)
    {
      fPrefixClasspath = "";
    }
    return myself();
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

  /**
   * Setter
   *
   * @param loadedDS the dataset instance of the last inserted
   *        dataset.
   */
  protected void setLastInsertedDataSet(IDataSet loadedDS) throws Exception
  {
    if (fKeepLastInsertedDataSet)
    {
      fLastInsertedDataSet = loadedDS;
    }
    else
    {
      fLastInsertedDataSet = null;
    }
  }

  /**
   * Clear, in case dataset is too big
   */
  protected void clearLastInsertedDataSet()
  {
    fLastInsertedDataSet = null;
  }

  public void dumpDatabase(String fileName) throws Exception
  {
    DataSetUtil.dumpDatabase(this, fileName);
  }

  /**
   * Compares the current state of the database with the given XML
   * file. Convenient method. For more database assertion methods see
   * {@link DBAssertion}.
   *
   * @param xmlFileRelativToClass XML file relative to the package. If
   *        a prefix is set via {@link #setClassPathPrefix(String)} it
   *        is put in front.
   * @param tableName The name of the table.
   * @param modifiers Optional list of {@link IDataSetModifier} that
   *        can manipulate the content of the loaded dataset.
   *
   * @throws Exception Error that occurs while loading or comparison.
   */
  public void assertTable(String xmlFileRelativToClass, String tableName, IDataSetModifier... modifiers)
      throws Exception
  {
    IDataSet dataset = getDataSet(xmlFileRelativToClass, modifiers);
    DBAssertion.assertTable(this.getConnection(), tableName, dataset);
  }

  protected void registerTypeFactory(IDatabaseConnection databaseConnection)
  {
    if (_registerTypeFactory != null && databaseConnection != null)
    {
      DatabaseConfig config = databaseConnection.getConfig();
      config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, _registerTypeFactory);
    }
  }

  protected void registerFeatures(IDatabaseConnection databaseConnection)
  {
    for (Entry<String, Boolean> item : getDBUnitFeatures().entrySet())
    {
      String feature = item.getKey();
      boolean value = item.getValue();
      databaseConnection.getConfig().setProperty(feature, value);
    }
  }

  protected void registerProperties(IDatabaseConnection databaseConnection)
  {
    for (Entry<String, Object> item : getDBUnitProperties().entrySet())
    {
      String name = item.getKey();
      Object value = item.getValue();
      databaseConnection.getConfig().setProperty(name, value);
    }
  }

  public boolean isKeepLastInsertedDataSet()
  {
    return fKeepLastInsertedDataSet;
  }

  public MY_TYPE setKeepLastInsertedDataSet(boolean keepLastInsertedDataSet)
  {
    fKeepLastInsertedDataSet = keepLastInsertedDataSet;
    return myself();
  }

  public MY_TYPE setUseCaseSensitiveNames(boolean useCaseSensitiveNames)
  {
    setDbUnitFeature("http://www.dbunit.org/features/caseSensitiveTableNames", useCaseSensitiveNames);
    return myself();
  }

  /**
   * Wait until database table has at least newMinRowCount rows.
   * @param tableName table to wait for
   * @param newMinRowCount number of rows which the table should have
   * @return the row count at the time the inner loop stopped.
   * @throws Exception
   */
  public int waitForNewRows(String tableName, int newMinRowCount) throws Exception
  {
    return waitForNewRows(tableName, newMinRowCount, 70);
  }

  /**
   * Wait until database table has at least newMinRowCount rows.
   * @param tableName table to wait for
   * @param newMinRowCount number of rows which the table should have
   * @return the row count at the time the inner loop stopped.
   * @throws Exception
   */
  public int waitForNewRows(String tableName, int newMinRowCount, int timeoutSeconds) throws Exception
  {
    long time = 0;
    long maxTime = timeoutSeconds * 1000;
    int progress = 0;
    while (true)
    {
      int count = getRowCount(tableName);
      if (count >= newMinRowCount)
      {
        return count;
      }
      if (time > maxTime)
      {
        throw new AssertionError("timeout while waiting for new rows to appear in Table " + tableName);
      }
      Thread.sleep(250);
      time += 250;
      if (++progress % (4 * 15) == 0)
      {
        System.out.print(".");
      }
    }
  }

  /**
   * Get the actual row count of a table.
   *
   * @param tableName the table name
   * @return the count 0 or greater not null
   *
   * @throws Exception
   */
  public int getRowCount(String tableName) throws Exception
  {
    String table = tableName;
    if (fSchema != null && !tableName.contains("."))
    {
      table = fSchema + "." + tableName;
    }
    String sql = "select count(*) from " + table;

    return executeSQLandReturnInt(sql);
  }

  public int executeSQLandReturnInt(String sql) throws Exception
  {
    Connection connection = getConnection().getConnection();
    connection.setAutoCommit(false);
    try
    {
      final Statement statement = connection.createStatement();
      try
      {
        statement.execute(sql);
        final ResultSet set = statement.getResultSet();
        try
        {
          if (statement.getResultSet().next())
          {
            int count = set.getInt(1);
            return count;
          }
        }
        finally
        {
          if (set != null)
          {
            set.close();
          }
        }
      }
      finally
      {
        if (statement != null)
        {
          statement.close();
        }
      }
    }
    finally
    {
      connection.commit();
      connection.setAutoCommit(true);
    }
    throw new RuntimeException("got no result");
  }

  public boolean executeSQL(String sql) throws Exception
  {
    Connection connection = getConnection().getConnection();
    connection.setAutoCommit(false);
    boolean result = false;
    try
    {
      final Statement statement = connection.createStatement();
      try
      {
        result = statement.execute(sql);
      }
      finally
      {
        if (statement != null)
        {
          statement.close();
        }
      }
    }
    finally
    {
      connection.commit();
      connection.setAutoCommit(true);
    }
    return result;
  }

  public static synchronized Class<?> getCallerClassViaMagic()
  {
    Class<?> potentialClazz = null;
    try
    {
      StackTraceElement[] stackTrace = new Throwable().getStackTrace();
      ClassLoader cl = DatabaseTester.class.getClassLoader();
      for (StackTraceElement item : stackTrace)
      {
        String clazzName = item.getClassName();

        // fix for Spock tests
        if (clazzName.startsWith("sun.reflect"))
        {
          continue;
        }

        Class<?> clazz = cl.loadClass(clazzName);
        if (!isSubclassFrom(clazz, DatabaseTesterBase.class))
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
      // swallow
    }
    return potentialClazz;
  }

  public MY_TYPE setClassPathClass(Class<?> clazz)
  {
    fClazz = clazz;
    return myself();
  }

  public MY_TYPE addCleanAction(DatabaseTesterCleanAction action)
  {
    _cleanActions.add(action);
    return myself();
  }

  /**
   * Set a config feature on the underlying DBUnit. See
   * {@link DatabaseConfig} for possile features.
   *
   * @param feature feature id
   * @param value activate deactivate feature
   * @return this
   */
  public MY_TYPE setDbUnitFeature(String feature, boolean value)
  {
    getDBUnitFeatures().put(feature, value);
    return myself();
  }

  protected Map<String, Boolean> getDBUnitFeatures()
  {
    if (_dbUnitFeatures == null)
    {
      _dbUnitFeatures = new HashMap<String, Boolean>();
    }
    return _dbUnitFeatures;
  }

  /**
   * Set a config property on the underlying DBUnit. See
   * {@link DatabaseConfig} for possile features.
   *
   * @param name of the property
   * @param value activate deactivate feature
   * @return this
   */
  public MY_TYPE setDbUnitProperty(String name, Object value)
  {
    getDBUnitProperties().put(name, value);
    return myself();
  }

  protected Map<String, Object> getDBUnitProperties()
  {
    if (_dbUnitProperty == null)
    {
      _dbUnitProperty = new HashMap<String, Object>();
    }
    return _dbUnitProperty;
  }

  protected void doCleanAction(IDataSet dataset) throws Exception
  {
    if (_cleanActions == null)
    {
      return;
    }
    for (DatabaseTesterCleanAction action : _cleanActions)
    {
      action.doCleanDatabase(this, dataset);
    }
  }

  protected void doPrepareAction(IDataSet dataset) throws Exception
  {
    if (_cleanActions == null)
    {
      return;
    }
    for (DatabaseTesterCleanAction action : _cleanActions)
    {
      action.doPrepareDatabase(this, dataset);
    }
  }

  protected void trySetAnnotatedField(Object datasetFactory) throws Exception
  {
    // 2 implement
  }

  /**
   * Internal getter of the OperationFactory, used to create
   * @return the actual Factory or lazy initialize the factory as
   *         {@linkplain DefaultDbUnitDatabaseOperationFactory}.
   */
  protected DatabaseOperationFactory getOperationFactory()
  {
    if (_databaseOperationFactory == null)
    {
      _databaseOperationFactory = new DefaultDbUnitDatabaseOperationFactory();
    }
    return _databaseOperationFactory;
  }

  /** replace the DatabaseOperation Factory */
  public MY_TYPE setDatabaseOperationFactory(DatabaseOperationFactory databaseOperationFactory)
  {
    _databaseOperationFactory = databaseOperationFactory;
    return myself();
  }

  @SuppressWarnings("unchecked")
  protected MY_TYPE myself()
  {
    return (MY_TYPE) this;
  }

}
