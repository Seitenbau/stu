package com.seitenbau.testing.dbunit;

import java.util.Calendar;
import java.util.Date;


public class DateUtil
{
  public static Date getDate(int year, int month, int dom)
  {
    return getDate(year, month, dom, 0, 0, 0);
  }

  public static Date getDate(int year, int month, int dom, int hour)
  {
    return getDate(year, month, dom, hour, 0, 0);
  }

  public static Date getDate(int year, int month, int dom, int hour, int minute)
  {
    return getDate(year, month, dom, hour, minute, 0);
  }
  
  public static Date getDate(int year, int month, int dom, int hour, int minute, int second) 
  {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.DAY_OF_MONTH, dom);
    cal.set(Calendar.HOUR, hour);
    cal.set(Calendar.MINUTE, minute);
    cal.set(Calendar.SECOND, second);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }
}
