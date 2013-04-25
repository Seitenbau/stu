package com.seitenbau.testing.asserts.fest.impl;

import static org.fest.assertions.Assertions.*;
import static com.seitenbau.testing.util.DateUtil.*;

import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.fest.assertions.Assert;
import org.fest.assertions.Assertions;

import com.seitenbau.testing.util.DateUtil;
import com.seitenbau.testing.util.date.Datum;

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
     * @param datum The actual Date
     */
    public DateAssert(Date datum)
    {
        _date = datum;
    }

    public DateAssert(Calendar datum)
    {
        if (datum == null)
        {
            _date = null;
        }
        else
        {
            _date = datum.getTime();
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
     * Check if the given Date is equal to the date, with a delta of x (
     * {@link #DEFAULT_DELTA_MS}).
     */
    public DateAssert is(Calendar datum, int deltaInMs)
    {
        return is(datum.getTime(),deltaInMs);
    }
    
    /**
     * Check if the given Date is equal to the date, with a delta of 15s (
     * {@link #DEFAULT_DELTA_MS}).
     */
    public DateAssert is(Calendar datum)
    {
        return is(datum.getTime());
    }
    
    /**
     * Check if the given Datum is equal to the date, with a delta of 15s (
     * {@link #DEFAULT_DELTA_MS}).
     */
    public DateAssert is(Datum datum)
    {
      return is(datum.asDate());
    }
    
    /**
     * Check if the given Datum is equal to the date, with a delta of x (
     * {@link #DEFAULT_DELTA_MS}).
     */
    public DateAssert is(Datum datum, int deltaInMs)
    {
      return is(datum.asDate(),deltaInMs);
    }
    
    /**
     * Check if the given Date is equal to the given date, with a delta of 15s (
     * {@link #DEFAULT_DELTA_MS}).
     */
    public DateAssert is(Date datum)
    {
        return is(datum, DEFAULT_DELTA_MS);
    }
    
    /**
     * Check if the given Date is equal to the given date, with a delta of 15s
     */
    public DateAssert is(String date)
    {
      return is(DateUtil.asDate(date), DEFAULT_DELTA_MS);
    }

    /**
     * Check if the given Date is equal to the given date, with a delta of n ms.
     * ( Meaning: + maxOffsetInMs or - maxOffsetInMs )
     */
    public DateAssert is(Date datum, int deltaInMs)
    {
        isNotNull();
        long actual = _date.getTime();
        long expect = datum.getTime();
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
        miliSecondsFromNow(0, deltaInMs);
        return this;
    }

    /**
     * Check if the given Date is equal to now() plus the given offset in ms.
     * The equals is tolerant wihin plus minus the given delta.
     */
    public DateAssert miliSecondsFromNow(long offsetInMiliseconds, int deltaInMs)
    {
        isNotNull();
        long actual = _date.getTime();
        long expect = getNow().getTime() + (offsetInMiliseconds);
        long diff = expect - actual;
        doAssert(diff, deltaInMs);
        return this;
    }

    private void doAssert(long diff, int deltaInMs)
    {
        Long diff2 = Math.abs(diff);
        StringBuffer sb = new StringBuffer();
        sb.append("Datum ");
        sb.append(_date);
        sb.append(" liegt um ");
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
        sb.append(" daneben");
        Assertions.assertThat(diff2).overridingErrorMessage(sb.toString())
                .isLessThanOrEqualTo(deltaInMs);
    }

    Date getNow()
    {
        return new Date();
    }

    /**
     * Check if the given Date is equal to now() plus the given offset in
     * Minutes. The equals is tolerant wihin plus minus the given delta.
     */
    public DateAssert minutesFromNow(long offsetInMinutes, int deltaInMS)
    {
        return secondFromNow(offsetInMinutes * 60, deltaInMS);
    }

    /**
     * Check if the given Date is equal to now() plus the given offset in
     * Minutes. The equals is tolerant wihin plus minus the given delta.
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
     * Fluent api: Test if Date is offset X away from now. With a detal of 15s
     * {@link #DEFAULT_DELTA_MS}
     */
    public DateInAssert isIn(long offset)
    {
        return new DateInAssert(this, offset);
    }

    /**
     * Checks if given Date is exclty the same
     * 
     * @param datum
     */
    public void isExactly(Date datum)
    {
        long diff = _date.getTime() - datum.getTime();
        if (diff != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("expected ");
            sb.append(datum);
            sb.append(" but was ");
            sb.append(_date);
            sb.append(" diff=");
            sb.append(diff);
            sb.append("ms");
            fail(sb.toString());
        }
    }

    /**
     * Checks if given Calendar is exclty the same
     * 
     * @param datum
     */
    public void isExactly(Calendar datum)
    {
        isExactly(datum.getTime());
    }
    
    /**
     * Checks if given Datum is exclty the same
     * 
     * @param datum
     */
    public void isExactly(Datum datum)
    {
      isExactly(datum.asDate());
    }

    /**
     * Checks if given Date is exactly the same.
     * 
     * Uses DataString for String to Date transformation.
     * 
     * @param datum "dd.MM.yyyy HH:mm:ss" and "yyyy-MM-dd HH:mm:ss"
     */
    public void isExactly(String datum)
    {
      Date exp = DateUtil.asDate(datum);
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
     * From here on only check the Time Part ( Hour, minute, seconds ans
     * miliseconds )
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
     * @param doCheckAlsoMilliseconds if {@code false} the miliseconds of the
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
            return _dateAssert.miliSecondsFromNow(_offset, DEFAULT_DELTA_MS);
        }

    }

    public class DateOnlyAssert
    {

        Calendar _date;

        public DateOnlyAssert(Date date)
        {
            _date = createCopy(date);
        }

        public DateOnlyAssert(Calendar date)
        {
            _date = createCopy(date);
        }
        
        public DateOnlyAssert(Datum date)
        {
          _date = createCopy(date.asCalendar());
        }
        
        public DateOnlyAssert(XMLGregorianCalendar date)
        {
          if(date == null) 
          {
            _date = null;
          } 
          else 
          {
            _date = createCopy(date.toGregorianCalendar());
          }
        }

        public DateOnlyAssert isExactly(String expect)
        {
            isExactly(createCopy((DateUtil.asDate(expect))));
            return this;
        }

        public DateOnlyAssert isExactly(Date expect)
        {
            isExactly(createCopy(expect));
            return this;
        }
        
        public DateOnlyAssert isExactly(Datum expect)
        {
          isExactly(expect.asCalendar());
          return this;
        }
        
        public DateOnlyAssert isExactly(XMLGregorianCalendar expect)
        {
          isExactly(expect.toGregorianCalendar());
          return this;
        }

        public DateOnlyAssert isExactly(Calendar expect)
        {
            Calendar calExpect = createCopy(expect);
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

        public TimeOnlyAssert(Calendar date, boolean checkMiliseconds)
        {
            _date = createCopy(date);
            _checkMiliseconds = checkMiliseconds;
        }

        public TimeOnlyAssert isExactly(String expect)
        {
            isExactly(DateUtil.asDate(expect));
            return this;
        }

        public TimeOnlyAssert isExactly(Date expect)
        {
            isExactly(createCopy(expect));
            return this;
        }
        
        public TimeOnlyAssert isExactly(XMLGregorianCalendar expect)
        {
          isExactly(expect.toGregorianCalendar());
          return this;
        }
        
        public TimeOnlyAssert isExactly(Datum expect)
        {
          isExactly(expect.asCalendar());
          return this;
        }

        public TimeOnlyAssert isExactly(Calendar expect)
        {
            Calendar calExpect = createCopy(expect);
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

    public DateAssert as(String text)
    {
        description(text);
        return this;
    }

}
