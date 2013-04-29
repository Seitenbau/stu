package com.seitenbau.testing.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.seitenbau.testing.config.impl.BeanConfigInjector;
import com.seitenbau.testing.config.impl.DefaultEnvironmentDetection;
import com.seitenbau.testing.config.impl.DefaultValueProcessor;
import com.seitenbau.testing.config.impl.PersistentConfiguration;
import com.seitenbau.testing.config.impl.VariableDslProcessor;

public class TestConfigurationIntern
{
  protected EnvironmentDetector defaultEnvProvider;

  protected ValueProvider config;

  protected ValueProvider defaultValueProvider;

  protected boolean _fastFail = true;

  protected ValueProcessor processor;

  public ValueProvider load(Object target, boolean print, String additionalEnvironment)
  {
    ValueProvider cfg = getConfig(additionalEnvironment, true);

    createBeanInjector().injectValuesInto(cfg, target);

    if (print && cfg instanceof ValueProvider.ValuesPrintable)
    {
      ((ValueProvider.ValuesPrintable) cfg).printValues();
    }
    return cfg;
  }

  protected BeanConfigInjector createBeanInjector()
  {
    return new BeanConfigInjector(_fastFail);
  }
  
  protected ValueProvider getConfig(String environemtType, boolean allowAutoload)
  {
    if (config == null)
    {
      if (!allowAutoload)
      {
        throw new RuntimeException("No configuration is laoded");
      }
      ValueProvider cfg = getValueProvider();
      String[] environment = getEnvironmentProvider().getEnvironmentIds();
      if (environemtType != null)
      {
        List<String> el = new ArrayList<String>();
        el.add("[" + environemtType + "]");
        for (String env : environment)
        {
          el.add(env);
          el.add("[" + environemtType + "]" + env);
        }
        environment = el.toArray(new String[] {});
      }
      cfg.initValuesFor(environment,getProcessor());
      config = cfg;
    }
    return config;
  }
  
  protected ValueProcessor getProcessor()
  {
    if(processor == null) 
    {
      processor = createDefaultProcessor();
    }
    return processor;
  }

  protected ValueProcessor createDefaultProcessor()
  {
    return new DefaultValueProcessor();
  }

  protected ValueProvider getValueProvider()
  {
    if (defaultValueProvider == null)
    {
      defaultValueProvider = createValueProvider(createDslProcessor());
    }
    return defaultValueProvider;
  }

  protected PersistentConfiguration createValueProvider(VariableDslProcessor variableDslProcessor)
  {
    return new PersistentConfiguration(variableDslProcessor);
  }

  protected VariableDslProcessor createDslProcessor()
  {
    return new VariableDslProcessor();
  }
  
  protected void loadProperties(Properties props)
  {
    String[] environments = getEnvironmentProvider().getEnvironmentIds();
    PersistentConfiguration state = createValueProvider(createDslProcessor());
    state.initValuesFor(environments,getProcessor());
    Properties mine = state.getProperties();
    for (Object nameObj : mine.keySet())
    {
      String name = (String) nameObj;
      String value = mine.getProperty(name);
      props.setProperty(name, value);
    }
    config = state;
  }

  protected EnvironmentDetector getEnvironmentProvider()
  {
    if (defaultEnvProvider == null)
    {
      defaultEnvProvider = new DefaultEnvironmentDetection();
    }
    return defaultEnvProvider;
  }

  public void setEnvironmentProvider(EnvironmentDetector environmentDetector)
  {
    defaultEnvProvider = environmentDetector;
  }

  public void reset()
  {
    config = null;
    defaultValueProvider = null;
    processor = null;
  }

  public void setFastFail(boolean fastFail)
  {
    _fastFail = fastFail;
  }

  protected void setValueProvider(PersistentConfiguration provider)
  {
    defaultValueProvider=provider;
  }

  public void setValueProcessor(ValueProcessor processor)
  {
    this.processor=processor;
  }

}
