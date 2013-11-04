package com.seitenbau.stu.database.generator;

import static com.seitenbau.stu.database.generator.DataBuilderGenerator.booleanToJava;
import static com.seitenbau.stu.database.generator.DataBuilderGenerator.byteArrayToJava;
import static com.seitenbau.stu.database.generator.DataBuilderGenerator.dateToJava;
import static com.seitenbau.stu.database.generator.DataBuilderGenerator.decimalToJava;
import static com.seitenbau.stu.database.generator.DataBuilderGenerator.isPlaceholder;
import static com.seitenbau.stu.database.generator.DataBuilderGenerator.stringToJava;
import static com.seitenbau.stu.database.generator.DataBuilderGenerator.toJava;
import static org.fest.assertions.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import org.junit.Test;

public class DataBuilderGeneratorTest
{
  @Test
  public void testStringToJava()
  {
    assertThat(stringToJava("test")).isEqualTo("\"test\"");
    assertThat(stringToJava("test\"test")).isEqualTo("\"test\\\"test\"");
  }

  @Test
  public void testDecimalToJava()
  {
    assertThat(decimalToJava(new BigDecimal(Double.MAX_VALUE))).isNotEqualTo(
        "new java.math.BigDecimal(" + Double.MAX_VALUE + ")");
    assertThat(decimalToJava(new BigDecimal(Double.MIN_VALUE))).isNotEqualTo(
        "new java.math.BigDecimal(" + Double.MIN_VALUE + ")");
    assertThat(decimalToJava(new BigDecimal(Long.MAX_VALUE))).isNotEqualTo(
        "new java.math.BigDecimal(" + Long.MAX_VALUE + ")");
    assertThat(decimalToJava(new BigDecimal(Long.MIN_VALUE))).isNotEqualTo(
        "new java.math.BigDecimal(" + Long.MIN_VALUE + ")");
  }

  @Test
  public void testDateToJava()
  {
    long time = System.currentTimeMillis();

    Date sqlDate = new Date(time);
    assertThat(dateToJava(sqlDate, sqlDate.getClass().getCanonicalName())).isEqualTo(
        "new java.sql.Date(" + time + ") /* " + sqlDate + " */");

    Time sqlTime = new Time(time);
    assertThat(dateToJava(sqlTime, sqlTime.getClass().getCanonicalName())).isEqualTo(
        "new java.sql.Time(" + time + ") /* " + sqlTime + " */");

    Timestamp sqlTimestamp = new Timestamp(time);
    assertThat(dateToJava(sqlTimestamp, sqlTimestamp.getClass().getCanonicalName())).isEqualTo(
        "new java.sql.Timestamp(" + time + ") /* " + sqlTimestamp + " */");
  }

  @Test
  public void testByteArrayToJava()
  {
    assertThat(byteArrayToJava(new byte[] {0, 1, -1, 127, -128})).isEqualTo("new byte[] {0,1,-1,127,-128}");
  }

  @Test
  public void testIsPlaceholder()
  {
    assertThat(isPlaceholder("${TEST}")).isTrue();
    assertThat(isPlaceholder("${}")).isFalse();
    assertThat(isPlaceholder("${TEST")).isFalse();
    assertThat(isPlaceholder("$TEST}")).isFalse();
    assertThat(isPlaceholder("{TEST}")).isFalse();
    assertThat(isPlaceholder("$TEST")).isFalse();
    assertThat(isPlaceholder("TEST")).isFalse();
  }

  @Test
  public void testBooleanToJava()
  {
    assertThat(booleanToJava("TRUE")).isEqualTo("true");
    assertThat(booleanToJava("FALSE")).isEqualTo("false");
    assertThat(booleanToJava(010)).isEqualTo("true");
    assertThat(booleanToJava(000)).isEqualTo("false");
  }

  @Test
  public void testToJava()
  {
    assertThat(toJava(Integer.MIN_VALUE, Integer.class.getCanonicalName())).isEqualTo(
        Integer.toString(Integer.MIN_VALUE));
    assertThat(toJava(Long.MIN_VALUE, Long.class.getCanonicalName())).isEqualTo(Long.toString(Long.MIN_VALUE));
    assertThat(toJava(Double.MIN_VALUE, Double.class.getCanonicalName())).isEqualTo(Double.toString(Double.MIN_VALUE));
    assertThat(toJava(Float.MIN_VALUE, Float.class.getCanonicalName())).isEqualTo(Float.toString(Float.MIN_VALUE));
    assertThat(toJava(new Object(), null)).isNull();
  }

}
