package com.seitenbau.stu.config.impl;

import static com.seitenbau.stu.asserts.fest.Assertions.*;
import static org.fest.assertions.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.fest.assertions.Fail;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import com.seitenbau.stu.config.StoredProperty;
import com.seitenbau.stu.config.ValueProcessor;
import com.seitenbau.stu.mockito.MockitoRule;
import com.seitenbau.stu.util.Holder;

public class BeanConfigInjectorTest
{

  PersistentConfiguration values;

  @Rule
  public MockitoRule mockito = new MockitoRule();

  @Mock
  ValueProcessor noProcessor;

  @Before
  public void setup()
  {
    values = new PersistentConfiguration(new VariableDslProcessor());
    values.initValuesFor(new String[] {"none"}, noProcessor);
    values.set("value", "aValue");
    values.set("next", "nextValue");
    Map<String, String> map = new HashMap<String, String>();
    map.put("key1", "val1");
    map.put("key2", "val2");
    values.setMap("values", map);
  }

  @Test
  public void notAnnotated()
  {
    Object into = new Object();
    new BeanConfigInjector().injectValuesInto(values, into);
    assertThat(into).isNotNull();
  }

  @Test
  public void simple()
  {
    Simple into = new Simple();
    new BeanConfigInjector().injectValuesInto(values, into);
    assertThat(into.value).isEqualTo("aValue");
    assertThat(into.value2).isEqualTo("default-value2");
  }

  @Test
  public void expand()
  {
    Expand into = new Expand();
    new BeanConfigInjector().injectValuesInto(values, into);
    assertThat(into.value).isEqualTo("ItIsaValue,and$_another:aValue_${none}");
  }

  @Test
  public void complex()
  {
    Complex into = new Complex();
    new BeanConfigInjector(false).injectValuesInto(values, into);
    assertThat(into.values).isNotNull();
    assertThat(into.values).hasSize(2);
    assertThat(into.values.get("key1")).isEqualTo("val1");
    assertThat(into.values.get("key2")).isEqualTo("val2");
    // not supported at the moment
    assertThat(into.key1).isEqualTo(StoredProperty.NOT_SET_VALUE);
    assertThat(into.key2).isEqualTo("_${values[key1]}");
  }
  
  @Test
  public void holders()
  {
    SomeHolders into = new SomeHolders();
    new BeanConfigInjector(false).injectValuesInto(values, into);
    assertThat(into.value2.getValue()).isEqualTo("nextValue");
    assertThat(into.notSet.getValue()).isEqualTo(StoredProperty.NOT_SET_VALUE);
    assertThat(into.value.getValue()).isEqualTo("aValue");
  }

  @Test
  public void complex_fail()
  {
    Complex into = new Complex();
    try
    {
      new BeanConfigInjector().injectValuesInto(values, into);
      Fail.fail();
    }
    catch (RuntimeException e)
    {
      assertThat(e).hasMessage("Unable to find a value for the property : values[key1]");
    }

  }

  class Simple
  {
    @StoredProperty(key = "value")
    String value;

    @StoredProperty(key = "value2")
    String value2;
  }

  class Expand 
  {
    @StoredProperty(key = "ItIs${value},and$_another:${value}_${none}")
    String value;
  }

  class Complex
  {
    @StoredProperty(key = "values")
    Map<String, String> values;

    // not supported at the moment
    @StoredProperty(key = "values[key1]")
    String key1;

    @StoredProperty(key = "_${values[key1]}")
    String key2;
  }
  
  interface Holders 
  {
    @StoredProperty(key = "value")
    Holder<String> value = new Holder<String>();
  }
  
  class SomeHolders implements Holders 
  {
    @StoredProperty(key = "next")
    Holder<String> value2;
    
    @StoredProperty(key = "notSet")
    Holder<String> notSet = new Holder<String>();
  }
}
