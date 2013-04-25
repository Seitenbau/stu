package com.seitenbau.testing.util.date;

public class Zeitspanne
{
  long diffInMs;

  public Zeitspanne(long diff)
  {
    diffInMs = diff;
  }

  public long inMS()
  {
    return diffInMs;
  }

  public int inSeconds()
  {
    return (int) (diffInMs / 1000);
  }

  public int inMinutes()
  {
    return inSeconds() / 60;
  }

  public int inHours()
  {
    return inMinutes() / 60;
  }

  public int inDays()
  {
    return inHours() / 24;
  }

}
