package com.seitenbau.testing.dbunit.tester;

import org.dbunit.DatabaseUnitException;
import org.junit.Test;
import static com.seitenbau.testing.asserts.fest.Assertions.*;

import com.seitenbau.testing.config.TestConfiguration;
import com.seitenbau.testing.dbunit.TestConfigDatabase;

public class DatabaseTesterBaseTest
{
  @Test
  public void x() throws DatabaseUnitException
  {
    DatabaseTesterBase base = new DatabaseTesterBase(MyConfig.class);
    assertThat(base.fUrl).isEqualTo("test-loadingof-configvalues-dburl");

    // this also works:
    new DatabaseTesterBase(TestConfigDatabase.class);
  }

  static class MyConfig implements TestConfigDatabase
  {
    static
    {
      TestConfiguration.load(MyConfig.class);
    }
  }
}
