package com.seitenbau.stu.config.impl;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import com.seitenbau.stu.config.impl.DefaultValueProcessor;
import com.seitenbau.stu.config.injectors.ValueInjector;
import com.seitenbau.stu.mockito.MockitoRule;

public class DefaultValueProcessorTest
{
  @Rule
  public MockitoRule mockito = new MockitoRule();

  @Mock
  ValueInjector p1;

  @Mock
  ValueInjector p2;

  DefaultValueProcessor sut = new DefaultValueProcessor()
  {
    @Override
    protected void initList(List<ValueInjector> injectors)
    {
    }
  };

  @Test
  public void defaulTtProcessorsAdded()
  {
    Properties prop = new Properties();
    
    DefaultValueProcessor sut = new DefaultValueProcessor();
    sut.beforeLoading(prop);
    sut.afterLoading(prop);
    
    assertThat(prop.getProperty("_username")).isNotEmpty();
    assertThat(prop.getProperty("_host.ip")).isNotEmpty();
  }
  
  @Test
  public void noProcessorAdded()
  {
    Properties prop = new Properties();

    sut.beforeLoading(prop);
    sut.afterLoading(prop);
    assertThat(prop).hasSize(0);

    verify(p1, never()).injectValues(prop);
    verify(p2, never()).injectValues(prop);
  }

  @Test
  public void oneProcessorAdded()
  {
    // prepare
    Properties prop = new Properties();
    sut.addProcessor(p1);
    sut.beforeLoading(prop);
    sut.afterLoading(prop);
    assertThat(prop).hasSize(0);

    verify(p1, only()).injectValues(prop);
    verify(p2, never()).injectValues(prop);
  }

  @Test
  public void twoProcessorAdded()
  {
    // prepare
    Properties prop = new Properties();
    sut.addProcessor(p1);
    sut.addProcessor(p2);
    sut.beforeLoading(prop);
    sut.afterLoading(prop);
    assertThat(prop).hasSize(0);

    verify(p1, only()).injectValues(prop);
    verify(p2, only()).injectValues(prop);
  }

}
