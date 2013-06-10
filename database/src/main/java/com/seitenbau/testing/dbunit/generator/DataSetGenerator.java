package com.seitenbau.testing.dbunit.generator;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.seitenbau.testing.logger.LogManager;
import com.seitenbau.testing.logger.LogManager.Levels;
import com.seitenbau.testing.logger.LogManager.LogBlock;
import com.seitenbau.testing.logger.Logger;
import com.seitenbau.testing.logger.TestLoggerFactory;
import com.seitenbau.testing.templates.VelocityGenerator;
import com.seitenbau.testing.util.JavaNameValidator;

public class DataSetGenerator
{
  public static final String DEFAULT_OUTPUT_FOLDER = "src/test/generated-java";

  DataSet _dataSet;

  private JavaSourceWriter _sourceWriter;

  private String _targetPath;

  private final VelocityGenerator templates = new VelocityGenerator()
  {
    @Override
    protected void executeTemplate(VelocityContext context, Template template, String into)
    {
      StringWriter sw = new StringWriter();
      template.merge(context, sw);
      String filename = (String) context.get("filename");
      String pkg = (String) context.get("package");
      getSourceWriter().write(into, pkg, filename, sw.toString());
      logger.debug("Written template into : " + filename);
    }
  };

  Logger logger = TestLoggerFactory.get(DataSetGenerator.class);

  String _caller;

  public DataSetGenerator(String thePackage, String name)
  {
    this(thePackage, name, DEFAULT_OUTPUT_FOLDER, true, false);
  }

  public DataSetGenerator(String thePackage, String name, String targetPath, boolean isTableDSLGeneration,
      boolean isModelClassGeneration)
  {
    _dataSet = new DataSet(thePackage, name, isTableDSLGeneration, isModelClassGeneration);
    _targetPath = targetPath;
  }

  public JavaSourceWriter getSourceWriter()
  {
    if (_sourceWriter == null)
    {
      _sourceWriter = new DefaultJavaSourceWriter();
    }
    return _sourceWriter;
  }

  public void setSourceWriter(JavaSourceWriter writer)
  {
    _sourceWriter = writer;
  }

  public Table addTable(Table table)
  {
    _dataSet.addTable(table);
    return table;
  }

  public String getTargetPath()
  {
    return _targetPath;
  }

  public void setTargetPath(String targetPath)
  {
    _targetPath = targetPath;
  }

  public void generateInto(String targetPath) throws Exception
  {
    detectCaller();
    LogBlock level = LogManager.setTemporarily(Levels.TRACE);
    try
    {
      check();
      _dataSet.setCaller(_caller);
      generateDataSet(targetPath + "/");
      generateTables(targetPath + "/");
      if (_dataSet.isModelClassGeneration()) 
      {
        generateTableJavaModels(targetPath + "/");
      }

      generateReferenceClasses(targetPath + "/");
      generateReferenceFactory(targetPath + "/");

      if (_dataSet.isTableDSLGeneration()) 
      {
        generateDSL(targetPath + "/");
        generateTableGateways(targetPath + "/");
      }
    }
    finally
    {
      level.rollback();
    }
  }

  public void generate() throws Exception
  {
    detectCaller();
    generateInto(_targetPath);
  }

  void detectCaller()
  {
    if (_caller == null)
    {
      StackTraceElement last = Thread.currentThread().getStackTrace()[3];
      _caller = "{@link " + last.getClassName() + "#" + last.getMethodName() + " }";
    }
  }

  protected void generateTables(String into) throws Exception
  {
    for (Table table : _dataSet.getTables())
    {
      templates.executeTemplate(table, getTemplatePathTables(), into);
    }
    logger.info("created " + _dataSet.getTables().size() + " Tables");
  }

  protected void generateTableJavaModels(String into) throws Exception
  {
    for (Table table : _dataSet.getTables())
    {
      templates.executeTemplate(table, getTemplatePathTableJavaModel(), into);
    }
    logger.debug("created " + _dataSet.getTables().size() + " Java Classes");
    logger.info("into : " + into);
  }

  protected String getTemplatePathTables()
  {
    return "/templates/db/Table.vm";
  }

  protected String getTemplatePathDataSets()
  {
    return "/templates/db/DataSet.vm";
  }

  protected String getTemplatePathTableJavaModel()
  {
    return "/templates/db/TableJavaModel.vm";
  }

  protected void generateTableGateways(String into) throws Exception
  {
    for (Table table : _dataSet.getTables())
    {
      templates.executeTemplate(table, getTemplatePathTableGateway(), into);
    }
    logger.info("created " + _dataSet.getTables().size() + " Table Gateways");
  }

  protected void generateReferenceClasses(String into) throws Exception
  {
    for (Table table : _dataSet.getTables())
    {
      templates.executeTemplate(table, getTemplatePathReference(), into);
    }
    logger.info("created " + _dataSet.getTables().size() + " Reference Classes");
  }

  protected void generateReferenceFactory(String into) throws Exception
  {
    templates.executeTemplate(_dataSet, getTemplatePathRefFactory(), into);
    logger.info("created 1 Reference Factory class");
  }

  protected void generateDSL(String into) throws Exception
  {
    templates.executeTemplate(_dataSet, getTemplatePathDSLBuilder(), into);
    logger.info("created 1 DSL class");
  }

  protected String getTemplatePathTableGateway()
  {
    return "/templates/db/TableGateway.vm";
  }

  protected String getTemplatePathReference()
  {
    return "/templates/db/Ref.vm";
  }

  protected String getTemplatePathRefFactory()
  {
    return "/templates/db/RefFactory.vm";
  }

  protected String getTemplatePathDSLBuilder()
  {
    return "/templates/db/Builder.vm";
  }

  protected void generateDataSet(String into) throws Exception
  {
    templates.executeTemplate(_dataSet, getTemplatePathDataSets(), into);
    logger.info("created 1 DataSet");
  }

  public void catchException(String exception)
  {
    _dataSet.letCreateThrow(exception);
  }

  public void catchException(Class<?> class1)
  {
    catchException(class1.getCanonicalName());
  }

  public DataSet getDataSet()
  {
    return this._dataSet;
  }

  /**
   * Check if the package name, the database name, the table names,
   * and the column names are valid Java identifiers.
   * @throws Exception if one of the names is not valid
   */
  public void check() throws Exception
  {
    checkPackageAndDatabaseName();

    for (Table table : getDataSet().getTables())
    {
      String tableJavaName = table.getJavaName();
      if (!JavaNameValidator.isValidIdentifier(tableJavaName))
      {
        throw new Exception("Table Name \"" + tableJavaName + "\" in not valid.");
      }

      for (Column column : table.getColumns())
      {
        String columnJavaName = column.getJavaName();
        if (!JavaNameValidator.isValidIdentifier(columnJavaName))
        {
          throw new Exception("Column Name \"" + columnJavaName + "\" of table \"" + tableJavaName + "\" is not valid.");
        }
      }
    }
  }

  private void checkPackageAndDatabaseName() throws Exception
  {
    String packageName = getDataSet().getPackage();

    if (packageName == null)
    {

      throw new Exception(
          "The package name is NULL or empty. The package name must be set. To achieve this the method \"packageName\" must be called.");
    }

    if (!JavaNameValidator.isValidPackageName(packageName))
    {
      throw new Exception("The package name contains an invalid Java name.");
    }

    String databaseName = getDataSet().getName();

    if (databaseName == null || databaseName.trim().length() == 0)
    {
      throw new RuntimeException(
          "The database name is NULL or empty. The database name must be set. To achieve this the method \"database\" must be called.");
    }

    if (!JavaNameValidator.isValidIdentifier(databaseName))
    {
      throw new Exception("The database name is not valid.");
    }
  }

  public void setCaller(String forceCaller)
  {
    _caller = forceCaller;
  }

}
