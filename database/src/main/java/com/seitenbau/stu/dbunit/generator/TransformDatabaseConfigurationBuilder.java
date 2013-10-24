// CHECKSTYLE:OFF
/**
 * Source code generated by Fluent Builders Generator Do not modify
 * this file See generator home page at:
 * http://code.google.com/p/fluent-builders-generator-eclipse-plugin/
 */

package com.seitenbau.stu.dbunit.generator;

public class TransformDatabaseConfigurationBuilder extends
    TransformDatabaseConfigurationBuilderBase<TransformDatabaseConfigurationBuilder>
{
  public static TransformDatabaseConfigurationBuilder transformDatabaseConfiguration()
  {
    return new TransformDatabaseConfigurationBuilder();
  }

  public TransformDatabaseConfigurationBuilder()
  {
    super(new TransformDatabaseConfiguration());
  }

  public TransformDatabaseConfiguration build()
  {
    return getInstance();
  }
}

class TransformDatabaseConfigurationBuilderBase<GeneratorT extends TransformDatabaseConfigurationBuilderBase<GeneratorT>>
{
  private TransformDatabaseConfiguration instance;

  protected TransformDatabaseConfigurationBuilderBase(TransformDatabaseConfiguration aInstance)
  {
    instance = aInstance;
  }

  protected TransformDatabaseConfiguration getInstance()
  {
    return instance;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withUrl(String aValue)
  {
    instance.setUrl(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withUser(String aValue)
  {
    instance.setUser(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withPassword(String aValue)
  {
    instance.setPassword(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withSchema(String aValue)
  {
    instance.setSchema(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withDataSetName(String aValue)
  {
    instance.setDataSetName(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withDataSetPackage(String aValue)
  {
    instance.setDataSetPackage(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withDataSetSourceFolder(String aValue)
  {
    instance.setDataSetSourceFolder(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withOutputClass(String aValue)
  {
    instance.setOutputClass(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withOutputPackage(String aValue)
  {
    instance.setOutputPackage(aValue);

    return (GeneratorT) this;
  }
}