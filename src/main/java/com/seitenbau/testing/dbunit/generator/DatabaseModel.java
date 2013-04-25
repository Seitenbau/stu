package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseModel
{

  DataSetGenerator generator;

  String databaseName;

  String packageName;

  String targetPath = DataSetGenerator.DEFAULT_OUTPUT_FOLDER;

  final List<Table> tables = new ArrayList<Table>();

  protected String __forceCaller;

  public DataSetGenerator getDataSetGenInstance()
  {
    if (generator == null)
    {
      generator = new DataSetGenerator(packageName, databaseName, targetPath);
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

  public Table addTable(String name, String javaName)
  {
    return getDataSetGenInstance().addTable(name, javaName);
  }

  public Table addTable(String name)
  {
    return getDataSetGenInstance().addTable(name);
  }

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

}
