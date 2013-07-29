package com.seitenbau.testing.asserts.fest.impl;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TicketAssertTest
{
  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void nullIsSolved()
  {
    TicketAssert sut = new TicketAssert();
    assertThat(sut.isSolved()).isTrue();
    assertThat(sut.isOpen()).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyStringIsSolved()
  {
    new TicketAssert("");
  }

  @Test
  public void fixedStringIsSolved()
  {
    TicketAssert sut = new TicketAssert("fixed:ticket");
    assertThat(sut.isSolved()).isTrue();
    assertThat(sut.isOpen()).isFalse();
  }

  @Test
  public void notAllfixedIsNotSolved()
  {
    TicketAssert sut = new TicketAssert("fixed:ticket", "notSovled");
    assertThat(sut.isSolved()).isFalse();
    assertThat(sut.isOpen()).isTrue();
  }

  @Test
  public void notFixedStringIsNotSolved()
  {
    TicketAssert sut = new TicketAssert("ticket");
    assertThat(sut.isSolved()).isFalse();
    assertThat(sut.isOpen()).isTrue();
  }
  
  @Test
  public void failIfOpen_OneTicketNotSolved()
  {
    TicketAssert sut = new TicketAssert("fixed:ticket", "notSovled");
    exception.expectMessage(
      "At least one ticket is open: [FIXED:ticket, OPEN:notSovled]");
    sut.failIfOpen();
  }
  
  @Test
  public void failIfOpen_AllTicketNotSolved()
  {
      TicketAssert sut = new TicketAssert("fixed:ticket", "fixed:notSovled");
      sut.failIfOpen();
  }
  
  @Test
  public void failIfSolved_OneTicketNotSolved()
  {
      TicketAssert sut = new TicketAssert("fixed:ticket", "notSovled","open:x");
      sut.failIfSolved();
  }
  
  @Test
  public void failIfSolved_AllTicketNotSolved()
  {
      TicketAssert sut = new TicketAssert("fixed:ticket", "fixed:notSovled");
      exception.expectMessage(
              "All tickets are solved: [FIXED:ticket, FIXED:notSovled]");
      sut.failIfSolved();
  }
 
}
