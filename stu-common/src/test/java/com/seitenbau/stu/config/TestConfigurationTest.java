package com.seitenbau.stu.config;

import static org.fest.assertions.Assertions.*;

import org.junit.Before;
import org.junit.Test;

public class TestConfigurationTest
{
  @Before
  public void resetConfig()
  {
    TestConfiguration.reset();
  }
  
  @Test
  public void testLoadingStatic() throws Exception
  {
    TestConfiguration.setEnvironmentProvider(new EnvironmentDetector()
    {
      public String[] getEnvironmentIds()
      {
        return new String[] {"fix-provider-id", "fix-provider-id-max"};
      }
    });
    assertThat(TestConfiguration.getString("property1")).isEqualTo(
        "p1-defaultFile");
    assertThat(TestConfiguration.getString("property2")).isEqualTo(
        "p2-maxproviderFile");
    assertThat(TestConfiguration.getString("property3")).isEqualTo(
        "p3-providerFile");
    assertThat(TestConfiguration.getString("property4")).isEqualTo(
        "p4-providerFileOverwrite");
    assertThat(TestConfiguration.getString("overwritten")).isEqualTo("true");
  }

  @Test
  public void testLoading() throws Exception
  {
    MyProperties values = new MyProperties();
    TestConfiguration.setEnvironmentProvider(new EnvironmentDetector()
    {
      public String[] getEnvironmentIds()
      {
        return new String[] {"fix-provider-id"};
      }
    });
    TestConfiguration.load(values);
    assertThat(values.prop1).isEqualTo("p1-defaultFile");
    assertThat(values.prop2).isEqualTo("p2-defaultFile");
    assertThat(values.prop3).isEqualTo("p3-providerFile");
    assertThat(values.prop4).isEqualTo("p4-providerFileOverwrite");
    assertThat(values.overwritten).isEqualTo("true");
  }

  @Test
  public void testCombinedLoading() throws Exception
  {
    MyPropertiesCombined values = new MyPropertiesCombined();
    TestConfiguration.setEnvironmentProvider(new EnvironmentDetector()
    {
      public String[] getEnvironmentIds()
      {
        return new String[] {"fix-provider-id"};
      }
    });
    TestConfiguration.load(values);
    assertThat(values.prop1).isEqualTo("p1-defaultFile");
    assertThat(values.prop2).isEqualTo("p2-defaultFile");
    assertThat(values.prop3).isEqualTo("p2-defaultFile");
    assertThat(values.prop4).isEqualTo("property4p2-defaultFile");
    assertThat(values.overwritten).isEqualTo("true");
  }

  public static class MyProperties
  {
    String prop0;

    @StoredProperty(key = "property1")
    String prop1;

    @StoredProperty(key = "property2")
    String prop2;

    @StoredProperty(key = "property3")
    String prop3;

    @StoredProperty("property4")
    String prop4;

    @StoredProperty(key = "overwritten")
    String overwritten;
  }

  public static class MyPropertiesCombined
  {
    String prop0;

    @StoredProperty(key = "property1")
    String prop1;

    @StoredProperty(key = "property2")
    String prop2;

    @StoredProperty( "${property2}")
    String prop3;

    @StoredProperty(key = "property4${property2}")
    String prop4;

    @StoredProperty(key = "overwritten")
    String overwritten;
  }
  
  public enum FruitEnum
  {
    BANNA,
    APPLE,
    MANDARIN
  }
  
  public static class EnumConfiguration
  {
    @StoredProperty(key = "test.fruits")
    FruitEnum fruit;
  }
  
  @Test
  public void testConfigurationWithEnum() throws Exception
  {
    EnumConfiguration enumConfiguration = new EnumConfiguration();
    TestConfiguration.setEnvironmentProvider(new EnvironmentDetector()
    {
      public String[] getEnvironmentIds()
      {
        return new String[] {"enum-config-provider"};
      }
    });
    TestConfiguration.load(enumConfiguration);
    assertThat(enumConfiguration.fruit).isEqualTo(FruitEnum.APPLE);
  }
  
}
