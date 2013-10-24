package com.seitenbau.stu.asserts.fest;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.datatype.XMLGregorianCalendar;

import com.seitenbau.stu.asserts.fest.impl.AtomicIntegerAssert;
import com.seitenbau.stu.asserts.fest.impl.DateAssert;
import com.seitenbau.stu.asserts.fest.impl.ExtendedFileAssert;
import com.seitenbau.stu.asserts.fest.impl.ExtendedStringAssert;
import com.seitenbau.stu.asserts.fest.impl.FileReferenceAssert;
import com.seitenbau.stu.asserts.fest.impl.TicketAssert;
import com.seitenbau.stu.asserts.fest.impl.UriAssert;
import com.seitenbau.stu.asserts.fest.impl.UrlAssert;
import com.seitenbau.stu.asserts.fest.impl.ExtendedFileAssert.ProvidesFile;
import com.seitenbau.stu.io.files.FileReference;
import com.seitenbau.stu.util.date.DateBuilder;

/**
 * Internal factory used by {@link Assertions} to create the actual assertion object.
 * This allows you to replace the created assertions objects with your own, but you're still
 * limited to the original api :(. 
 */
public class AssertionsFactory
{
  static AssertionsFactory _factory = new AssertionsFactory();

  public static void registerFactory(AssertionsFactory factory) {
    _factory=factory;
  }

  public static AssertionsFactory get()
  {
    return _factory;
  }

  public ExtendedFileAssert create(ProvidesFile file)
  {
    return new ExtendedFileAssert(file);
  }

  public ExtendedFileAssert create(File file)
  {
    return new ExtendedFileAssert(file);
  }
  
  public FileReferenceAssert create(FileReference file)
  {
    return new FileReferenceAssert(file);
  }

  public DateAssert create(Calendar calendar)
  {
    return new DateAssert(calendar);
  }

  public DateAssert create(DateBuilder dateBuilder)
  {
    return new DateAssert(dateBuilder.asCalendar());
  }

  public DateAssert create(Date date)
  {
    return new DateAssert(date);
  }

  public ExtendedStringAssert create(String text)
  {
    return new ExtendedStringAssert(text);
  }

  public UriAssert create(URI uri)
  {
    return new UriAssert(uri);
  }
  
  public UrlAssert create(URL url)
  {
    return new UrlAssert(url);
  }


  public AtomicIntegerAssert create(AtomicInteger number)
  {
    return new AtomicIntegerAssert(number);
  }

  public TicketAssert createTicket(String... tickets)
  {
    return new TicketAssert(tickets);
  }

  public DateAssert create(XMLGregorianCalendar xmlDate)
  {
    if(xmlDate != null)
    {
      return create(xmlDate.toGregorianCalendar());
    }
    else 
    {
      return create((Date) null);
    }
  }

}