package com.seitenbau.stu.testdata;

public class FieldReadException extends RuntimeException
{

  private static final long serialVersionUID = -2734102227487458556L;

  public FieldReadException(Exception exp)
  {
    super(exp);
  }
  
  public FieldReadException(String msg, Exception exp)
  {
    super(msg, exp);
  }
  
}
