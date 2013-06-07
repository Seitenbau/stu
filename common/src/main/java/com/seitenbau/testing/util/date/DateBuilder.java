package com.seitenbau.testing.util.date;

import java.util.Calendar;
import java.util.Date;

import com.seitenbau.testing.util.DateUtil;

public class DateBuilder
{
  Calendar calendar;

  public DateBuilder(Calendar current)
  {
    calendar = current;
  }

  /**
   * Get as Date object
   * @return
   */
  public Date asDate()
  {
    return DateUtil.createCopy(calendar).getTime();
  }

  /**
   * Get as formatted String
   * @param format
   * @return
   */
  public String asString(String format)
  {
    return DateUtil.formatDate(calendar, format);
  }

  /**
   * get as Calendar object
   * @return
   */
  public Calendar asCalendar()
  {
    return DateUtil.createCopy(calendar);
  }
  
  @Override
  public String toString()
  {
    return DateUtil.formatDate(asCalendar(),"yyyy-MM-dd HH:mm:ss.SSS");
  }
  
  /**
   * Remove the Miliseconds part, e.g. set miliseconds to 0.
   * @return
   */
  public DateBuilder removeMS()
  {
    Calendar datum = DateUtil.createCopy(calendar);
    datum.set(Calendar.MILLISECOND, 0);
    return new DateBuilder(datum);
  }

  /**
   * Remove the Hour, Minute, Second and Miliseconds part, e.g. set
   * them to 0.
   * @return
   */
  public DateBuilder removeTime()
  {
    Calendar datum = DateUtil.createCopy(calendar);
    datum.set(Calendar.HOUR, 0);
    datum.set(Calendar.MINUTE, 0);
    datum.set(Calendar.SECOND, 0);
    datum.set(Calendar.MILLISECOND, 0);
    return new DateBuilder(datum);
  }

  /**
   * Remove the Year, Month and Date part, e.g. set them to 0.
   * @return
   */
  public DateBuilder removeDate()
  {
    Calendar datum = DateUtil.createCopy(calendar);
    datum.set(Calendar.YEAR, 1970);
    datum.set(Calendar.MONTH, 0);
    datum.set(Calendar.DATE, 1);
    return new DateBuilder(datum);
  }

  /**
   * add the given increment to the datebuilder. ( Specify the field
   * via the returned builder)
   * @return
   */
  public Incrementer add(int increment)
  {
    return new Incrementer(increment, this);
  }

  /**
   * set the given value to the datebuilder. ( Specify the field via
   * the returned builder)
   * @return
   */
  public Setter set(int value)
  {
    return new Setter(value, this);
  }
  
  /**
   * Calculate the timespan between this an another  
   * 
   * @param datum the other date
   * @return a Timespan
   */
  public Timespan calcDifferencTo(DateBuilder datum) 
  {
    long now = calendar.getTimeInMillis();
    long then = datum.calendar.getTimeInMillis();
    long diff= now - then;
    return new Timespan(diff);
  }
  
  /**
   * Calculate the timespan between this an another  
   * 
   * @param datum the other date
   * @return a Timespan
   */
  public Timespan calcDifferencTo(String datum) 
  {
    return calcDifferencTo(DateUtil.asDatebuilder(datum));
  }

  protected DateBuilder add(int field, int amount)
  {
    Calendar datum = DateUtil.createCopy(calendar);
    datum.add(field, amount);
    return new DateBuilder(datum);
  }

  /**
   * Month is 1 based! (not zero based as in the underlying
   * Calendar)
   */
  protected DateBuilder set(int field, int amount)
  {
    Calendar datum = DateUtil.createCopy(calendar);
    if (field == Calendar.MONTH)
    {
      datum.set(field, amount - 1);
    }
    else
    {
      datum.set(field, amount);
    }
    return new DateBuilder(datum);
  }
  
  public class Incrementer
  {

    DateBuilder _builder;

    int _amount;

    public Incrementer(int i, DateBuilder dateBuilder)
    {
      _builder = dateBuilder;
      _amount = i;
    }

    /**
     * Increment on field Second
     */
    public DateBuilder seconds()
    {
      return _builder.add(Calendar.SECOND, _amount);
    }

    /**
     * Increment on field Second
     */
    public DateBuilder second()
    {
      return _builder.add(Calendar.SECOND, _amount);
    }

    /**
     * Increment on field Minute
     */
    public DateBuilder minutes()
    {
      return _builder.add(Calendar.MINUTE, _amount);
    }

    /**
     * Increment on field Minute
     */
    public DateBuilder minute()
    {
      return _builder.add(Calendar.MINUTE, _amount);
    }

    /**
     * Increment on field Hour
     */
    public DateBuilder hours()
    {
      return _builder.add(Calendar.HOUR_OF_DAY, _amount);
    }

    /**
     * Increment on field Hour
     */
    public DateBuilder hour()
    {
      return _builder.add(Calendar.HOUR_OF_DAY, _amount);
    }

    /**
     * Increment on field Day
     */
    public DateBuilder days()
    {
      return _builder.add(Calendar.DATE, _amount);
    }

    /**
     * Increment on field Day
     */
    public DateBuilder day()
    {
      return _builder.add(Calendar.DATE, _amount);
    }

    /**
     * Increment on field Month
     */
    public DateBuilder months()
    {
      return _builder.add(Calendar.MONTH, _amount);
    }

    /**
     * Increment on field Month
     */
    public DateBuilder month()
    {
      return _builder.add(Calendar.MONTH, _amount);
    }

    /**
     * Increment on field Year
     */
    public DateBuilder years()
    {
      return _builder.add(Calendar.YEAR, _amount);
    }

    /**
     * Increment on field Year
     */
    public DateBuilder year()
    {
      return _builder.add(Calendar.YEAR, _amount);
    }

    /**
     * Increment on field Millisecond
     */
    public DateBuilder milliseconds()
    {
      return _builder.add(Calendar.MILLISECOND, _amount);
    }

    /**
     * Increment on field Millisecond
     */
    public DateBuilder millisecond()
    {
      return _builder.add(Calendar.MILLISECOND, _amount);
    }

  }

  public class Setter
  {

    DateBuilder _builder;

    int _increment;

    public Setter(int i, DateBuilder dateBuilder)
    {
      _builder = dateBuilder;
      _increment = i;
    }

    /**
     * set field Second
     */
    public DateBuilder seconds()
    {
      return _builder.set(Calendar.SECOND, _increment);
    }

    /**
     * set field Second
     */
    public DateBuilder second()
    {
      return _builder.set(Calendar.SECOND, _increment);
    }

    /**
     * set field Minute
     */
    public DateBuilder minutes()
    {
      return _builder.set(Calendar.MINUTE, _increment);
    }

    /**
     * set field Minute
     */
    public DateBuilder minute()
    {
      return _builder.set(Calendar.MINUTE, _increment);
    }

    /**
     * set field Hour
     */
    public DateBuilder hours()
    {
      return _builder.set(Calendar.HOUR_OF_DAY, _increment);
    }

    /**
     * set field Hour
     */
    public DateBuilder hour()
    {
      return _builder.set(Calendar.HOUR_OF_DAY, _increment);
    }

    /**
     * set field Day
     */
    public DateBuilder days()
    {
      return _builder.set(Calendar.DATE, _increment);
    }

    /**
     * set field Day
     */
    public DateBuilder day()
    {
      return _builder.set(Calendar.DATE, _increment);
    }

    /**
     * set field Month ( 1=January)
     */
    public DateBuilder months()
    {
      return _builder.set(Calendar.MONTH, _increment);
    }

    /**
     * set field Month ( 1=January)
     */
    public DateBuilder month()
    {
      return _builder.set(Calendar.MONTH, _increment);
    }

    /**
     * set field Year
     */
    public DateBuilder years()
    {
      return _builder.set(Calendar.YEAR, _increment);
    }

    /**
     * set field Year
     */
    public DateBuilder year()
    {
      return _builder.set(Calendar.YEAR, _increment);
    }

    /**
     * set field Millisecond
     */
    public DateBuilder milliseconds()
    {
      return _builder.set(Calendar.MILLISECOND, _increment);
    }

    /**
     * set field Millisecond
     */
    public DateBuilder millisecond()
    {
      return _builder.set(Calendar.MILLISECOND, _increment);
    }
  }

}
