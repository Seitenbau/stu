package com.seitenbau.stu.rules.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.runners.model.FrameworkMethod;

import com.seitenbau.stu.rules.ITestMethodDescriptor;
import com.seitenbau.stu.util.ReflectionUtils;

public class JUnitTestMethodDescriptor implements ITestMethodDescriptor
{

  protected FrameworkMethod _frameworkMethod;

  protected Object _target;

  protected Integer _index;

  public JUnitTestMethodDescriptor(FrameworkMethod method, Object target, Integer index)
  {
    _frameworkMethod = method;
    _target = target;
    _index = index;
  }

  public FrameworkMethod getTestFrameworkMethod()
  {
    return _frameworkMethod;
  }

  public String getMethodName()
  {
    return _frameworkMethod.getName();
  }

  public <T extends Annotation> T getAnnotation(Class<T> annotationClass, boolean searchHierachy)
  {
    T value = (T) getMethod().getAnnotation((Class<T>) annotationClass);
    if (value == null && searchHierachy)
    {
      Class<?> clazz = getTestClass();
      value = find(clazz, annotationClass, getMethod());
    }
    return value;
  }

  public Integer getIndex()
  {
    return _index;
  }

  public String getClassCanoncialName()
  {
    if (_target != null)
    {
      return _target.getClass().getCanonicalName();
    }
    return getMethod().getDeclaringClass().getCanonicalName();
  }

  public String getClassName()
  {
    if (_target != null)
    {
      return _target.getClass().getName();
    }
    return getMethod().getDeclaringClass().getName();
  }

  public Object getTarget()
  {
    return _target;
  }

  @Override
  public String toString()
  {
    return getClassCanoncialName() + "::" + getMethodName();
  }

  protected Method getMethod()
  {
    return _frameworkMethod.getMethod();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  protected <T> T find(Class parent, Class<T> annotation, Method methodTemplate)
  {
    T anno = null;
    Method methodHere = ReflectionUtils.getMethod(parent, methodTemplate.getName());
    if (methodHere != null)
    {
      anno = (T) methodHere.getAnnotation((Class) annotation);
      if (anno != null)
      {
        return anno;
      }
    }
    anno = (T) parent.getAnnotation(annotation);
    if (anno != null)
    {
      return anno;
    }
    if (parent.getSuperclass() != Object.class)
    {
      return find(parent.getSuperclass(), annotation, methodTemplate);
    }
    return null;
  }

  protected Class<?> getTestClass()
  {
    if (getTarget() != null)
    {
      return getTarget().getClass();
    }
    return getMethod().getDeclaringClass();
  }

}
