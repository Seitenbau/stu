package com.seitenbau.testing.asserts.fest;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.datatype.XMLGregorianCalendar;

import com.seitenbau.testing.asserts.fest.impl.AtomicIntegerAssert;
import com.seitenbau.testing.asserts.fest.impl.DateAssert;
import com.seitenbau.testing.asserts.fest.impl.ExtendedFileAssert;
import com.seitenbau.testing.asserts.fest.impl.ExtendedFileAssert.ProvidesFile;
import com.seitenbau.testing.asserts.fest.impl.ExtendedStringAssert;
import com.seitenbau.testing.asserts.fest.impl.FileReferenceAssert;
import com.seitenbau.testing.asserts.fest.impl.TicketAssert;
import com.seitenbau.testing.asserts.fest.impl.UriAssert;
import com.seitenbau.testing.asserts.fest.impl.UrlAssert;
import com.seitenbau.testing.io.files.FileReference;
import com.seitenbau.testing.util.date.DateBuilder;

public class Assertions extends org.fest.assertions.Assertions
{
  /**
   * Fluent assertions of the given date Supports Dates:
   * {@link #formats} "dd.MM.yyyy HH:mm:ss.SSS" and
   * "yyyy-MM-dd HH:mm:ss.SSS" or only date or only time. </p> When
   * only a Time is give, the Date will be 1.1.1970. When only a Date
   * is given the time part will be 0:0:0.000
   */
  public static ExtendedStringAssert assertThat(String dateString)
  {
    return AssertionsFactory.get().create(dateString);
  }

  /**
   * Fluent assertions of the given date
   */
  public static DateAssert assertThat(Date date)
  {
    return AssertionsFactory.get().create(date);
  }

  /**
   * Fluent assertions of the given XML calendar
   */
  public static DateAssert assertThat(XMLGregorianCalendar xmlDate)
  {
    return AssertionsFactory.get().create(xmlDate);
  }

  /**
   * Fluent assertions of the given date
   */
  public static DateAssert assertThat(DateBuilder dateBuilder)
  {
    return AssertionsFactory.get().create(dateBuilder);
  }

  /**
   * Fluent assertions of the given date
   */
  public static DateAssert assertThat(Calendar calendar)
  {
    return AssertionsFactory.get().create(calendar);
  }

  /**
   * Fluent assertions of the given file
   */
  public static ExtendedFileAssert assertThat(File file)
  {
    return AssertionsFactory.get().create(file);
  }

  /**
   * Fluent assertions of the given file
   */
  public static ExtendedFileAssert assertThat(ProvidesFile file)
  {
    return AssertionsFactory.get().create(file);
  }
  
  /**
   * Fluent assertions of the given file
   */
  public static FileReferenceAssert assertThat(FileReference file)
  {
    return AssertionsFactory.get().create(file);
  }

  /**
   * Interpret the parameter as List of Tickets. (TicketDsl style)
   * @param tickets
   */
  public static TicketAssert ticket(String... tickets)
  {
    return AssertionsFactory.get().createTicket(tickets);
  }

  /**
   * Fluent assertions of the given uri
   */
  public static UriAssert assertThat(URI uri)
  {
    return AssertionsFactory.get().create(uri);
  }

  /**
   * Fluent assertions of the given url
   */
  public static UrlAssert assertThat(URL url)
  {
    return AssertionsFactory.get().create(url);
  }

  /**
   * Fluent assertions of the given number
   */
  public static AtomicIntegerAssert assertThat(AtomicInteger number)
  {
    return AssertionsFactory.get().create(number);
  }
}
