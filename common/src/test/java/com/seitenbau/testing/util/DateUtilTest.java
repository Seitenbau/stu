package com.seitenbau.testing.util;

import static com.seitenbau.testing.asserts.fest.Assertions.assertThat;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import com.seitenbau.testing.util.date.DateBuilder;

public class DateUtilTest
{
  Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

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
    setupCalendar(format1);

    assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2010);
    assertThat(calendar.get(Calendar.MONTH) + 1).isEqualTo(2);
    assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(23);
    assertThat(calendar.get(Calendar.HOUR_OF_DAY)).isEqualTo(10);
    assertThat(calendar.get(Calendar.MINUTE)).isEqualTo(4);
    assertThat(calendar.get(Calendar.SECOND)).isEqualTo(12);
    assertThat(calendar.get(Calendar.MILLISECOND)).isEqualTo(894);
    assertThat(format1.getTime()).isEqualTo(1266915852894L);
  }

  @Test
  public void testAsDateFormatOrdering()
  {
    Date format1 = DateUtil.asDate("23.02.2010 10:04:12.894");
    setupCalendar(format1);

    assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2010);
    assertThat(calendar.get(Calendar.MONTH) + 1).isEqualTo(2);
    assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(23);
    assertThat(calendar.get(Calendar.HOUR_OF_DAY)).isEqualTo(10);
    assertThat(calendar.get(Calendar.MINUTE)).isEqualTo(4);
    assertThat(calendar.get(Calendar.SECOND)).isEqualTo(12);
    assertThat(calendar.get(Calendar.MILLISECOND)).isEqualTo(894);
    assertThat(format1.getTime()).isEqualTo(1266915852894L);
  }

  @Test
  public void testAsDateWithNoDate()
  {
    Date format1 = DateUtil.asDate("10:04:12.894");
    setupCalendar(format1);

    assertThat(calendar.get(Calendar.YEAR)).isEqualTo(1970);
    assertThat(calendar.get(Calendar.MONTH)).isEqualTo(0);
    assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(1);
    assertThat(calendar.get(Calendar.HOUR_OF_DAY)).isEqualTo(10);
    assertThat(calendar.get(Calendar.MINUTE)).isEqualTo(4);
    assertThat(calendar.get(Calendar.SECOND)).isEqualTo(12);
    assertThat(calendar.get(Calendar.MILLISECOND)).isEqualTo(894);
    assertThat(format1.getTime()).isEqualTo(32652894L);
  }

  @Test
  public void testAsDateWithNoTime()
  {
    Date format1 = DateUtil.asDate("2010-02-23");
    setupCalendar(format1);

    assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2010);
    assertThat(calendar.get(Calendar.MONTH) + 1).isEqualTo(2);
    assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(23);
    assertThat(calendar.get(Calendar.HOUR_OF_DAY)).isEqualTo(0);
    assertThat(calendar.get(Calendar.MINUTE)).isEqualTo(0);
    assertThat(calendar.get(Calendar.SECOND)).isEqualTo(0);
    assertThat(calendar.get(Calendar.MILLISECOND)).isEqualTo(0);
    assertThat(format1.getTime()).isEqualTo(1266879600000L);
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
    setupCalendar(datum.getTime());

    Date val = DateUtil.datum(datum).resetDate().resetTime().add(1).hour().asDate();
    Date ref = new Date(0);
    assertThat(val).isExactly(ref);
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

  private void setupCalendar(Date date)
  {
    calendar.setTimeInMillis(date.getTime());
    calendar.add(Calendar.MINUTE, -1 * date.getTimezoneOffset());
  }

}
