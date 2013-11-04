package com.seitenbau.stu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.seitenbau.stu.util.date.DateBuilder;

/**
 * Helper Class for working with Date or Calendar Objects
 */
abstract public class DateUtil
{
  
  static final String[] formats = {
      "dd.MM.yyyy HH:mm:ss.SSS",
      "dd.MM.yyyy HH:mm:ss",
      "dd.MM.yyyy HH:mm",
      "dd.MM.yyyy",
      "yyyy-MM-dd HH:mm:ss.SSS",
      "yyyy-MM-dd HH:mm:ss",
      "yyyy-MM-dd HH:mm",
      "yyyy-MM-dd",
      "HH:mm:ss.SSS",
      "HH:mm:ss",
      "HH:mm",
      // Some common formats
      "yyyy-MM-dd'T'HH:mm:ss.SSS",
  };

  /**
   * Supports Dates: {@link #formats} "dd.MM.yyyy HH:mm:ss.SSS" and
   * "yyyy-MM-dd HH:mm:ss.SSS" or only date or only time. </p> When
   * only a Time is give, the Date will be 1.1.1970. When only a Date
   * is given the time part will be 0:0:0.000
   */
  public static Date asDate(String dateString)
  {
    if (dateString == null)
    {
      return null;
    }
    for (String format : formats)
    {
      Date result = tryParseDate(format, dateString);
      if (result != null)
      {
        return result;
      }
    }
    throw new RuntimeException("Date not parseable : " + dateString);
  }

  /**
   * Supports Dates: {@link #formats} "dd.MM.yyyy HH:mm:ss.SSS" and
   * "yyyy-MM-dd HH:mm:ss.SSS" or only date or only time.
   * 
   * </p> When only a Time is given, the Date will be 1.1.1970. When
   * only a Date is given the time part will be 0:0:0.000
   */
  public static Calendar asCalendar(String dateString)
  {
    if (dateString == null)
    {
      return null;
    }
    for (String format : formats)
    {
      Date result = tryParseDate(format, dateString);
      if (result != null)
      {
        return createCopy(result);
      }
    }
    throw new RuntimeException("Date not parseable : " + dateString);
  }
  
  /**
   * Supports Dates: {@link #formats} "dd.MM.yyyy HH:mm:ss.SSS" and
   * "yyyy-MM-dd HH:mm:ss.SSS" or only date or only time.
   * 
   * </p> When only a Time is given, the Date will be 1.1.1970! When
   * only a Date is given the time part will be 0:0:0.000
   */
  public static DateBuilder asDatebuilder(String dateString)
  {
    return new DateBuilder(asCalendar(dateString));
  }


  /**
   * Tries to parse the given date-string with the given format. Does
   * a not lenient parsing, and does return null when not able to
   * parse.
   * @param format The format of the datestring.
   * @param dateString The Date coded as string.
   * @return The Date or null.
   */
  public static Date tryParseDate(String format, String dateString)
  {

    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try
    {
      sdf.setLenient(false);
      return sdf.parse(dateString);
    }
    catch (ParseException e)
    {
      return null;
    }
  }

  /**
   * Create a copy of the given date object as Calendar
   * @param date The Date
   * @return The Date as Calendar or {@code null} when date was
   *         {@code null}
   */
  public static Calendar createCopy(Date date)
  {
    if (date == null)
    {
      return null;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal;
  }

  /**
   * Create a copy of the given Calendar
   * @param date The Calendar
   * @return A Copy or {@code null} when date was {@code null}
   */
  public static Calendar createCopy(Calendar date)
  {
    if (date == null)
    {
      return null;
    }

    Calendar cal = (Calendar) date.clone();
    return cal;
  }

  /**
   * Format the given Date as String.
   * @param dateBuilder The Date as DateBuilder
   * @param format The Format of the given Date e.g.
   *        "dd.MM.yyyy HH:mm:ss.SSS" or "dd.MM.yyyy' 23:59'"
   * @return The formatted String
   */
  public static String formatDate(DateBuilder dateBuilder, String format)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setLenient(false);
    String heute = dateFormat.format(dateBuilder.asDate());
    return heute;
  }

  /**
   * Format the given Date as String.
   * @param dateBuilder The DateBuilder
   * @param format The Format of the given Date e.g.
   *        "dd.MM.yyyy HH:mm:ss.SSS" or "dd.MM.yyyy' 23:59'"
   * @return The formatted String
   */
  public static String formatDate(Date dateBuilder, String format)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setLenient(false);
    String heute = dateFormat.format(dateBuilder);
    return heute;
  }

  /**
   * Format the given Date as String.
   * @param calendar The Calendar, Not {@code null}
   * @param format The Format of the given Date e.g.
   *        "dd.MM.yyyy HH:mm:ss.SSS" or "dd.MM.yyyy' 23:59'"
   * @return The formatted String
   */
  public static String formatDate(Calendar calendar, String format)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setLenient(false);
    String heute = dateFormat.format(calendar.getTime());
    return heute;
  }

  /**
   * Format the current Date as String.
   * @param format The Format of the given Date e.g.
   *        "dd.MM.yyyy HH:mm:ss.SSS" or "dd.MM.yyyy' 23:59'"
   * @param datum The Date, Not {@code null}
   * @return The formatted String
   */
  public static String formatDate(String format)
  {
    return formatDate(new Date(), format);
  }

  /**
   * Create a Date Builder with the current Date.
   * @return the Datum
   */
  public static DateBuilder datum()
  {
    return new DateBuilder(Calendar.getInstance());
  }

  /**
   * Create a Date Builder from the given DateString
   * @return the Datum
   */
  public static DateBuilder datum(String datum)
  {
    return new DateBuilder(createCopy(asDate(datum)));
  }

  /**
   * Create a Date Builder with the given Date.
   * @param datum the Date
   * @return the Datum
   */
  public static DateBuilder datum(Calendar datum)
  {
    return new DateBuilder(createCopy(datum));
  }

  /**
   * Create a Date Builder with the given Date.
   * @param datum the Date
   * @return the Datum
   */
  public static DateBuilder datum(Date datum)
  {
    return new DateBuilder(createCopy(datum));
  }

  /**
   * Create a Date Builder with the given Date.
   * @param datum the Date
   * @return the Datum
   */
  public static DateBuilder datum(DateBuilder datum)
  {
    return new DateBuilder(createCopy(datum.asDate()));
  }

}
