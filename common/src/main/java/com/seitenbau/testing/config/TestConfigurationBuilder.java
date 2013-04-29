package com.seitenbau.testing.config;

import com.seitenbau.testing.config.impl.PersistentConfiguration;

public class TestConfigurationBuilder
{
  protected String _sourcePath;

  protected TestConfigurationBuilder()
  {
    // not public
  }

  protected TestConfigurationBuilder(TestConfigurationBuilder cloneFrom)
  {
    cloneFieldsFrom(cloneFrom);
  }

  protected void cloneFieldsFrom(TestConfigurationBuilder cloneFrom)
  {
  }

  public static TestConfigurationBuilder buildTestConfig()
  {
    return new TestConfigurationBuilder();
  }

  public void loadInto(Class<?> clazzToLoad) {
    load(clazzToLoad);
  }
  
  public void loadInto(Object toLoad)
  {
    load(toLoad);
  }

  protected void load(Object toLoad)
  {
    TestConfigurationIntern loaded = new TestConfigurationIntern();
    
    if(_sourcePath!=null) {
      // TODO : remove code from TestConfigurationIntern so the creation code is in one place
      //        and is always done via this builder
      PersistentConfiguration provider = new PersistentConfiguration(loaded.createDslProcessor());
      provider.setConfigPath(_sourcePath);
      loaded.setValueProvider( provider );
    }
    
    loaded.load(toLoad, false, null);
  }

  public TestConfigurationBuilder sourcePath(String pathToConfigFolder)
  {
    TestConfigurationBuilder clone = new TestConfigurationBuilder(this);
    clone._sourcePath = pathToConfigFolder;
    return clone;
  }

}
