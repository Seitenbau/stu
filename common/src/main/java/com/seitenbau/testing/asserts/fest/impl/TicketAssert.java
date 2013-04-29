package com.seitenbau.testing.asserts.fest.impl;

import static org.fest.assertions.Assertions.*;

import com.seitenbau.testing.dsl.tickets.TicketList;

public class TicketAssert
{
  TicketList _tickets;

  public TicketAssert(String... tickets)
  {
    _tickets = TicketList.of(tickets);
  }

  /**
   * @return {@code true} when all tickets are solved
   */
  public boolean isSolved()
  {
    return _tickets.hasAllTicketsSolved();
  }

  /**
   * @return {@code true} when at least one ticket is still open
   */
  public boolean isOpen()
  {
    return !_tickets.hasAllTicketsSolved();
  }
  
  /**
   * Throw a error when all tickets are solved.
   */
  public void failIfSolved()
  {
      assertThat(isSolved()).as("All tickets are solved: " + _tickets).isFalse();
  }
  
  /**
   * Throw a error when at least one ticket is open.
   */
  public void failIfOpen()
  {
      assertThat(isOpen()).as("At least one ticket is open: " + _tickets).isFalse();
  }

}
