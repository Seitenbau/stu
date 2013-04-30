package com.seitenbau.testing.dbunit.config;

import com.seitenbau.testing.config.TestConfiguration;
import com.seitenbau.testing.dbunit.TestConfigDatabase;

public class TestConfig implements TestConfigDatabase
{
  static
  {
    TestConfiguration.load(TestConfig.class);
  }
}
