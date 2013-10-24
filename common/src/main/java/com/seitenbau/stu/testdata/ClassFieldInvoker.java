package com.seitenbau.stu.testdata;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

class ClassFieldInvoker
{

  private final Class<?> parentClass;
  
  private ClassFieldInvoker(Class<?> parentClass)
  {
    this.parentClass = parentClass;
  }

  public static ClassFieldInvoker forFieldsOf(Class<?> parentClass)
  {
    return new ClassFieldInvoker(parentClass);
  }
  
  public void invoke(FieldInvoker invoker)
  {
    final Set<String> invokedFieldNames = new HashSet<String>();
    Class<?> actualClass = parentClass;
    while (actualClass != null && !actualClass.equals(Object.class)) {
      Field[] declaredFields = actualClass.getDeclaredFields();
      for (Field field : declaredFields)
      {
        if(Modifier.isPublic(field.getModifiers()))
        {
          if(invokedFieldNames.contains(field.getName()))
          {
            // ignore this field the field is overwritten by a parent class.
          }
          else 
          {
            invokedFieldNames.add(field.getName());
            invoker.invoke(field);
          }
        }
      }
      actualClass = actualClass.getSuperclass();
    }
  }

}
