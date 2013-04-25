package com.seitenbau.testing.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.seitenbau.testing.util.date.Datum;

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
  public static Datum asDatum(String dateString)
  {
    return new Datum(asCalendar(dateString));
  }


  /**
   * Tries to parse the given date-string with the given format. Does
   * a not Linient parsing, and does return null when not able to
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
   * @param datum The Date as Datum
   * @param format The Format of the given Date e.g.
   *        "dd.MM.yyyy HH:mm:ss.SSS" or "dd.MM.yyyy' 23:59'"
   * @return The formatted String
   */
  public static String formatDate(Datum datum, String format)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setLenient(false);
    String heute = dateFormat.format(datum.asDate());
    return heute;
  }

  /**
   * Format the given Date as String.
   * @param datum The Date
   * @param format The Format of the given Date e.g.
   *        "dd.MM.yyyy HH:mm:ss.SSS" or "dd.MM.yyyy' 23:59'"
   * @return The formatted String
   */
  public static String formatDate(Date datum, String format)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setLenient(false);
    String heute = dateFormat.format(datum);
    return heute;
  }

  /**
   * Format the given Date as String.
   * @param datum The Date, Not {@code null}
   * @param format The Format of the given Date e.g.
   *        "dd.MM.yyyy HH:mm:ss.SSS" or "dd.MM.yyyy' 23:59'"
   * @return The formatted String
   */
  public static String formatDate(Calendar datum, String format)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setLenient(false);
    String heute = dateFormat.format(datum.getTime());
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
  public static Datum datum()
  {
    return new Datum(Calendar.getInstance());
  }

  /**
   * Create a Date Builder from the given DateString
   * @return the Datum
   */
  public static Datum datum(String datum)
  {
    return new Datum(createCopy(asDate(datum)));
  }

  /**
   * Create a Date Builder with the given Date.
   * @param datum the Date
   * @return the Datum
   */
  public static Datum datum(Calendar datum)
  {
    return new Datum(createCopy(datum));
  }

  /**
   * Create a Date Builder with the given Date.
   * @param datum the Date
   * @return the Datum
   */
  public static Datum datum(Date datum)
  {
    return new Datum(createCopy(datum));
  }

  /**
   * Create a Date Builder with the given Date.
   * @param datum the Date
   * @return the Datum
   */
  public static Datum datum(Datum datum)
  {
    return new Datum(createCopy(datum.asDate()));
  }

}
