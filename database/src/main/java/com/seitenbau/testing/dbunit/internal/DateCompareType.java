package com.seitenbau.testing.dbunit.internal;

public class DateCompareType
{

  private Object date;

  public DateCompareType(Object date)
  {
    this.date = date;
  }

  public Object getDate()
  {
    return date;
  }

  public void setDate(Object date)
  {
    this.date = date;
  }

  @Override
  public String toString()
  {
    return "Date is " + date.toString();
  }

}
