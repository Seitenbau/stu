package com.seitenbau.testing.dbunit.rule;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.seitenbau.testing.dbunit.SortConfig;
import com.seitenbau.testing.dbunit.TestConfigDatabase;
import com.seitenbau.testing.dbunit.extend.DatabaseTesterCleanAction;
import com.seitenbau.testing.dbunit.extend.DbUnitDatasetFactory;
import com.seitenbau.testing.dbunit.extend.impl.DatasetFactoryComposite;
import com.seitenbau.testing.dbunit.modifier.IDataSetModifier;
import com.seitenbau.testing.dbunit.tester.DatabaseTesterBase;
import com.seitenbau.testing.rules.ITestMethodDescriptor;
import com.seitenbau.testing.rules.impl.JUnitTestMethodDescriptor;
import com.seitenbau.testing.util.AnnotationUtils;
import com.seitenbau.testing.util.Future;
import com.seitenbau.testing.util.ReflectionUtils;

/**
 * <p>
 * JUnit Rule zum Einspielen und Vergleichen von Datensets.
 * <p/>
 * <p>
 * x
 * </p>
 */
public class DatabaseTesterRule extends DatabaseTesterBase<DatabaseTesterRule> implements MethodRule
{
  protected String defaultDatasetName;

  protected IDataSet defaultDataset;

  protected Future<?> _future;

  protected DbUnitDatasetFactory _defaultDatasetFactory;

  protected boolean _useTruncateAsCleanInsert;

  protected Object _target;

  protected SortConfig[] _defaultSortConfig;

  /**
   * 
   * @param driverName
   * @param url
   * @param username
   * @param password
   * @param defaultDataSetName
   * @param defaultModifiers
   */
  public DatabaseTesterRule(
      String driverName,
      String url,
      String username,
      String password,
      String defaultDataSetName,
      IDataSetModifier... defaultModifiers)
  {
    super(driverName, url, username, password, defaultModifiers);
    this.defaultDatasetName = defaultDataSetName;
  }

  /**
   * Create a new DatabaseTesterRule by using the configuation via
   * TestConfiguration
   * 
   * <pre>
   *   db.driver=org.gjt.mm.mysql.Driver
   *   db.url=jdbc:mysql://192.168.0.x:3306/dbName
   *   db.username=admin
   *   db.password=geheim
   *   # optinal :
   *   db.schema=theSchema
   * </pre>
   * 
   * @param configClass
   * @param defaultModifiers
   */
  public DatabaseTesterRule(Class<? extends TestConfigDatabase> configClass,
      IDataSetModifier... defaultModifiers)
  {
    super(configClass, defaultModifiers);
  }

  public DatabaseTesterRule(String driverName, String url, String username,
      String password,
      IDataSetModifier... defaultModifiers)
  {
    super(driverName, url, username, password, defaultModifiers);
  }

  public DatabaseTesterRule(String driverName, String url, String username,
      String password,
      Class<?> clazz, IDataSetModifier... defaultModifiers)
  {
    super(driverName, url, username, password, clazz, defaultModifiers);
  }

  public DatabaseTesterRule(IDataSet defaultDataSet, String dbDriver,
      String dbUrl, String dbUser,
      String dbPwd, IDataSetModifier[] defaultModifiers)
  {
    super(dbDriver, dbUrl, dbUser, dbPwd, defaultModifiers);
    setDefaultDataSet(defaultDataSet);
  }

  public DatabaseTesterRule(Future<DataSource> lazyDataSource,
      IDataSetModifier... defaultModifiers)
  {
    super(lazyDataSource, defaultModifiers);
  }

  protected class AnnotationConfig
  {
    boolean foundAnyAnnotation;

    public boolean assertNoModification;

    public Class<? extends DbUnitDatasetFactory>[] prepare;

    public String prepareDS;

    public boolean suppressInsert;
  }

  protected void before(ITestMethodDescriptor descriptor) throws Exception
  {
    AnnotationConfig setting = extractAnnotationConfig(descriptor);
    if (!setting.foundAnyAnnotation)
    {
      tryDefaultCleanInsert();
      return;
    }
    if (setting.suppressInsert)
    {
      return;
    }
    if (setting.prepareDS != null
        && !setting.prepareDS.equals(DatabaseSetup.NOT_SET))
    {
      doCleanInsert(setting.prepareDS);
    }
    else if (setting.prepare != null
        && setting.prepare != null
        && !setting.prepare[0].equals(DbUnitDatasetFactory.class))
    {
      doCleanInsert(descriptor, setting.prepare);
    }
    else
    {
      tryDefaultCleanInsert();
    }
  }

  protected AnnotationConfig extractAnnotationConfig(ITestMethodDescriptor method)
  {
    AnnotationConfig setting = new AnnotationConfig();
    DatabaseSetup methodAnnotation = method.getAnnotation(DatabaseSetup.class, false);
    if (methodAnnotation != null)
    {
      setting.foundAnyAnnotation = true;
      setting.assertNoModification = methodAnnotation.assertNoModification();
      setting.prepare = methodAnnotation.prepare();
      setting.prepareDS = methodAnnotation.prepareDS();
      setting.suppressInsert = methodAnnotation.suppressInsert();
    }
    DatabaseSetup classAnnotation = AnnotationUtils.findInTree(getClazz(), DatabaseSetup.class);
    if (classAnnotation != null)
    {
      if (setting.foundAnyAnnotation)
      { // only overwrite
        if (setting.prepare == null && setting.prepareDS == null)
        {
          setting.prepare = classAnnotation.prepare();
          setting.prepareDS = classAnnotation.prepareDS();
        }
      }
      else
      // all new
      {
        setting.foundAnyAnnotation = true;
        setting.assertNoModification = classAnnotation.assertNoModification();
        setting.prepare = classAnnotation.prepare();
        setting.prepareDS = classAnnotation.prepareDS();
        setting.suppressInsert = classAnnotation.suppressInsert();
      }
    }
    return setting;
  }

  protected void tryDefaultCleanInsert() throws Exception
  {
    if (defaultDataset != null)
    {
      doCleanInsert(defaultDataset);
      return;
    }
    if (defaultDatasetName != null)
    {
      doCleanInsert(defaultDatasetName);
      return;
    }
    if (_future != null)
    {
      Object value = _future.getFuture();
      if (value instanceof IDataSet)
      {
        doCleanInsert((IDataSet) value);
        return;
      }
      if (value instanceof DbUnitDatasetFactory)
      {
        doCleanInsert(((DbUnitDatasetFactory) value).createDBUnitDataSet());
        trySetAnnotatedField(value);
        return;
      }
    }
    if (_defaultDatasetFactory instanceof DbUnitDatasetFactory)
    {
      doCleanInsert(_defaultDatasetFactory
          .createDBUnitDataSet());
      return;
    }
  }

  protected void after(JUnitTestMethodDescriptor descriptor) throws Exception
  {
    DatabaseSetup annotation = descriptor.getAnnotation(DatabaseSetup.class, false);
    if (annotation == null)
    {
      DatabaseSetup classAnnotation = getClazz().getAnnotation(
          DatabaseSetup.class);
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
        throw new AssertionError("DatabasetTesterRule @after failed : "
            + error.toString());
      }
    }
  }

  /**
   * Set the default sorting used when Comparing via the Database
   * Tester rule. Not used in 'normal' compares.
   * 
   * @param defaultSortConfig the new SortConfig
   * @return this
   */
  public DatabaseTesterRule setDefaultSortConfig(SortConfig... defaultSortConfig)
  {
    _defaultSortConfig = defaultSortConfig;
    return this;
  }

  /**
   * The default DatasetFactories used when no {@link DatabaseSetup}
   * annotation is given.
   * @param datasetfactories List of factories, merged into one
   *        Dataset, no overlapping Tables!
   * @return this
   */
  public DatabaseTesterRule setDefaultDataSet(DbUnitDatasetFactory... datasetfactories)
  {
    if (datasetfactories == null || datasetfactories.length == 0)
    {
      throw new IllegalArgumentException("datasetFactory not given");
    }
    if (datasetfactories.length == 1)
    {
      _defaultDatasetFactory = datasetfactories[0];
    }
    else
    {
      _defaultDatasetFactory = DatasetFactoryComposite.of(datasetfactories);
    }
    return this;
  }

  /**
   * The default DefaultDataset used for clean insert when no
   * {@link DatabaseSetup} annotation is found on the Test
   * class/method.
   * 
   * @param defaultDataSet the default dataset.
   * @return this
   */
  public DatabaseTesterRule setDefaultDataSet(IDataSet defaultDataSet)
  {
    this.defaultDataset = defaultDataSet;
    return this;
  }

  /**
   * The default DefaultDataset used for cleanInsert when no
   * {@link DatabaseSetup} annotation is found on the Test
   * class/method. This is a future, so the actual creation can be
   * postboned.
   * 
   * @param future the default dataset as Future
   * @return this
   */
  public DatabaseTesterRule setDefaultDataSet(Future<IDataSet> future)
  {
    _future = future;
    return this;
  }

  /**
   * The default DefaultDatasetFactory used for cleanInsert when no
   * {@link DatabaseSetup} annotation is found on the Test
   * class/method. This is a future, so the actual creation can be
   * postboned.
   * 
   * @param factory the default dataset factory as Future
   * @return this
   */
  public DatabaseTesterRule setDefaultDataSetFactory(
      Future<DbUnitDatasetFactory> factory)
  {
    _future = factory;
    return this;
  }

  /**
   * Default implementation returns null. No sorting will be applied.
   * Override to specify sorting format.
   * 
   * @return sorting configuration
   * @throws DataSetException
   */
  protected SortConfig[] getSortConfig() throws DataSetException
  {
    return _defaultSortConfig;
  }

  public Statement apply(final Statement base, final FrameworkMethod method,
      final Object target)
  {
    _target = target;
    return new Statement()
    {
      @Override
      public void evaluate() throws Throwable
      {
        JUnitTestMethodDescriptor descriptor = new JUnitTestMethodDescriptor(method, target, null);
        before(descriptor);
        try
        {
          base.evaluate();
          after(descriptor);
        }
        finally
        {
          close();
        }
      }
    };
  }

  // ##########################################################################
  // # Delegates for Builder Pattern
  // ##########################################################################

  @Override
  public DatabaseTesterRule addDefaultModifier(IDataSetModifier aModifier)
  {
    super.addDefaultModifier(aModifier);
    return this;
  }

  @Override
  public DatabaseTesterRule addDefaultModifier(IDataSetModifier... aModifier)
  {
    super.addDefaultModifier(aModifier);
    return this;
  }

  @Override
  public DatabaseTesterRule setSchema(String schema)
  {
    super.setSchema(schema);
    return this;
  }

  @Override
  public DatabaseTesterRule setClassPathPrefix(String prefixForClasspath)
  {
    super.setClassPathPrefix(prefixForClasspath);
    return this;
  }

  @Override
  public DatabaseTesterRule setTypeFactoryToRegister(
      IDataTypeFactory registerTypeFactory)
  {
    super.setTypeFactoryToRegister(registerTypeFactory);
    return this;
  }

  @Override
  public DatabaseTesterRule addCleanAction(DatabaseTesterCleanAction action)
  {
    super.addCleanAction(action);
    return this;
  }

  @Override
  public DatabaseTesterRule setUseCaseSensitiveNames(
      boolean useCaseSensitiveNames)
  {
    super.setUseCaseSensitiveNames(useCaseSensitiveNames);
    return this;
  }

  public DatabaseTesterRule useTruncateAsCleanInsert()
  {
    _useTruncateAsCleanInsert = true;
    return this;
  }

  protected void doCleanInsert(String prepareDS) throws Exception
  {
    if (_useTruncateAsCleanInsert)
    {
      truncateAndInsert(prepareDS);
    }
    else
    {
      cleanInsert(prepareDS);
    }
  }

  protected void doCleanInsert(ITestMethodDescriptor descriptor,
      Class<? extends DbUnitDatasetFactory>... datasetfactories)
      throws Exception
  {
    if (datasetfactories == null)
    {
      throw new IllegalArgumentException("datasetFactory not given");
    }
    DbUnitDatasetFactory factoryInstance = null;
    if (datasetfactories.length == 1)
    {
      factoryInstance = newInstance(datasetfactories[0]);
    }
    else
    {
      DbUnitDatasetFactory[] factories = new DbUnitDatasetFactory[datasetfactories.length];
      int i = 0;
      for (Class<? extends DbUnitDatasetFactory> factoryClass : datasetfactories)
      {
        factories[i++] = newInstance(factoryClass);
      }
      factoryInstance = DatasetFactoryComposite.of(factories);
    }
    invokePrepareDatasetMethods(descriptor, factoryInstance);
    IDataSet dataset = factoryInstance.createDBUnitDataSet();
    doCleanInsert(dataset);
    trySetAnnotatedField(factoryInstance);
  }

  protected void invokePrepareDatasetMethods(
      ITestMethodDescriptor descriptor,
      DbUnitDatasetFactory factoryInstance)
  {
    DatabasePrepare anno = descriptor.getAnnotation(DatabasePrepare.class, false);
    String explicit = null;
    if (anno != null && !anno.value().isEmpty() && !anno.value().equals(DatabaseSetup.NOT_SET))
    {
      explicit = anno.value();
    }
    Class<? extends Object> clazz = descriptor.getTarget().getClass();
    List<Method> potentialMethods = ReflectionUtils
        .findMethodByAnnotation(clazz, DatabaseBefore.class, true);
    boolean invokedOnce = false;
    for (Method m : potentialMethods)
    {
      Class<?> actual = factoryInstance.getClass();
      if (methodParamEquals(m.getParameterTypes(), new Class[] {actual}))
      {
        boolean invoke = true;
        DatabaseBefore the = m.getAnnotation(DatabaseBefore.class);
        if (the.id() != null && !the.id().isEmpty() && !the.id().equals(DatabaseBefore.NOT_SET))
        {
          invoke = false;
          String id = the.id();
          if (explicit != null && explicit.equals(id))
          {
            invoke = true;
          }
        }
        else
        {
          if (explicit != null)
          {
            invoke = false;
          }
        }
        if (invoke)
        {
          invokedOnce = true;
          ReflectionUtils.invoke(m, descriptor.getTarget(), factoryInstance);
        }
      }
    }
    if (explicit != null && !invokedOnce)
    {
      throw new RuntimeException("Unable to find database prepare method with id = '" + explicit + "'");
    }
  }

  protected boolean methodParamEquals(Class[] actual, Class[] expected)
  {
    if (actual.length != expected.length)
    {
      return false;
    }
    for (int i = 0; i < actual.length; i++)
    {
      Class act = actual[i];
      Class exp = expected[i];
      if (act.equals(exp))
      {
        continue;
      }
      if(!ReflectionUtils.canCast(exp,act) ) 
      {
        return false;
      }
    }
    return true;
  }

  protected void doCleanInsert(IDataSet dataset) throws Exception
  {
    trySetAnnotatedField(dataset);
    if (_useTruncateAsCleanInsert)
    {
      truncate(dataset);
      prepare(dataset, DatabaseOperation.INSERT);
    }
    else
    {
      cleanInsert(dataset);
    }
  }

  @Override
  protected void trySetAnnotatedField(Object set) throws Exception
  {
    Class<?> clazz = _target.getClass();
    trySetAnnotatedField(_target, clazz, set);
  }

  @Override
  protected void setLastInsertedDataSet(IDataSet loadedDS) throws Exception
  {
    super.setLastInsertedDataSet(loadedDS);
    Class<?> clazz = _target.getClass();
    trySetAnnotatedField(_target, clazz, loadedDS);
  }

  protected void trySetAnnotatedField(Object target, Class<?> clazz,
      Object loadedDS) throws Exception
  {
    for (Field field : clazz.getDeclaredFields())
    {
      InjectDataSet anno = field.getAnnotation(InjectDataSet.class);
      if (anno != null)
      {
        field.setAccessible(true);
        if (loadedDS == null)
        {
          field.set(target, null);
          continue;
        }
        else if (field.getType().isAssignableFrom(loadedDS.getClass()))
        {
          field.set(target, loadedDS);
          continue;
        }
      }
    }
    if (!clazz.getSuperclass().equals(Object.class))
    {
      trySetAnnotatedField(target, clazz.getSuperclass(), loadedDS);
    }
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

  @Override
  public DatabaseTesterRule setDbUnitFeature(String feature, boolean value)
  {
    super.setDbUnitFeature(feature, value);
    return this;
  }

  @Override
  public DatabaseTesterRule setDbUnitProperty(String name, Object value)
  {
    super.setDbUnitProperty(name, value);
    return this;
  }

  public DatabaseTesterRule setDefaultDataSetByAnnotation(boolean activate)
  {
    return this;
  }
}
