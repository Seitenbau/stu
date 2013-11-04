package com.seitenbau.stu.config;

import org.fest.assertions.Fail;
import org.junit.Ignore;
import org.junit.Test;

import com.seitenbau.stu.config.StoredProperty;
import com.seitenbau.stu.config.TestConfigurationBuilder;

import static org.fest.assertions.Assertions.*;

public class TestConfigurationBuilderTest
{
  @Ignore
  @Test
  public void loadInto_Default()
  {
    // prepare
    assertThat(Load01.property1).isNull();

    // execute
    TestConfigurationBuilder sut = TestConfigurationBuilder
        .buildTestConfig();
    sut.loadInto(Load01.class);

    // verify
    assertThat(Load01.property1).isEqualTo("p1-defaultFile");
  }

  @Test
  public void loadInto_FromNonClassPath()
  {
    // prepare
    Load01 cfg = new Load01();

    // execute
    TestConfigurationBuilder sut = TestConfigurationBuilder
        .buildTestConfig()
        .sourcePath("/other/path");
    try
    {
      sut.loadInto(cfg);
      Fail.fail();
    }
    catch (IllegalArgumentException e)
    {
      assertThat(e).hasMessage("currently only classpath: is a supported location");
    }
  }

  @Test
  public void loadInto_FromPath()
  {
    // prepare
    Load01 cfg = new Load01();

    // execute
    TestConfigurationBuilder sut = TestConfigurationBuilder
        .buildTestConfig()
        .sourcePath("classpath:///other/path");
    sut.loadInto(cfg);

    // verify
    assertThat(cfg.property1).isEqualTo("config from another path");
  }

  static class Load01
  {
    @StoredProperty(key = "property1")
    static String property1;

  }
}
