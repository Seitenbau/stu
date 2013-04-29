package com.seitenbau.testing.config.impl;

import java.util.HashMap;
import java.util.Map;
import static com.seitenbau.testing.asserts.fest.Assertions.*;

import org.fest.assertions.Fail;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.seitenbau.testing.config.StoredProperty;
import com.seitenbau.testing.config.ValueProcessor;
import com.seitenbau.testing.mockito.MockitoRule;
import com.seitenbau.testing.util.Holder;

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
    values.set("wert", "einWert");
    values.set("next", "nextWert");
    Map<String, String> map = new HashMap<String, String>();
    map.put("key1", "val1");
    map.put("key2", "val2");
    values.setMap("werte", map);
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
    assertThat(into.wert).isEqualTo("einWert");
    assertThat(into.wert2).isEqualTo("default-wert2");
  }

  @Test
  public void expand()
  {
    Expand into = new Expand();
    new BeanConfigInjector().injectValuesInto(values, into);
    assertThat(into.wert).isEqualTo("EsIsteinWert,und$_nochEiner:einWert_${none}");
  }

  @Test
  public void complex()
  {
    Complex into = new Complex();
    new BeanConfigInjector(false).injectValuesInto(values, into);
    assertThat(into.werte).isNotNull();
    assertThat(into.werte).hasSize(2);
    assertThat(into.werte.get("key1")).isEqualTo("val1");
    assertThat(into.werte.get("key2")).isEqualTo("val2");
    // not supported at the moment
    assertThat(into.key1).isEqualTo(StoredProperty.NOT_SET_VALUE);
    assertThat(into.key2).isEqualTo("_${werte[key1]}");
  }
  
  @Test
  public void holders()
  {
    SomeHolders into = new SomeHolders();
    new BeanConfigInjector(false).injectValuesInto(values, into);
    assertThat(into.wert2.getValue()).isEqualTo("nextWert");
    assertThat(into.notSet.getValue()).isEqualTo(StoredProperty.NOT_SET_VALUE);
    assertThat(into.wert.getValue()).isEqualTo("einWert");
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
      assertThat(e).hasMessage("Unable to find a value for the property : werte[key1]");
    }

  }

  class Simple
  {
    @StoredProperty(key = "wert", defaultValue = "default-wert1")
    String wert;

    @StoredProperty(key = "wert2", defaultValue = "default-wert2")
    String wert2;
  }

  class Expand
  {
    @StoredProperty(key = "EsIst${wert},und$_nochEiner:${wert}_${none}", defaultValue = "default-wert2")
    String wert;
  }

  class Complex
  {
    @StoredProperty(key = "werte")
    Map<String, String> werte;

    // not supported at the moment
    @StoredProperty(key = "werte[key1]")
    String key1;

    @StoredProperty(key = "_${werte[key1]}")
    String key2;
  }
  
  interface Holders 
  {
    @StoredProperty(key = "wert")
    Holder<String> wert = new Holder<String>();
  }
  
  class SomeHolders implements Holders 
  {
    @StoredProperty(key = "next")
    Holder<String> wert2;
    
    @StoredProperty(key = "notSet")
    Holder<String> notSet = new Holder<String>();
  }
}
