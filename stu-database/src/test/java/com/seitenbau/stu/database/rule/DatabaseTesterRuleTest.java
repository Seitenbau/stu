package com.seitenbau.stu.database.rule;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import static org.fest.assertions.Assertions.*;

import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;

import com.seitenbau.stu.database.extend.DbUnitDatasetFactory;
import com.seitenbau.stu.database.rule.DatabaseSetup;
import com.seitenbau.stu.database.rule.DatabaseTesterRule;
import com.seitenbau.stu.database.rule.InjectDataSet;
import com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptor;
import com.seitenbau.stu.util.Future;

public class DatabaseTesterRuleTest
{

  @Test
  public void testBefore_BugNoDefaultDsIsFixed() throws Exception
  {
    DatabaseTesterRule sut = new DatabaseTesterRule(((Future<DataSource>) null));
    sut.before(method("doNotRenameThisMethod_NoModification"));
  }

  JUnitTestMethodDescriptor method(String name) throws SecurityException, NoSuchMethodException
  {
    Method method = DatabaseTesterRuleTest.class.getDeclaredMethod(name);
    return new JUnitTestMethodDescriptor(new FrameworkMethod(method), this, null);
  }

  @DatabaseSetup(assertNoModification = true)
  public void doNotRenameThisMethod_NoModification()
  {

  }

  static class SampleDataSetFactory implements DbUnitDatasetFactory
  {

    public IDataSet createDBUnitDataSet()
    {
      return null;
    }
  }

  @DatabaseSetup(prepare = SampleDataSetFactory.class)
  public static class InjectionTest
  {
    @Rule
    public DatabaseTesterRule db = new DatabaseTesterRule(null, "", "", "")
    {
      @Override
      public void prepare(IDataSet dataset, DatabaseOperation operation,
          com.seitenbau.stu.database.modifier.IDataSetModifier[] modifiers) throws Exception
      {
      };

      @Override
      public void prepare(DbUnitDatasetFactory datasetFactory, org.dbunit.operation.DatabaseOperation operation,
          com.seitenbau.stu.database.modifier.IDataSetModifier[] modifiers) throws Exception
      {
      };
    };

    @InjectDataSet
    SampleDataSetFactory ds;

    @Test
    public void testInjection() throws Exception
    {
      assertThat(ds).isInstanceOf(SampleDataSetFactory.class);
    }
  }

  public static class InjectionTest2 extends InjectionTest
  {
    @InjectDataSet
    String ds2;

    @Test
    public void testInjectionInHierachy() throws Exception
    {
      assertThat(ds).isInstanceOf(SampleDataSetFactory.class);
      assertThat(ds2).isNull();
    }
  }
}
