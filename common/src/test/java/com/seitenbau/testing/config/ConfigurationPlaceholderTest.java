//package com.seitenbau.testing.config;
//
//import java.io.IOException;
//import static com.seitenbau.testing.asserts.fest.Assertions.*;
//import static org.fest.assertions.MapAssert.*;
//import java.util.Properties;
//
//import org.fest.assertions.MapAssert.Entry;
//import org.junit.Before;
//import org.junit.Test;
//
//import junit.framework.TestCase;
//
//public class ConfigurationPlaceholderTest extends TestCase
//{
//  @Before
//  public void prepare()
//  {
//    TestConfiguration.setValueProvider(new DummyProvider());
//  }
//
//  @Test
//  public void test() throws IOException
//  {
//    TestConfigurationPlaceholder sut = new TestConfigurationPlaceholder();
//    Properties properties = sut.mergeProperties();
//    assertThat(properties).hasSize(1);
//    assertThat(properties).includes(entry("x", "y"));
//  }
//
//  class DummyProvider implements ValueProvider
//  {
//    public String getString(String key)
//    {
//      return null;
//    }
//
//    public Boolean getBoolean(String key)
//    {
//      return null;
//    }
//
//    public Integer getInteger(String key)
//    {
//      return null;
//    }
//
//    public ValueProvider set(String key, String value)
//    {
//      return null;
//    }
//
//  }
//}
