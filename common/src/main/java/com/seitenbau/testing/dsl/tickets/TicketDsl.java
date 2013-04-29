package com.seitenbau.testing.dsl.tickets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketDsl
{
  public enum TicketState
  {
    OPEN, FIXED, IGNORED
  }

  static Pattern pattern = Pattern
      .compile("(?i)^(\\s*?(open|fixed|ignored)\\s*:)?(?!.*:).+$");

  TicketState _state;

  String _id;

  public TicketDsl(String dsl)
  {
    if (dsl == null)
    {
      throw new IllegalArgumentException("dsl was null");
    }
    String[] items = dsl.split(":");
    if (items.length > 2)
    {
      throw new IllegalArgumentException("dsl has too many parts");
    }
    if (items.length == 2)
    {
      _state = parseState(items[0]);
      _id = parseId(items[1]);
    }
    else
    {
      _state = TicketState.OPEN;
      _id = parseId(items[0]);
    }
  }

  protected String parseId(String id)
  {
    if (id == null || id.trim().length() == 0)
    {
      throw new IllegalArgumentException("dls ticket id is invalid");
    }
    return id.trim();
  }

  protected TicketState parseState(String state)
  {
    TicketState result = parseStateInternal(state);
    if (result == null)
    {
      throw new IllegalArgumentException("dls state is invalid");
    }
    return result;
  }

  public static boolean isTicketDSLState(String stateDsl)
  {
    return parseStateInternal(stateDsl) != null;
  }

  static TicketState parseStateInternal(String stateDsl)
  {
    String trimmed = stateDsl.trim();
    for (TicketState eState : TicketState.values())
    {
      if (eState.name().equalsIgnoreCase(trimmed))
      {
        return eState;
      }
    }
    return null;
  }

  public String getId()
  {
    return _id;
  }

  public TicketState getState()
  {
    return _state;
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append(_state);
    sb.append(":");
    sb.append(_id);
    return sb.toString();
  }

  public boolean isSolved()
  {
    switch (_state)
    {
    case OPEN:
      return false;
    case FIXED:
    case IGNORED:
      return true;
    default:
      throw new IllegalStateException("not supported state");
    }
  }

  public static boolean isTicketDSL(String ticketDsl)
  {
    if (ticketDsl == null)
    {
      return false;
    }
    Matcher matcher = pattern.matcher(ticketDsl.trim());
    if (!matcher.matches())
    {
      return false;
    }
    return true;
  }

  public boolean isFixed()
  {
    return TicketState.FIXED.equals(_state);
  }

}
