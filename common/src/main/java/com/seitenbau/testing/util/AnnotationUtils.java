package com.seitenbau.testing.util;

import java.lang.annotation.Annotation;

public class AnnotationUtils
{

  /**
   * Searches an annotation in the complete hierarchy
   * @param startClass
   * @param annotation
   * @return
   */
  public static <T extends Annotation> T findInTree(Class startClass, Class<T> annotation)
  {
    T anno = (T) startClass.getAnnotation(annotation);
    if (anno != null)
    {
      return anno;
    }
    if (startClass.getSuperclass() != Object.class)
    {
      return findInTree(startClass.getSuperclass(), annotation);
    }
    return null;
  }

}
