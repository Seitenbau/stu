package com.seitenbau.stu.testdata;

public class CanNotInstanceModelClassException extends RuntimeException
{

  private static final long serialVersionUID = 1L;

  public CanNotInstanceModelClassException(Exception exp)
  {
    super(exp);
  }

}
