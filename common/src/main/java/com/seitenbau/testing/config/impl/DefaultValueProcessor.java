package com.seitenbau.testing.config.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.seitenbau.testing.config.ValueProcessor;
import com.seitenbau.testing.config.injectors.HostAndIpInjector;
import com.seitenbau.testing.config.injectors.UsernameInjector;
import com.seitenbau.testing.config.injectors.ValueInjector;

public class DefaultValueProcessor implements ValueProcessor
{
  List<ValueInjector> injectors = new ArrayList<ValueInjector>();

  public DefaultValueProcessor()
  {
    initList(injectors);
  }

  protected void initList(List<ValueInjector> injectors)
  {
    injectors.add(new UsernameInjector());
    injectors.add(new HostAndIpInjector());
  }

  public void beforeLoading(Properties prop)
  {
    for (ValueInjector injector : injectors)
    {
      injector.injectValues(prop);
    }
  }

  public void afterLoading(Properties prop)
  {

  }

  public void addProcessor(ValueInjector inj)
  {
    injectors.add(inj);
  }

}
