package com.seitenbau.stu.dbunit.generator;

public class TransformDatabaseConfiguration
{
  private String url;

  private String user;

  private String password;

  private String schema;

  private String dataSetName;

  private String dataSetPackage;

  private String dataSetSourceFolder;

  private String outputClass;

  private String outputPackage;

  public String getUrl()
  {
    return url;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }

  public String getUser()
  {
    return user;
  }

  public void setUser(String user)
  {
    this.user = user;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getSchema()
  {
    return schema;
  }

  public void setSchema(String schema)
  {
    this.schema = schema;
  }

  public String getDataSetName()
  {
    return dataSetName;
  }

  public void setDataSetName(String dataSetName)
  {
    this.dataSetName = dataSetName;
  }

  public String getDataSetPackage()
  {
    return dataSetPackage;
  }

  public void setDataSetPackage(String dataSetPackage)
  {
    this.dataSetPackage = dataSetPackage;
  }

  public String getDataSetSourceFolder()
  {
    return dataSetSourceFolder;
  }

  public void setDataSetSourceFolder(String dataSetSourceFolder)
  {
    this.dataSetSourceFolder = dataSetSourceFolder;
  }

  public String getOutputClass()
  {
    return outputClass;
  }

  public void setOutputClass(String outputClass)
  {
    this.outputClass = outputClass;
  }

  public String getOutputPackage()
  {
    return outputPackage;
  }

  public void setOutputPackage(String outputPackage)
  {
    this.outputPackage = outputPackage;
  }
}