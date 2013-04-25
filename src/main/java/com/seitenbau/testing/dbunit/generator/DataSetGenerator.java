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
    protected void executeTemplate(VelocityContext context, Template template,
        String into)
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
    this(thePackage, name, DEFAULT_OUTPUT_FOLDER);
  }

  public DataSetGenerator(String thePackage, String name, String targetPath)
  {
    _dataSet = new DataSet(thePackage, name);
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

  public Table addTable(String name, String javaName)
  {
    Table table = new Table(name, javaName);
    _dataSet.addTable(table);
    return table;
  }

  public Table addTable(String name)
  {
    Table table = new Table(name);
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
      generateJavaClasses(targetPath + "/");
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

  protected void generateJavaClasses(String into) throws Exception
  {
    for (Table table : _dataSet.getTables())
    {
      templates.executeTemplate(table, getTemplatePathJavaClass(), into);
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

  protected String getTemplatePathJavaClass()
  {
    return "/templates/db/JavaClass.vm";
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
        throw new Exception("Table Name \"" + tableJavaName
            + "\" ist nicht gültig.");
      }

      for (Column column : table.getColumns())
      {
        String columnJavaName = column.getJavaName();
        if (!JavaNameValidator.isValidIdentifier(columnJavaName))
        {
          throw new Exception("Column Name \"" + columnJavaName
              + "\" of table \"" + tableJavaName + "\" ist nicht gültig.");
        }
      }
    }
  }

  private void checkPackageAndDatabaseName() throws Exception
  {
    String packageName = getDataSet().getPackage();

    if (packageName == null)
    {
      throw new Exception("Package name ist NULL oder leer. "
          + "Der Package Name muss gesetzt werden. "
          + "Dazu die Methode \"packageName\" aufrufen.");
    }

    if (!JavaNameValidator.isValidPackageName(packageName))
    {
      throw new Exception("Der Package Name enthält ungültige Java Namen.");
    }

    String databaseName = getDataSet().getName();

    if (databaseName == null || databaseName.trim().length() == 0)
    {
      throw new RuntimeException("Database Name ist NULL oder leer. "
          + "Der Database Name muss gesetzt werden. "
          + "Dazu die Methode \"database\" aufrufen.");
    }

    if (!JavaNameValidator.isValidIdentifier(databaseName))
    {
      throw new Exception("Der Database Name ist nicht gültig.");
    }
  }

  public void setCaller(String forceCaller)
  {
    _caller = forceCaller;
  }

}
