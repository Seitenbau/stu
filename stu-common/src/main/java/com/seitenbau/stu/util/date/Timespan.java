package com.seitenbau.stu.util.date;

public class Timespan
{
  long diffInMs;

  public Timespan(long diff)
  {
    diffInMs = diff;
  }

  public long inMilliseconds()
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
