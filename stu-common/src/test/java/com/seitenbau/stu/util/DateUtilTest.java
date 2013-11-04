package com.seitenbau.stu.util;

import static com.seitenbau.stu.asserts.fest.Assertions.assertThat;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

import com.seitenbau.stu.util.DateUtil;
import com.seitenbau.stu.util.date.DateBuilder;

public class DateUtilTest
{
  @Test
  public void testAsDateFormants()
  {
    Date format1 = DateUtil.asDate("2010-01-23 10:00");
    Date format2 = DateUtil.asDate("23.01.2010 10:00");
    assertThat(format1.getTime()).isEqualTo(format2.getTime());
  }

  @Test
  public void testAsDateFormantsWithMS()
  {
    Date format1 = DateUtil.asDate("2010-02-23 10:04:12.894");

    DateTime dt = new DateTime(format1);

    assertThat(dt.year().get()).isEqualTo(2010);
    assertThat(dt.monthOfYear().get()).isEqualTo(2);
    assertThat(dt.dayOfMonth().get()).isEqualTo(23);
    assertThat(dt.hourOfDay().get()).isEqualTo(10);
    assertThat(dt.minuteOfHour().get()).isEqualTo(4);
    assertThat(dt.secondOfMinute().get()).isEqualTo(12);
    assertThat(dt.millisOfSecond().get()).isEqualTo(894);
  }

  @Test
  public void testAsDateFormatOrdering()
  {
    Date format1 = DateUtil.asDate("23.02.2010 10:04:12.894");
    DateTime dt = new DateTime(format1);

    assertThat(dt.year().get()).isEqualTo(2010);
    assertThat(dt.monthOfYear().get()).isEqualTo(2);
    assertThat(dt.dayOfMonth().get()).isEqualTo(23);
    assertThat(dt.hourOfDay().get()).isEqualTo(10);
    assertThat(dt.minuteOfHour().get()).isEqualTo(4);
    assertThat(dt.secondOfMinute().get()).isEqualTo(12);
    assertThat(dt.millisOfSecond().get()).isEqualTo(894);
  }

  @Test
  public void testAsDateWithNoDate()
  {
    Date format1 = DateUtil.asDate("10:04:12.894");
    DateTime dt = new DateTime(format1);

    assertThat(dt.year().get()).isEqualTo(1970);
    assertThat(dt.monthOfYear().get()).isEqualTo(1);
    assertThat(dt.dayOfMonth().get()).isEqualTo(1);
    assertThat(dt.hourOfDay().get()).isEqualTo(10);
    assertThat(dt.minuteOfHour().get()).isEqualTo(4);
    assertThat(dt.secondOfMinute().get()).isEqualTo(12);
    assertThat(dt.millisOfSecond().get()).isEqualTo(894);
  }

  @Test
  public void testAsDateWithNoTime()
  {
    Date format1 = DateUtil.asDate("2010-02-23");
    DateTime dt = new DateTime(format1);

    assertThat(dt.year().get()).isEqualTo(2010);
    assertThat(dt.monthOfYear().get()).isEqualTo(2);
    assertThat(dt.dayOfMonth().get()).isEqualTo(23);
    assertThat(dt.hourOfDay().get()).isEqualTo(0);
    assertThat(dt.minuteOfHour().get()).isEqualTo(0);
    assertThat(dt.secondOfMinute().get()).isEqualTo(0);
    assertThat(dt.millisOfSecond().get()).isEqualTo(0);
  }

  @Test
  public void testCalendar()
  {
    Calendar format1 = DateUtil.asCalendar("2010-02-23 10:04:12.894");

    assertThat(format1.get(Calendar.YEAR)).isEqualTo(2010);
    assertThat(format1.get(Calendar.MONTH) + 1).isEqualTo(2);
    assertThat(format1.get(Calendar.DAY_OF_MONTH)).isEqualTo(23);
    assertThat(format1.get(Calendar.HOUR)).isEqualTo(10);
    assertThat(format1.get(Calendar.MINUTE)).isEqualTo(4);
    assertThat(format1.get(Calendar.SECOND)).isEqualTo(12);
    assertThat(format1.get(Calendar.MILLISECOND)).isEqualTo(894);
  }

  @Test(expected = RuntimeException.class)
  public void testCalendarParsingError()
  {
    DateUtil.asCalendar("2010-02- xxx");
  }

  @Test
  public void testCalendarParsingNull()
  {
    assertThat(DateUtil.asCalendar(null)).isNull();
  }

  @Test
  public void toDateUtilTest1()
  {
    Date datum = DateUtil.asDate("2010-02-23 10:04:12.894");
    String format1 = DateUtil.formatDate(datum, "yyyy-MM-dd");
    assertThat(format1).isEqualTo("2010-02-23");
  }

  @Test
  public void toDateUtilTest2()
  {
    Date datum = DateUtil.asDate("2010-02-23 10:04:12.894");
    String format1 = DateUtil.formatDate(datum, "yyyy-MM-dd 00:00");
    assertThat(format1).isEqualTo("2010-02-23 00:00");
  }

  @Test
  public void toDateUtilTest3()
  {
    Date datum = DateUtil.asDate("2010-02-23 10:04:12.894");
    String format1 = DateUtil.formatDate(datum, "yyyy-MM-dd");
    assertThat(format1).isEqualTo("2010-02-23");
  }

  @Test
  public void toDateUtilTest4()
  {
    DateBuilder datum = DateUtil.datum("2010-02-23 10:04:12.894");
    datum = DateUtil.datum(datum);
    String format1 = DateUtil.formatDate(datum, "'x'yyyy,MM.dd HHmm_ss:SSS' rainer'");
    assertThat(format1).isEqualTo("x2010,02.23 1004_12:894 rainer");
  }

  @Test
  public void toDateUtilTest5()
  {
    String format1 = DateUtil.formatDate("yyyy-dd-MM 00:00");
    assertThat(format1).matches("\\d\\d\\d\\d-\\d\\d-\\d\\d 00:00");
  }

  @Test
  public void testNull()
  {
    assertThat(DateUtil.asDate(null)).isNull();
  }

  @Test(expected = RuntimeException.class)
  public void testEmpty()
  {
    DateUtil.asDate("");
  }

  @Test
  public void testBuilder()
  {
    assertThat(DateUtil.datum().asDate()).isNow();
    assertThat(DateUtil.datum().asCalendar()).isNow();
  }

  @Test
  public void testBuilderRoundtrip()
  {
    Calendar datum = DateUtil.asCalendar("2010-02-23 10:04:12.894");
    assertThat(DateUtil.datum(datum)).isExactly("2010-02-23 10:04:12.894");
  }

  @Test
  public void testBuilderOnlyDate()
  {
    Calendar datum = DateUtil.asCalendar("2010-02-23 10:04:12.894");
    Date val = DateUtil.datum(datum).resetDate().asDate();
    assertThat(val).isExactly("1970-01-01 10:04:12.894");
  }

  @Test
  public void testBuilderOnlyTimeAndNoMS()
  {
    Calendar datum = DateUtil.asCalendar("2010-02-23 10:04:12.894");
    Date val = DateUtil.datum(datum).resetDate().resetMilliseconds().asDate();
    assertThat(val).isExactly("1970-01-01 10:04:12.000");
  }

  @Test
  public void testBuilderOnlyTime()
  {
    Calendar datum = DateUtil.asCalendar("2010-02-23 10:04:12.894");
    Date val = DateUtil.datum(datum).resetTime().asDate();
    assertThat(val).isExactly("2010-02-23 00:00:00.000");
  }

  @Test
  public void testBuilderRemoveAllIs1970()
  {
    Calendar datum = DateUtil.asCalendar("2010-02-23 10:04:12.894");

    Date val = DateUtil.datum(datum).resetDate().resetTime().asDate();
    DateTime dt = new DateTime(val);
    Date ref = dt.toDate();

    assertThat(val).isExactly(ref);
    assertThat(dt.year().get()).isEqualTo(1970);
    assertThat(dt.monthOfYear().get()).isEqualTo(1);
    assertThat(dt.dayOfMonth().get()).isEqualTo(1);
    assertThat(dt.hourOfDay().get()).isEqualTo(0);
    assertThat(dt.minuteOfHour().get()).isEqualTo(0);
    assertThat(dt.secondOfMinute().get()).isEqualTo(0);
    assertThat(dt.millisOfSecond().get()).isEqualTo(0);
  }

  @Test
  public void testBuilder_Increment()
  {
    Calendar datum = DateUtil.asCalendar("2010-02-23 10:04:12.894");
    Date val = DateUtil.datum(datum).add(5).seconds().add(5).second().add(5).minutes().add(5).minute().add(5).hours()
        .add(5).hour().add(5).days().add(5).day().add(2).months().add(2).month().add(5).years().add(5).year().add(10)
        .milliseconds().add(100).millisecond().asDate();
    assertThat(val).isExactly("2020-07-05 20:14:23.004");
  }

  @Test
  public void testBuilder_set()
  {
    Date datum = DateUtil.asDate("2010-02-23 10:04:12.894");
    DateBuilder builder = DateUtil.datum(datum).set(15).second().set(14).minute().set(13).hour().set(12).day().set(4)
        .month().set(10).year().set(19).millisecond();
    assertThat(builder.asDate()).isExactly("0010-04-12 13:14:15.019");
    assertThat(builder.asString("yyyy-dd SSS")).isEqualTo("0010-12 019");
  }

  @Test
  public void testBuilder_sets()
  {
    Date datum = DateUtil.asDate("2010-02-23 10:04:12.894");
    DateBuilder builder = DateUtil.datum(datum).set(15).seconds().set(14).minutes().set(13).hours().set(12).days()
        .set(4).months().set(10).years().set(19).milliseconds();
    assertThat(builder.asDate()).isExactly("0010-04-12 13:14:15.019");
    assertThat(builder.asString("yyyy-dd SSS")).isEqualTo("0010-12 019");
  }

  @Test
  public void make100()
  {
    assertThat(DateUtil.createCopy((Date) null)).isNull();
    assertThat(DateUtil.createCopy((Calendar) null)).isNull();
    new DateUtil()
    {
      // to get to 100% coverage ... :)
    };
  }
}
