package com.seitenbau.stu.database.tester;

import org.dbunit.DatabaseUnitException;
import org.junit.Test;

import static com.seitenbau.stu.asserts.fest.Assertions.*;

import com.seitenbau.stu.config.TestConfiguration;
import com.seitenbau.stu.database.TestConfigDatabase;
import com.seitenbau.stu.database.tester.DatabaseTesterBase;

public class DatabaseTesterBaseTest
{
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  public void testDatabaseTesterBaseConstructors() throws DatabaseUnitException
  {
    DatabaseTesterBase base = new DatabaseTesterBase(MyConfig.class);
    assertThat(base.fUrl).isEqualTo("test-loadingof-configvalues-dburl");

    // this also works:
    new DatabaseTesterBase(TestConfigDatabase.class);
  }
  
  @Test
  public void testSetSchema() throws DatabaseUnitException
  {
    String driverName = "mysql";
    String url = "mysql:test-url";
    String username = "dummy-db-user";
    String password = "12345";
    @SuppressWarnings({"rawtypes", "unchecked"})
    DatabaseTesterBase base = new DatabaseTesterBase(driverName, url, username, password, this.getClass());
    String schema = "dummy-test-schema";
    base.setSchema(schema);
    assertThat(base.fSchema).isEqualTo(schema);
    assertThat(base.fDriverName).isEqualTo(driverName);
    assertThat(base.fUrl).isEqualTo(url);
    assertThat(base.fUsername).isEqualTo(username);
    assertThat(base.fPassword).isEqualTo(password);
  }

  static class MyConfig implements TestConfigDatabase
  {
    static
    {
      TestConfiguration.load(MyConfig.class);
    }
  }
}
