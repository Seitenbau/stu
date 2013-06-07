package com.seitenbau.testing.asserts.fest.impl;

import static com.seitenbau.testing.util.DateUtil.createCopy;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.fest.assertions.Assert;
import org.fest.assertions.Assertions;

import com.seitenbau.testing.util.DateUtil;
import com.seitenbau.testing.util.date.DateBuilder;

/**
 * Helper for fest-style Date Assertions
 */
public class DateAssert extends Assert
{

    public static final int DEFAULT_DELTA_MS = 15 * 1000;

    Date _date;

    /**
     * Create new Assertion against actual date
     * 
     * @param date The actual Date
     */
    public DateAssert(Date date)
    {
        _date = date;
    }

    public DateAssert(Calendar calendar)
    {
        if (calendar == null)
        {
            _date = null;
        }
        else
        {
            _date = calendar.getTime();
        }
    }

    /**
     * Check if the given Date is null.
     */
    public DateAssert isNull()
    {
        Assertions.assertThat(_date).isNull();
        return this;
    }

    /**
     * Check if the given Date is not null.
     */
    public DateAssert isNotNull()
    {
        Assertions.assertThat(_date).isNotNull();
        return this;
    }

    /**
     * Check if the Date of the given Calendat is equal to the date, with a delta of x (
     * {@link #DEFAULT_DELTA_MS}).
     */
    public DateAssert is(Calendar calendar, int deltaInMs)
    {
        return is(calendar.getTime(),deltaInMs);
    }
    
    /**
     * Check if the Date of the given calendar is equal to the date, with a delta of 15s (
     * {@link #DEFAULT_DELTA_MS}).
     */
    public DateAssert is(Calendar calendar)
    {
        return is(calendar.getTime());
    }
    
    /**
     * Check if the Date of the given Datebuilder is equal to the date, with a delta of 15s (
     * {@link #DEFAULT_DELTA_MS}).
     */
    public DateAssert is(DateBuilder dateBuilder)
    {
      return is(dateBuilder.asDate());
    }
    
    /**
     * Check if the Date of the given Datebuilder is equal to the date, with a delta of x (
     * {@link #DEFAULT_DELTA_MS}).
     */
    public DateAssert is(DateBuilder dateBuilder, int deltaInMs)
    {
      return is(dateBuilder.asDate(),deltaInMs);
    }
    
    /**
     * Check if the given Date is equal to the given date, with a delta of 15s (
     * {@link #DEFAULT_DELTA_MS}).
     */
    public DateAssert is(Date date)
    {
        return is(date, DEFAULT_DELTA_MS);
    }
    
  /**
   * Check if the given Date is equal to the given date, with a delta
   * of 15s. Supports Dates: {@link #formats}
   * "dd.MM.yyyy HH:mm:ss.SSS" and "yyyy-MM-dd HH:mm:ss.SSS" or only
   * date or only time. </p> When only a Time is give, the Date will
   * be 1.1.1970. When only a Date is given the time part will be
   * 0:0:0.000
   */
  public DateAssert is(String dateString)
  {
    return is(DateUtil.asDate(dateString), DEFAULT_DELTA_MS);
  }

    /**
     * Check if the given Date is equal to the given date, with a delta of n ms.
     * ( Meaning: + maxOffsetInMs or - maxOffsetInMs )
     */
    public DateAssert is(Date date, int deltaInMs)
    {
        isNotNull();
        long actual = _date.getTime();
        long expect = date.getTime();
        long diff = expect - actual;
        doAssert(diff, deltaInMs);
        return this;
    }

    /**
     * Check if the given Date is equal to Now plus minus 15s (
     * {@link #DEFAULT_DELTA_MS}).
     */
    public DateAssert isNow()
    {
        isNotNull();
        isNow(DEFAULT_DELTA_MS);
        return this;
    }

    /**
     * Check if the given Date is equal to Now plus minus the given delta.
     */
    public DateAssert isNow(int deltaInMs)
    {
        milliSecondsFromNow(0, deltaInMs);
        return this;
    }

    /**
     * Check if the given Date is equal to now() plus the given offset in ms.
     * The equals is tolerant wihin plus minus the given delta.
     */
    public DateAssert milliSecondsFromNow(long offsetInMilliseconds, int deltaInMs)
    {
        isNotNull();
        long actual = _date.getTime();
        long expect = getNow().getTime() + (offsetInMilliseconds);
        long diff = expect - actual;
        doAssert(diff, deltaInMs);
        return this;
    }

    private void doAssert(long diff, int deltaInMs)
    {
        Long absoluteDiff = Math.abs(diff);
        StringBuffer sb = new StringBuffer();
        sb.append("Date ");
        sb.append(_date);
        sb.append(" differs with ");
        if (diff > 4000)
        {
            if (diff / 1000 > 240)
            {
                sb.append(diff / 1000 / 60);
                sb.append("m ");
            }
            else
            {
                sb.append(diff / 1000);
                sb.append("s ");
            }
        }
        sb.append("[");
        sb.append(diff);
        sb.append("ms]");
        sb.append(" from compare date");
        Assertions.assertThat(absoluteDiff).overridingErrorMessage(sb.toString())
                .isLessThanOrEqualTo(deltaInMs);
    }

    Date getNow()
    {
        return new Date();
    }

    /**
     * Check if the given Date is equal to now() plus the given offset in
     * minutes. The equals is tolerant within plus/minus the given delta.
     */
    public DateAssert minutesFromNow(long offsetInMinutes, int deltaInMS)
    {
        return secondFromNow(offsetInMinutes * 60, deltaInMS);
    }

    /**
     * Check if the given Date is equal to now() plus the given offset in
     * minutes. The equals is tolerant within plus/minus the given delta.
     */
    public DateAssert secondFromNow(long offsetInSeconds, int deltaInMS)
    {
        isNotNull();
        long actual = _date.getTime();
        long expect = getNow().getTime() + (1000 * offsetInSeconds);
        long diff = expect - actual;
        doAssert(diff, deltaInMS);
        return this;
    }

    /**
     * Fluent api: Test if Date is offset X away from now. With a delta of 15s
     * {@link #DEFAULT_DELTA_MS}
     */
    public DateInAssert isIn(long offset)
    {
        return new DateInAssert(this, offset);
    }

    /**
     * Checks if given Date is exactly the same
     * 
     * @param date
     */
    public void isExactly(Date date)
    {
        long diff = _date.getTime() - date.getTime();
        if (diff != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("expected ");
            sb.append(date);
            sb.append(" but was ");
            sb.append(_date);
            sb.append(" diff=");
            sb.append(diff);
            sb.append("ms");
            fail(sb.toString());
        }
    }

    /**
     * Checks if given Date of given Calendar is exactly the same
     * 
     * @param calendar
     */
    public void isExactly(Calendar calendar)
    {
        isExactly(calendar.getTime());
    }
    
    /**
     * Checks if Date of given DateBuilder is exactly the same
     * 
     * @param dateBuilder
     */
    public void isExactly(DateBuilder dateBuilder)
    {
      isExactly(dateBuilder.asDate());
    }

    /**
     * Checks if given Date is exactly the same.
     * 
     * Uses DataString for String to Date transformation.
     * 
     * @param dateString "dd.MM.yyyy HH:mm:ss" and "yyyy-MM-dd HH:mm:ss"
     */
    public void isExactly(String dateString)
    {
      Date exp = DateUtil.asDate(dateString);
      assertThat(_date.getTime())
          .as("expected '" + _date.toString() + "' to equal '"+ exp.toString() + "'")
          .isEqualTo(exp.getTime());
    }

    /**
     * From here on only check the Date Part ( Year, month and day )
     * 
     * @return
     */
    public DateOnlyAssert onDate()
    {
        return new DateOnlyAssert(_date);
    }

    /**
     * From here on only check the Time Part ( Hour, minute, seconds and
     * milliseconds )
     * 
     * @return
     */
    public TimeOnlyAssert onTime()
    {
        return new TimeOnlyAssert(_date, true);
    }

    /**
     * From here on only check the Time Part
     * 
     * @param doCheckAlsoMilliseconds if {@code false} the milliseconds of the
     *        time part will also not be checked! * @return
     */
    public TimeOnlyAssert onTime(boolean doCheckAlsoMilliseconds)
    {
        return new TimeOnlyAssert(_date, doCheckAlsoMilliseconds);
    }

    public class DateInAssert
    {

        DateAssert _dateAssert;

        long _offset;

        public DateInAssert(DateAssert dateAssert, long offset)
        {
            _dateAssert = dateAssert;
            _offset = offset;
        }

        public DateAssert minutes()
        {
            return _dateAssert.minutesFromNow(_offset, DEFAULT_DELTA_MS);
        }

        public DateAssert seconds()
        {
            return _dateAssert.secondFromNow(_offset, DEFAULT_DELTA_MS);
        }

        public DateAssert miliSeconds()
        {
            return _dateAssert.milliSecondsFromNow(_offset, DEFAULT_DELTA_MS);
        }

    }

    public class DateOnlyAssert
    {

        Calendar _date;

        public DateOnlyAssert(Date date)
        {
            _date = createCopy(date);
        }

        public DateOnlyAssert(Calendar calendar)
        {
            _date = createCopy(calendar);
        }
        
        public DateOnlyAssert(DateBuilder dateBuilder)
        {
          _date = createCopy(dateBuilder.asCalendar());
        }
        
        public DateOnlyAssert(XMLGregorianCalendar xmlCalendar)
        {
          if(xmlCalendar == null) 
          {
            _date = null;
          } 
          else 
          {
            _date = createCopy(xmlCalendar.toGregorianCalendar());
          }
        }

        public DateOnlyAssert isExactly(String expectedDateString)
        {
            isExactly(createCopy((DateUtil.asDate(expectedDateString))));
            return this;
        }

        public DateOnlyAssert isExactly(Date expect)
        {
            isExactly(createCopy(expect));
            return this;
        }
        
        public DateOnlyAssert isExactly(DateBuilder expectedDateBuilder)
        {
          isExactly(expectedDateBuilder.asCalendar());
          return this;
        }
        
        public DateOnlyAssert isExactly(XMLGregorianCalendar expectedXmlCalendar)
        {
          isExactly(expectedXmlCalendar.toGregorianCalendar());
          return this;
        }

        public DateOnlyAssert isExactly(Calendar expectedCalendar)
        {
            Calendar calExpect = createCopy(expectedCalendar);
            int ay = _date.get(Calendar.YEAR);
            int am = _date.get(Calendar.MONTH) + 1;
            int ad = _date.get(Calendar.DAY_OF_MONTH);
            int ey = calExpect.get(Calendar.YEAR);
            int em = calExpect.get(Calendar.MONTH) + 1;
            int ed = calExpect.get(Calendar.DAY_OF_MONTH);

            assertThat(ay).as("Year").isEqualTo(ey);
            assertThat(am).as("Month").isEqualTo(em);
            assertThat(ad).as("Day").isEqualTo(ed);
            return this;
        }

    }

    public class TimeOnlyAssert
    {

        Calendar _date;

        boolean _checkMiliseconds;

        public TimeOnlyAssert(Date date, boolean checkMiliseconds)
        {
            _date = createCopy(date);
            _checkMiliseconds = checkMiliseconds;
        }

        public TimeOnlyAssert(Calendar calendar, boolean checkMiliseconds)
        {
            _date = createCopy(calendar);
            _checkMiliseconds = checkMiliseconds;
        }

        public TimeOnlyAssert isExactly(String expectDateString)
        {
            isExactly(DateUtil.asDate(expectDateString));
            return this;
        }

        public TimeOnlyAssert isExactly(Date expectedDate)
        {
            isExactly(createCopy(expectedDate));
            return this;
        }
        
        public TimeOnlyAssert isExactly(XMLGregorianCalendar expectXmlCalendar)
        {
          isExactly(expectXmlCalendar.toGregorianCalendar());
          return this;
        }
        
        public TimeOnlyAssert isExactly(DateBuilder expectedDateBuilder)
        {
          isExactly(expectedDateBuilder.asCalendar());
          return this;
        }

        public TimeOnlyAssert isExactly(Calendar expectedCalendar)
        {
            Calendar calExpect = createCopy(expectedCalendar);
            int ah = _date.get(Calendar.HOUR);
            int am = _date.get(Calendar.MINUTE);
            int as = _date.get(Calendar.SECOND);
            int ay = _date.get(Calendar.MILLISECOND);
            int eh = calExpect.get(Calendar.HOUR);
            int em = calExpect.get(Calendar.MINUTE);
            int es = calExpect.get(Calendar.SECOND);
            int ey = calExpect.get(Calendar.MILLISECOND);

            assertThat(ah).as("Hour").isEqualTo(eh);
            assertThat(am).as("Minute").isEqualTo(em);
            assertThat(as).as("Second").isEqualTo(es);
            if (_checkMiliseconds)
            {
                assertThat(ay).as("Milisecond").isEqualTo(ey);
            }
            return this;
        }

    }

    /**
     * Sets the description of the actual value, to be used in as message of any AssertionError thrown when an assertion fails.
     * @param text - the description
     */
    public DateAssert as(String text)
    {
        description(text);
        return this;
    }

}
