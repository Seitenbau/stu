package com.seitenbau.stu.rules.impl;

import java.lang.annotation.Annotation;

import org.junit.runner.Description;
import org.springframework.core.annotation.AnnotationUtils;

import com.seitenbau.stu.rules.ITestMethodDescriptor;

public class JUnitTestMethodDescriptor2 implements ITestMethodDescriptor
{
  Description _description;

  Object _target;

  Integer _index;

  public JUnitTestMethodDescriptor2(Integer index, Object target,
      Description description)
  {
    _description = description;
    _target = target;
    _index = index;
  }

  public String getMethodName()
  {
    return _description.getMethodName();
  }

  public String getClassCanoncialName()
  {
    return _description.getClassName();
  }

  public <T extends Annotation> T getAnnotation(Class<T> annotationClass,
      boolean searchHierachy)
  {
    T anno = _description.getAnnotation(annotationClass);
    // Should be :
    // AnnotationUtils.findAnnotation()
    if (anno != null)
    {
      return anno;
    }
    if (!searchHierachy)
    {
      return null;
    }
    return AnnotationUtils.findAnnotation(_target.getClass(), annotationClass);
  }

  public Integer getIndex()
  {
    return _index;
  }

  public String getClassName()
  {
    return _description.getClassName();
  }

  public Object getTarget()
  {
    return _target;
  }

}
