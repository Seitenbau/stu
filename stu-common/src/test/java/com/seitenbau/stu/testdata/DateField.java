package com.seitenbau.stu.testdata;

import com.seitenbau.stu.testdata.Date.Day;
import com.seitenbau.stu.testdata.Date.Month;
import com.seitenbau.stu.testdata.Date.Year;

@Date
public class DateField
{

  @Day
  public String day;
  
  @Month
  public String month;
  
  @Year
  public String year;
  
}
