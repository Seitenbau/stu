package com.seitenbau.testing.util.date;

import java.util.Calendar;
import java.util.Date;

import com.seitenbau.testing.util.DateUtil;

public class Datum
{
  Calendar _datum;

  public Datum(Calendar current)
  {
    _datum = current;
  }

  /**
   * Get as Date object
   * @return
   */
  public Date asDate()
  {
    return DateUtil.createCopy(_datum).getTime();
  }

  /**
   * Get as formatted String
   * @param format
   * @return
   */
  public String asString(String format)
  {
    return DateUtil.formatDate(_datum, format);
  }

  /**
   * get as Calendar object
   * @return
   */
  public Calendar asCalendar()
  {
    return DateUtil.createCopy(_datum);
  }

  @Override
  public String toString()
  {
    return DateUtil.formatDate(asCalendar(), "yyyy-MM-dd HH:mm:ss.SSS");
  }

  /**
   * Remove the Miliseconds part, e.g. set miliseconds to 0.
   * @return
   */
  public Datum removeMS()
  {
    Calendar datum = DateUtil.createCopy(_datum);
    datum.set(Calendar.MILLISECOND, 0);
    return new Datum(datum);
  }

  /**
   * Remove the Hour, Minute, Second and Miliseconds part, e.g. set
   * them to 0.
   * @return
   */
  public Datum removeTime()
  {
    Calendar datum = DateUtil.createCopy(_datum);
    datum.set(Calendar.HOUR, 0);
    datum.set(Calendar.MINUTE, 0);
    datum.set(Calendar.SECOND, 0);
    datum.set(Calendar.MILLISECOND, 0);
    return new Datum(datum);
  }

  /**
   * Remove the Year, Month and Date part, e.g. set them to 0.
   * @return
   */
  public Datum removeDate()
  {
    Calendar datum = DateUtil.createCopy(_datum);
    datum.set(Calendar.YEAR, 1970);
    datum.set(Calendar.MONTH, 0);
    datum.set(Calendar.DATE, 1);
    return new Datum(datum);
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
   * @return a Zeitspanne
   */
  public Zeitspanne calcDifferencTo(Datum datum)
  {
    long now = _datum.getTimeInMillis();
    long then = datum._datum.getTimeInMillis();
    long diff = now - then;
    return new Zeitspanne(diff);
  }

  /**
   * Calculate the timespan between this an another
   * 
   * @param datum the other date
   * @return a Zeitspanne
   */
  public Zeitspanne calcDifferencTo(String datum)
  {
    return calcDifferencTo(DateUtil.asDatum(datum));
  }

  protected Datum add(int field, int amount)
  {
    Calendar datum = DateUtil.createCopy(_datum);
    datum.add(field, amount);
    return new Datum(datum);
  }

  /**
   * Month is 1 based! (not zero based as in the underlying Calendar)
   */
  protected Datum set(int field, int amount)
  {
    Calendar datum = DateUtil.createCopy(_datum);
    if (field == Calendar.MONTH)
    {
      datum.set(field, amount - 1);
    }
    else
    {
      datum.set(field, amount);
    }
    return new Datum(datum);
  }

  public class Incrementer
  {

    Datum _builder;

    int _amount;

    public Incrementer(int i, Datum dateBuilder)
    {
      _builder = dateBuilder;
      _amount = i;
    }

    /**
     * Increment on field Second
     */
    public Datum seconds()
    {
      return _builder.add(Calendar.SECOND, _amount);
    }

    /**
     * Increment on field Second
     */
    public Datum second()
    {
      return _builder.add(Calendar.SECOND, _amount);
    }

    /**
     * Increment on field Minute
     */
    public Datum minutes()
    {
      return _builder.add(Calendar.MINUTE, _amount);
    }

    /**
     * Increment on field Minute
     */
    public Datum minute()
    {
      return _builder.add(Calendar.MINUTE, _amount);
    }

    /**
     * Increment on field Hour
     */
    public Datum hours()
    {
      return _builder.add(Calendar.HOUR_OF_DAY, _amount);
    }

    /**
     * Increment on field Hour
     */
    public Datum hour()
    {
      return _builder.add(Calendar.HOUR_OF_DAY, _amount);
    }

    /**
     * Increment on field Day
     */
    public Datum days()
    {
      return _builder.add(Calendar.DATE, _amount);
    }

    /**
     * Increment on field Day
     */
    public Datum day()
    {
      return _builder.add(Calendar.DATE, _amount);
    }

    /**
     * Increment on field Month
     */
    public Datum months()
    {
      return _builder.add(Calendar.MONTH, _amount);
    }

    /**
     * Increment on field Month
     */
    public Datum month()
    {
      return _builder.add(Calendar.MONTH, _amount);
    }

    /**
     * Increment on field Year
     */
    public Datum years()
    {
      return _builder.add(Calendar.YEAR, _amount);
    }

    /**
     * Increment on field Year
     */
    public Datum year()
    {
      return _builder.add(Calendar.YEAR, _amount);
    }

    /**
     * Increment on field Milisecond
     */
    public Datum miliseconds()
    {
      return _builder.add(Calendar.MILLISECOND, _amount);
    }

    /**
     * Increment on field Milisecond
     */
    public Datum milisecond()
    {
      return _builder.add(Calendar.MILLISECOND, _amount);
    }

  }

  public class Setter
  {

    Datum _builder;

    int _increment;

    public Setter(int i, Datum dateBuilder)
    {
      _builder = dateBuilder;
      _increment = i;
    }

    /**
     * set field Second
     */
    public Datum seconds()
    {
      return _builder.set(Calendar.SECOND, _increment);
    }

    /**
     * set field Second
     */
    public Datum second()
    {
      return _builder.set(Calendar.SECOND, _increment);
    }

    /**
     * set field Minute
     */
    public Datum minutes()
    {
      return _builder.set(Calendar.MINUTE, _increment);
    }

    /**
     * set field Minute
     */
    public Datum minute()
    {
      return _builder.set(Calendar.MINUTE, _increment);
    }

    /**
     * set field Hour
     */
    public Datum hours()
    {
      return _builder.set(Calendar.HOUR_OF_DAY, _increment);
    }

    /**
     * set field Hour
     */
    public Datum hour()
    {
      return _builder.set(Calendar.HOUR_OF_DAY, _increment);
    }

    /**
     * set field Day
     */
    public Datum days()
    {
      return _builder.set(Calendar.DATE, _increment);
    }

    /**
     * set field Day
     */
    public Datum day()
    {
      return _builder.set(Calendar.DATE, _increment);
    }

    /**
     * set field Month ( 1=January)
     */
    public Datum months()
    {
      return _builder.set(Calendar.MONTH, _increment);
    }

    /**
     * set field Month ( 1=January)
     */
    public Datum month()
    {
      return _builder.set(Calendar.MONTH, _increment);
    }

    /**
     * set field Year
     */
    public Datum years()
    {
      return _builder.set(Calendar.YEAR, _increment);
    }

    /**
     * set field Year
     */
    public Datum year()
    {
      return _builder.set(Calendar.YEAR, _increment);
    }

    /**
     * set field Milisecond
     */
    public Datum miliseconds()
    {
      return _builder.set(Calendar.MILLISECOND, _increment);
    }

    /**
     * set field Milisecond
     */
    public Datum milisecond()
    {
      return _builder.set(Calendar.MILLISECOND, _increment);
    }
  }

}
