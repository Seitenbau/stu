package com.seitenbau.testing.config;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.fest.assertions.Fail;
import org.junit.Before;
import org.junit.Test;

public class TestConfigurationCompatabilityTest
{
  @Before
  public void resetConfig()
  {
    TestConfiguration.reset();
  }

  @Test
  public void testLoading_DataTypeTests() throws Exception
  {
    TestConfiguration.setEnvironmentProvider(new EnvironmentDetector()
    {
      public String[] getEnvironmentIds()
      {
        return new String[] {"compatability"};
      }
    });

    Load01 cfg = new Load01();
    TestConfiguration.load(cfg);
    // verify
    // strings
    assertThat(cfg.notAnnotated).isNull();
    assertThat(cfg.fromDefaultFile).isEqualTo("p1-defaultFile");
    assertThat(cfg.overwritten).isEqualTo("this was overwritten");
    assertThat(cfg.escaping).isEqualTo("\"anSpace\"");

    // numbers
    assertThat(cfg.integer).isEqualTo(42);
    assertThat(cfg.along).isEqualTo(23l);

    // enums
    assertThat(cfg.timeUnit).isEqualTo(TimeUnit.DAYS);
    assertThat(cfg.timeUnitMs).isEqualTo(TimeUnit.MICROSECONDS);

    // maps
    assertThat(cfg.stringMap)
        .isNotNull()
        .hasSize(2)
        .includes(entry("key1", "value1"))
        .includes(entry("theOtherKeyIsBetter", "and this Value is "));
  }

  @Test
  public void testLoading_NotFound_AkaMagicValueNotSet() throws Exception
  {
    TestConfiguration.setEnvironmentProvider(new EnvironmentDetector()
    {
      public String[] getEnvironmentIds()
      {
        return new String[] {"compatability"};
      }
    });

    Load02 cfg = new Load02();

    // switch back to old behaviour
    TestConfiguration.setFastFail(false);
    TestConfiguration.load(cfg);

    // verify
    assertThat(cfg.noFound).isEqualTo(StoredProperty.NOT_SET_VALUE);
  }

  @Test
  public void testLoading_NotFound_throwError() throws Exception
  {
    TestConfiguration.setEnvironmentProvider(new EnvironmentDetector()
    {
      public String[] getEnvironmentIds()
      {
        return new String[] {"compatability"};
      }
    });

    Load02 cfg = new Load02();

    try
    {
      TestConfiguration.load(cfg);
      Fail.fail();
    }
    catch (RuntimeException e)
    {
      assertThat(e).hasMessage("Unable to find a value for the property : noFound");
    }

  }

  @Test
  public void testLoading_VariableProcessing() throws Exception
  {
    TestConfiguration.setEnvironmentProvider(new EnvironmentDetector()
    {
      public String[] getEnvironmentIds()
      {
        return new String[] {"compatability"};
      }
    });

    Load03 cfg = new Load03();
    TestConfiguration.load(cfg);

    // verify
    assertThat(cfg.fixed).isEqualTo("http://p1-defaultFile/example");
    assertThat(cfg.overwrite).isEqualTo("myValue");
    assertThat(cfg.overwritten).isEqualTo("http://myValue/example");
    assertThat(cfg.myexpression).isEqualTo("http://p1-defaultFile/example/myValue");
  }

  @Test
  public void testLoading_FailVariableProcessing() throws Exception
  {
    TestConfiguration.setEnvironmentProvider(new EnvironmentDetector()
    {
      public String[] getEnvironmentIds()
      {
        return new String[] {"compatability"};
      }
    });

    try
    {
      TestConfiguration.load(new Load04());
      Fail.fail();
    }
    catch (RuntimeException e)
    {
      assertThat(e).hasMessage("Was not able to resolve config property : nonExisting");
    }
  }

  static class Load01
  {
    String notAnnotated;

    @StoredProperty(key = "property1")
    String fromDefaultFile;

    @StoredProperty(key = "property2")
    String overwritten;

    @StoredProperty(key = "property_with[%&]EsapesAndSpaces")
    String escaping;

    @StoredProperty(key = "num.integer")
    Integer integer;

    @StoredProperty(key = "num.long")
    Long along;

    @StoredProperty(key = "enum.time")
    TimeUnit timeUnit;

    @StoredProperty(key = "enum.time.ms")
    TimeUnit timeUnitMs;

    @StoredProperty(key = "map.stringmap")
    Map<String, String> stringMap;
  }

  static class Load02
  {
    @StoredProperty(key = "noFound")
    String noFound;
  }

  static class Load03
  {
    @StoredProperty(key = "var.fixed")
    String fixed;

    @StoredProperty(key = "overwrite")
    String overwrite;

    @StoredProperty(key = "var.overwrite")
    String overwritten;

    @StoredProperty(key = "myexpression")
    String myexpression;
  }

  static class Load04
  {
    @StoredProperty(key = "myexpressionFails")
    String myexpressionFails;
  }
}
