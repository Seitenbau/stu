package com.seitenbau.testing.asserts.fest;

import static com.seitenbau.testing.asserts.fest.Assertions.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import com.seitenbau.testing.asserts.fest.impl.AtomicIntegerAssert;
import com.seitenbau.testing.asserts.fest.impl.DateAssert;
import com.seitenbau.testing.asserts.fest.impl.ExtendedFileAssert;
import com.seitenbau.testing.asserts.fest.impl.ExtendedFileAssert.ProvidesFile;
import com.seitenbau.testing.asserts.fest.impl.ExtendedStringAssert;
import com.seitenbau.testing.asserts.fest.impl.TicketAssert;
import com.seitenbau.testing.asserts.fest.impl.UriAssert;
import com.seitenbau.testing.asserts.fest.impl.UrlAssert;
import com.seitenbau.testing.util.DateUtil;


public class AssertionsTest
{
  @Test
  public void ofString() 
  {
    Assert.assertEquals( ExtendedStringAssert.class,  assertThat("rainer").getClass() );
  }
  
  @Test
  public void ofDate() 
  {
    Assert.assertEquals( DateAssert.class,  assertThat(new Date()).getClass() );
  }
  
  @Test
  public void ofDateBuilder() 
  {
    Assert.assertEquals( DateAssert.class,  assertThat(DateUtil.datum() ).getClass() );
  }
  
  @Test
  public void ofCalendar() 
  {
    Assert.assertEquals( DateAssert.class,  assertThat(Calendar.getInstance()).getClass() );
  }
  
  @Test
  public void ofFile() 
  {
    Assert.assertEquals( ExtendedFileAssert.class,  assertThat(new File("/")).getClass() );
  }
  
  @Test
  public void ofProvidesFile() 
  {
    Assert.assertEquals( ExtendedFileAssert.class,  assertThat(new ProvidesFile()
    {
      public File getFile()
      {
        return null;
      }
    }).getClass() );
  }

  @Test
  public void ofTicket() 
  {
    Assert.assertEquals( TicketAssert.class,  ticket("SBT-12").getClass() );
  }
  
  @Test
  public void ofURI() throws MalformedURLException, URISyntaxException 
  {
    Assert.assertEquals( UriAssert.class,  assertThat(new URI("http://example.com")).getClass() );
  }
  
  @Test
  public void ofURL() throws MalformedURLException 
  {
    Assert.assertEquals( UrlAssert.class,  assertThat(new URL("http://example.com")).getClass() );
  }
  
  @Test
  public void ofAtomicInteger() 
  {
    Assert.assertEquals( AtomicIntegerAssert.class,  assertThat(new AtomicInteger()).getClass() );
  }
  
  @Test
  public void assertForXMLGregorianCalendar_Null() throws Exception
  {
    assertThat((XMLGregorianCalendar) null).isNull();
  }
  
  @Test
  public void assertForXMLGregorianCalendar() throws Exception
  {
    GregorianCalendar gc = new GregorianCalendar();
    DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
    XMLGregorianCalendar calc = datatypeFactory.newXMLGregorianCalendar(gc);
    assertThat(calc).isNotNull();
    assertThat(calc).is(gc.getTime());
  }
  
}
