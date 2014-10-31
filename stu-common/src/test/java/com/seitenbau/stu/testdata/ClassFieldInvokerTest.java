package com.seitenbau.stu.testdata;

import static org.fest.assertions.Assertions.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.Test;

public class ClassFieldInvokerTest
{

  static class ParentClass extends SuperClass
  {
    public String name;
  }
  
  static class SuperClass
  {
    public String name;
    
    public String firstname;
    
    String nonPublicField;
  }

  @Test
  public void testFieldVisitor() throws Exception
  {
    final ArrayList<Field> invokedFields = new ArrayList<Field>();
    ClassFieldInvoker.forFieldsOf(ParentClass.class).invoke(new FieldInvoker()
    {
      public void invoke(Field field)
      {
        invokedFields.add(field);
      }
    });
    assertThat(invokedFields).hasSize(2);
    assertThat(invokedFields).contains(ParentClass.class.getField("name"));
    assertThat(invokedFields).isNotIn(SuperClass.class.getField("name"));
    assertThat(invokedFields).contains(SuperClass.class.getField("firstname"));
  }
  
  @Test
  public void testFieldVisitor_NullClass() throws Exception
  {
    final ArrayList<Field> invokedFields = new ArrayList<Field>();
    ClassFieldInvoker.forFieldsOf(null).invoke(new FieldInvoker()
    {
      public void invoke(Field field)
      {
        invokedFields.add(field);
      }
    });
    assertThat(invokedFields).hasSize(0);
  }


}
