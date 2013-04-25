package com.seitenbau.testing.dbunit.generator;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.ITableMetaData;

import com.seitenbau.testing.templates.VelocityGenerator;

/**
 * Generate an implementation of {@link DatabaseModel} using the
 * structure of an existing database.
 */
public class TransformDatabase
{
  /** Path to Velocity template used for model class generation. */
  private static final String TEMPLATE = "/templates/db/DatabaseModel.vm";

  /** Database JDBC connection URL. */
  private final String url;

  /** Database user name. */
  private final String user;

  /** Database user password. */
  private final String password;

  /** Database schema (optional). */
  private final String schema;

  /** Data set name. */
  private final String dataSetName;

  /** Data set package. */
  private final String dataSetPackage;

  /** Data set source folder (optional). */
  private final String dataSetSourceFolder;

  /** Generated model class. */
  private final String outputClass;

  /** Generated model package. */
  private final String outputPackage;

  /** Database tables. */
  private final ArrayList<ITableMetaData> tables;

  /** Database connection. */
  private IDatabaseConnection connection;

  /**
   * Constructor.
   * @param configuration TODO
   */
  public TransformDatabase(TransformDatabaseConfigurationBuilder builder)
  {
    TransformDatabaseConfiguration configuration = builder.build();
    this.url = configuration.getUrl();
    this.user = configuration.getUser();
    this.password = configuration.getPassword();
    this.schema = configuration.getSchema();
    this.dataSetName = configuration.getDataSetName();
    this.dataSetPackage = configuration.getDataSetPackage();
    this.dataSetSourceFolder = configuration.getDataSetSourceFolder();
    this.outputClass = configuration.getOutputClass();
    this.outputPackage = configuration.getOutputPackage();
    this.tables = new ArrayList<ITableMetaData>();
  }

  /**
   * Close used database connection.
   * @throws SQLException
   */
  public void close() throws SQLException
  {
    if (connection == null)
    {
      return;
    }

    connection.close();
  }

  /**
   * Generate model class.
   * @param outputFolder output folder where the source file will be
   *        generated
   * @throws IOException
   * @throws Exception
   */
  public void transform(String outputFolder) throws IOException, Exception
  {
    ITableIterator i = getConnection().createDataSet().iterator();
    while (i.next())
    {
      tables.add(i.getTableMetaData());
    }
    executeTemplate(outputFolder);
  }

  // private methods
  /**
   * Get database connection; if none exists it will be created.
   * @return active database connection
   * @throws SQLException
   * @throws DatabaseUnitException
   */
  private IDatabaseConnection getConnection() throws SQLException,
      DatabaseUnitException
  {
    if (connection == null)
    {
      connection = new DatabaseConnection(DriverManager.getConnection(url,
          user, password), schema);
    }
    return connection;
  }

  /**
   * Execute Velocity template.
   * @param outputFolder output folder where the source file will be
   *        generated
   * @throws IOException
   * @throws Exception
   */
  private void executeTemplate(String outputFolder) throws IOException,
      Exception
  {
    new VelocityGenerator().executeTemplate(this, TEMPLATE, outputFolder + '/');
  }

  // getters used by velocity template
  public String getOutputClass()
  {
    return outputClass;
  }

  public String getOutputPackage()
  {
    return outputPackage;
  }

  public String getDataSetPackage()
  {
    return dataSetPackage;
  }

  public String getDataSetName()
  {
    return dataSetName;
  }

  public String getDataSetSourceFolder()
  {
    return dataSetSourceFolder;
  }

  public ITableMetaData[] getTables()
  {
    return tables.toArray(new ITableMetaData[tables.size()]);
  }
}
