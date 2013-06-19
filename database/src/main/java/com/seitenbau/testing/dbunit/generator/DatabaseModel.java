package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseModel
{

  DataSetGenerator generator;

  String databaseName;

  String packageName;

  String targetPath = DataSetGenerator.DEFAULT_OUTPUT_FOLDER;

  boolean isModelClassGeneration;

  boolean isTableDSLGeneration;

  final List<Table> tables = new ArrayList<Table>();

  protected String __forceCaller;

  public DatabaseModel()
  {
    isModelClassGeneration = false;
    isTableDSLGeneration = true;
  }

  public DataSetGenerator getDataSetGenInstance()
  {
    if (generator == null)
    {
      generator = new DataSetGenerator(packageName, databaseName, targetPath, isTableDSLGeneration,
          isModelClassGeneration);
    }
    return generator;
  }

  public void database(String name)
  {
    this.databaseName = name;
  }

  public void packageName(String name)
  {
    this.packageName = name;
  }

  public void generatedSourceFolder(String folder)
  {
    if (generator == null)
    {
      this.targetPath = folder;
    }
    else
    {
      generator.setTargetPath(folder);
    }
  }

  public void catchException(String exception)
  {
    getDataSetGenInstance().catchException(exception);
  }

  /**
   * Adds a table to the database model.
   * @param name The name of the table
   * @return The builder to configure the table.
   */
  public TableBuilder table(String name)
  {
    return new TableBuilder(this, name);
  }

  /**
   * Adds a built table to the data set
   * @param table The Table built by a TableBuilder
   */
  void addTable(Table table)
  {
    getDataSetGenInstance().addTable(table);
  }

  /**
   * Starts the generation of the DSL model classes
   * @throws Exception
   */
  public void generate() throws Exception
  {
    DataSetGenerator gen = getDataSetGenInstance();

    if (__forceCaller != null)
    {
      gen.setCaller(__forceCaller);
    }
    gen.generate();
  }

  public void generateInto(String folder) throws Exception
  {
    DataSetGenerator gen = getDataSetGenInstance();
    if (__forceCaller != null)
    {
      gen.setCaller(__forceCaller);
    }
    gen.generateInto(folder);
  }

  public void enableTableModelClassesGeneration()
  {
    isModelClassGeneration = true;
  }

  public void disbaleTableDSLGeneration()
  {
    isTableDSLGeneration = false;
  }

}
