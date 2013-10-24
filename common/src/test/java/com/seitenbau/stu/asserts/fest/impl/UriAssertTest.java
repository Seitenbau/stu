package com.seitenbau.stu.asserts.fest.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.seitenbau.stu.asserts.fest.impl.UriAssert;

public class UriAssertTest
{
  UriAssert sut2 = sut();

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testNull()
  {
    new UriAssert(null).isNull();
  }

  @Test
  public void testEmpty() throws URISyntaxException
  {
    // prepare
    UriAssert sut = new UriAssert(new URI(""));

    // execute
    sut.isNotNull();
  }

  @Test
  public void testNotContains() throws URISyntaxException
  {
    // prepare
    UriAssert sut = new UriAssert(new URI(
        "http://bitnoise.de/jenkins"));

    // execute
    sut.containsHost("bitnoise.de")
        .hasNoFragment()
        .hasNoPort()
        .hasNoQueryPart()
        .hasProtocol("http")
        .hasPath("/jenkins")
        .isEqualTo("http://bitnoise.de/jenkins");
  }

  @Test
  public void testContains() throws URISyntaxException
  {
    // prepare
    UriAssert sut = new UriAssert(new URI(
        "http://bitnoise.de:80/jenkins?key=val&x=y#all"));

    // execute
    sut.containsHost("bitnoise.de")
        .hasFragment()
        .hasPort()
        .hasQueryPart()
        .hasProtocol("http")
        .hasPath("/jenkins")
        .hasPort(80)
        .hasFragment("all")
        .queryIsEqualTo("key=val&x=y")
        .isEqualTo("http://bitnoise.de:80/jenkins?key=val&x=y#all");
  }

  @Test
  public void testContainsFtp() throws URISyntaxException
  {
    // prepare
    UriAssert sut = sut();

    Map<String, String> entries = new HashMap<String, String>();
    entries.put("key", "val");
    entries.put("x", "y");
    // execute
    sut.containsHost("seitenbau.com")
        .hasFragment()
        .hasPort()
        .hasQueryPart()
        .hasProtocol("ftp")
        .hasPath("//root")
        .hasPort(21)
        .containsQuery("key", "val")
        .containsQuery("x", "y")
        .containsQueries(entries)
        .hasFragment("file1")
        .queryIsEqualTo("key=val&x=y")
        .isEqualTo("ftp://seitenbau.com:21//root?key=val&x=y#file1")
        .startsWith("ftp://seitenbau.com:21//root?key=val&x=y#file1")
        .endsWith("ftp://seitenbau.com:21//root?key=val&x=y#file1");
  }

  UriAssert sut()
  {
    try
    {
      return new UriAssert(new URI(
          "ftp://seitenbau.com:21//root?key=val&x=y#file1"));
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testFailWhen_WrongPort() throws URISyntaxException
  {
    // verify
    exception.expect(AssertionError.class);
    exception.expectMessage("expected:<2[2]> but was:<2[1]>");

    // execute
    sut2.hasPort().hasPort(22);
  }

  @Test
  public void testFailWhen_WrongFragment() throws URISyntaxException
  {
    // verify
    exception.expect(AssertionError.class);
    exception.expectMessage("expected:<'[x]1'> but was:<'[file]1'>");

    // execute
    sut2.hasPort().hasFragment("x1");
  }

  @Test
  public void testFailWhen_WrongPath() throws URISyntaxException
  {
    // verify
    exception.expect(AssertionError.class);
    exception.expectMessage("expected:<'[p1]'> but was:<'[//root]'>");

    // execute
    sut2.hasPort().hasPath("p1");
  }

  @Test
  public void testFailWhen_WrongProtocol() throws URISyntaxException
  {
    // verify
    exception.expect(AssertionError.class);
    exception.expectMessage("expected:<'f[ile]'> but was:<'f[tp]'>");

    // execute
    sut2.hasPort().hasProtocol("file");
  }

  @Test
  public void testFailWhen_WrongUrl() throws URISyntaxException
  {
    // verify
    exception.expect(AssertionError.class);
    exception.expectMessage("expected:<f[ile://]file1> but was:" +
        "<f[tp://seitenbau.com:21//root?key=val&x=y#]file1>");

    // execute
    sut2.hasPort().isEqualTo("file://file1");
  }

  @Test
  public void testFailWhen_WrongUrlNull() throws URISyntaxException
  {
    // verify
    exception.expect(AssertionError.class);
    exception.expectMessage("expected:<null> but was:" +
        "<ftp://seitenbau.com:21//root?key=val&x=y#file1>");

    // execute
    sut2.hasPort().isEqualTo(null);
  }
  
  @Test
  public void testFailWhen_WrongUrlInExpect() throws URISyntaxException
  {
    // verify
    exception.expect(RuntimeException.class);
    exception.expectMessage(  "The Expected URI was wrong! Check you Testcode! :::");
    
    // execute
    sut2.hasPort().isEqualTo("::");
  }

  @Test
  public void testFailWhen_WrongStartWith() throws URISyntaxException
  {
    // verify
    exception.expect(AssertionError.class);
    exception
        .expectMessage(
        "'ftp://seitenbau.com:21//root?key=val&x=y#file1'> should start with:"
            + "<'ftp://seitenbau.com:21//root?key=val&x=y#file1_'>");

    // execute
    sut2.hasPort()
        .startsWith("ftp://seitenbau.com:21//root?key=val&x=y#file1_");
  }

  @Test
  public void testFailWhen_WrongEndsWith() throws URISyntaxException
  {
    // verify
    exception.expect(AssertionError.class);
    exception
        .expectMessage(
        "'ftp://seitenbau.com:21//root?key=val&x=y#file1'> should end with:"
            + "<'ftp://seitenbau.com:21//root?key=val&x=y#file1_'>");

    // execute
    sut2.hasPort().endsWith("ftp://seitenbau.com:21//root?key=val&x=y#file1_");
  }
}
