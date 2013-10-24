package com.seitenbau.stu.config.impl;

import com.seitenbau.stu.config.StoredProperty;

/**
 * The exception will be thrown when a static class has a
 * non static field with a {@link StoredProperty} annotation.
 */
public class NonStaticFieldException extends RuntimeException
{

  private static final long serialVersionUID = 1L;

  NonStaticFieldException(String msg)
  {
    super(msg);
  }

}
