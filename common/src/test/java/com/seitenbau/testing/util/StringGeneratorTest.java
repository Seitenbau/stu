package com.seitenbau.testing.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringGeneratorTest
{
  @Test
  public void validTestsForMethod_Gen1() throws Exception
  {
    assertEquals("somexxtext", StringGenerator.gen(10));
    assertEquals("s", StringGenerator.gen(1));
    assertEquals("", StringGenerator.gen(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTestsForMethod_Gen1() throws Exception
  {
    StringGenerator.gen(-1);
  }

  @Test
  public void validTestsForMethod_Gen2() throws Exception
  {
    assertEquals("rainerrain", StringGenerator.gen("rainer", 10));
    assertEquals("r", StringGenerator.gen("rainer", 1));
    assertEquals("", StringGenerator.gen("rainer", 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTestsForMethod_Gen2() throws Exception
  {
    StringGenerator.gen("rainer", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidfillForMethod_Gen2() throws Exception
  {
    StringGenerator.gen("", 2);
  }

  @Test
  public void validTestsForMethod_Gen4() throws Exception
  {
    assertEquals("preraipost", StringGenerator.gen("pre", "rainer", "post", 10));
    assertEquals("p", StringGenerator.gen("pre", "rainer", "post", 1));
    assertEquals("", StringGenerator.gen("pre", "rainer", "post", 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTestsForMethod_Gen4() throws Exception
  {
    StringGenerator.gen("pre", "rainer", "post", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidfillForMethod_Gen4() throws Exception
  {
    StringGenerator.gen("pre", "", "post", 2);
  }
  
  @Test
  public void trimToMaxLength() throws Exception
  {
    String fillString = "a string, nothing but a string ";

    assertEquals(fillString.trim() + ".",
        StringGenerator.gen("", fillString, "", fillString.length(), true));
    assertEquals(fillString.trim() + "...",
        StringGenerator.gen("", fillString + "  ", "", fillString.length() + 2, true));
    assertEquals(fillString.trim() + "...",
        StringGenerator.gen("", " " + fillString + " ", "", fillString.length() + 2, true));
  }

}
