package com.seitenbau.testing.dsl.tickets;

import java.util.ArrayList;
import java.util.List;

import com.seitenbau.testing.annotation.TestIssue;

public class TicketList
{
  List<TicketDsl> _tickets = new ArrayList<TicketDsl>();

  public static TicketList of(TestIssue annotation)
  {
    if (annotation == null)
    {
      return new TicketList();
    }
    return of(annotation.value());
  }

  public static TicketList of(String... ticketList)
  {
    TicketList result = new TicketList();
    if (ticketList == null)
    {
      return result;
    }
    for (String s : ticketList)
    {
      result.addTicket(s);
    }
    return result;
  }

  public void addTicket(String ticketDSL)
  {
    _tickets.add(new TicketDsl(ticketDSL));
  }

  public boolean hasAllTicketsSolved()
  {
    for (TicketDsl ticket : _tickets)
    {
      if (!ticket.isSolved())
      {
        return false;
      }
    }
    return true;
  }

  public List<TicketDsl> getAllUnsolvedTickets()
  {
    List<TicketDsl> result = new ArrayList<TicketDsl>();
    for (TicketDsl ticket : _tickets)
    {
      if (!ticket.isSolved())
      {
        result.add(ticket);
      }
    }
    return result;
  }
  
  @Override
  public String toString() {
      return _tickets.toString();
  }

  public int size()
  {
    return _tickets.size();
  }
}
