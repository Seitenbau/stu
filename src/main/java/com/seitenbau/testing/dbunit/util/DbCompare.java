package com.seitenbau.testing.dbunit.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;

import com.seitenbau.testing.dbunit.modifier.IDataSetOverwriteCompare;
import com.seitenbau.testing.util.date.Datum;

public class DbCompare
{

  /**
   * Prueft DB Feld auf ungefaehr jetzt (+-15s)
   */
  public static Date warp(Datum datum2wrap)
  {
    return new ReplaceDate(datum2wrap.asDate(), new DateCompareImpl());
  }

  /**
   * Prueft DB Feld auf ungefaehr jetzt (+-15s)
   */
  public static Date jetzt()
  {
    return new ReplaceDate(new Date(), new DateCompareImpl());
  }

  /**
   * Prueft DB Feld auf ungefaehr jetzt (+-15s). Der Milisekunden
   * Anteil ist 0.
   */
  public static Date jetztOhneMS()
  {
    Date jetzt = new Date((System.currentTimeMillis() / 1000) * 1000);
    return new ReplaceDate(jetzt, new DateCompareImpl());
  }

  /**
   * Prueft DB Feld auf ungefaehr jetzt (+-15s). Der Zeitaneilt ist
   * "00:00:00.000".
   */
  public static Date jetztNurDatum()
  {
    Date jetzt = new Date((System.currentTimeMillis() / 1000) * 1000);

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(jetzt);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);

    jetzt.setTime(calendar.getTimeInMillis());
    return new ReplaceDate(jetzt, new DateCompareImpl());
  }

  /**
   * Prueft DB Feld auf datum (+-15s) Format : "dd.MM.yyyy HH:mm.ss"
   */
  public static Date datum(String datum) throws ParseException
  {
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm.ss");
    Date expect = sdf.parse(datum);
    return new ReplaceDate(expect, new DateCompareImpl());
  }

  public static Date datum(long offsetInSeconds, int minSecOffSet, int maxSecOffSet)
  {
    Date expect = new Date(new Date().getTime() + (offsetInSeconds * 1000));
    return new ReplaceDate(expect, new DateCompareImpl(minSecOffSet * 1000, maxSecOffSet * 1000));
  }

  /**
   * Prueft DB Feld auf datum (+-15s)
   */
  public static Date datum(Date now, long offsetInSeconds)
  {
    Date expect = new Date(now.getTime() + (offsetInSeconds * 1000));
    return new ReplaceDate(expect, new DateCompareImpl());
  }

  /**
   * Prueft DB Feld ungefaehr jetzt plus Nsekunden (+-30s)
   */
  public static Date datum(long offsetInSeconds)
  {
    Date expect = new Date(new Date().getTime() + (offsetInSeconds * 1000));
    return new ReplaceDate(expect, new DateCompareImpl());
  }

  public static class DateCompareImpl
  {
    private static final int ONE_SECOND = 1000;

    private Integer fPlusMilliseconds = null;

    private Integer fMinusMilliseconds = null;

    public DateCompareImpl(int plusMinusMilliseconds)
    {
      init(plusMinusMilliseconds, plusMinusMilliseconds);
    }

    public DateCompareImpl()
    {
      init(15 * ONE_SECOND, 15 * ONE_SECOND);
    }

    public DateCompareImpl(int minusMilliseconds, int plusMilliseconds)
    {
      init(minusMilliseconds, plusMilliseconds);
    }

    public void init(int minusMilliseconds, int plusMilliseconds)
    {
      fPlusMilliseconds = plusMilliseconds;
      fMinusMilliseconds = minusMilliseconds;
    }

    public boolean compareValues(Date expectedDate, Date actualValue)
    {
      long ticksExpect = expectedDate.getTime();
      long ticksActual = actualValue.getTime();

      if (fMinusMilliseconds != null)
      {
        long diff = ticksExpect - ticksActual;

        if (diff > fMinusMilliseconds)
        {
          throw new AssertionError("Zeitstempel - Range Vergleich : erwartete " + actualValue + " nicht mehr als "
              + fMinusMilliseconds + " ms vor " + new Date(ticksExpect) + ", aber war " + diff + " ms früher");
        }
      }
      if (fPlusMilliseconds != null)
      {
        long diff = ticksActual - ticksExpect;

        if (diff > fPlusMilliseconds)
        {
          throw new AssertionError("Zeitstempel - Range Vergleich : erwartete " + actualValue + " nicht mehr als "
              + fPlusMilliseconds + " ms nach " + new Date(ticksExpect) + ", aber war " + diff + " ms später");
        }
      }
      return true;
    }

    public Date createCompareTo(Date referenceDate)
    {
      return new ReplaceDate(referenceDate, this);
    }
  }

  public static class ReplaceDate extends Date implements IDataSetOverwriteCompare
  {
    private static final long serialVersionUID = 1L;

    private Date _datum;

    private DateCompareImpl _compare;

    public ReplaceDate(Date referenceDate, DateCompareImpl compare)
    {
      super(referenceDate.getTime());
      _datum = referenceDate;
      _compare = compare;
    }

    protected Date getDatum()
    {
      return _datum;
    }

    public boolean equals(Object actual)
    {
      if (actual instanceof Date)
      {
        return _compare.compareValues(getDatum(), (Date) actual);
      }
      return super.equals(actual);
    }

    public int compareTo(Date actual)
    {
      if (actual == null)
      {
        if (getDatum() == null)
        {
          return 0;
        }
      }
      else
      {
        if (_compare.compareValues(getDatum(), actual))
        {
          return 0;
        }
      }
      return -1;
    }

    public int compareDataSetElementTo(Object objectToCompareTo)
    {
      if (objectToCompareTo instanceof Date)
      {
        if (_compare.compareValues(getDatum(), (Date) objectToCompareTo))
        {
          return 0;
        }
        else
        {
          Assert.fail("Date values not equal");
          return 0;
        }
      }
      else
      {
        // TODO wenn kein Date ? also bsp null -> Fehler Werfen
        // diff von hier zum obrigen instanceof
        Assert.fail("Column was not of Type Date");
        return 0;
      }
    }

  }

}