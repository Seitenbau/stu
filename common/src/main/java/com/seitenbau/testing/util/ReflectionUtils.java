package com.seitenbau.testing.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils
{

  /**
   * Find's all methods with the given annotation. <br/>
   * The list is ordered by hierarchy : First methods from children than
   * from the superclass(s). The list only contains methods once,
   * methods found already in a child, are not in the list when they
   * exist with the same name + parameters in a superclass.
   * @param clazz
   * @param annotationClazz
   * @param searchInHierachy
   * @return
   */
  public static List<Method> findMethodByAnnotation(Class<? extends Object> clazz, Class<?> annotationClazz,
      boolean searchInHierachy)
  {
    List<Method> methods = new ArrayList<Method>();
    findByAnnotations(methods, clazz, annotationClazz, searchInHierachy);
    return methods;
  }

  protected static void findByAnnotations(List<Method> methods, Class<? extends Object> clazz,
      Class annotationClazz, boolean searchInHierachy)
  {
    Method[] allMethods = clazz.getDeclaredMethods();
    for (Method m : allMethods)
    {
      if (m.getAnnotation(annotationClazz) != null && !doesContain(methods, m))
      {
        methods.add(m);
      }
    }
    if (searchInHierachy && clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class)
    {
      findByAnnotations(methods, clazz.getSuperclass(), annotationClazz, searchInHierachy);
    }
  }

  protected static boolean doesContain(List<Method> methods, Method method)
  {
    for (Method m : methods)
    {
      if (m.getName().equals(method.getName()))
      {
        Class<?>[] params1 = m.getParameterTypes();
        Class<?>[] params2 = method.getParameterTypes();
        if (Arrays.equals(params1, params2))
        {
          return true;
        }
      }
    }
    return false;
  }

  public static Method getMethod(Class<?> clazz, String name)
  {
    if (clazz == null)
    {
      return null;
    }
    if (name == null)
    {
      return null;
    }
    try
    {
      return clazz.getMethod(name);
    }
    catch (SecurityException e)
    {
      return null;
    }
    catch (NoSuchMethodException e)
    {
      return null;
    }
  }

  public static void setField(Object that, Field field, Object bean)
  {
    field.setAccessible(true);
    try
    {
      field.set(that, bean);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  public static void invoke(Method m, Object obj, Object... args)
  {
    try
    {
      m.setAccessible(true);
      m.invoke(obj, args);
    }
    catch (IllegalArgumentException e)
    {
      throw new RuntimeException(e);
    }
    catch (IllegalAccessException e)
    {
      throw new RuntimeException(e);
    }
    catch (InvocationTargetException e)
    {
      throw new RuntimeException(e);
    }
  }

  public static boolean canCast(Class from, Class into)
  {
    try
    {
      from.asSubclass(into);
      return true;
    }
    catch (ClassCastException e)
    {
      return false;
    }
  }
}
