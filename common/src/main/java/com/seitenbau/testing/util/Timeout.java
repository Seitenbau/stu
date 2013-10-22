package com.seitenbau.testing.util;

import java.util.concurrent.TimeUnit;

public class Timeout
{
  long started;

  long stopAt;

  public Timeout(TimeUnit unit, long maxWait)
  {
    init(unit, maxWait);
  }

  public Timeout(long maxWait)
  {
    init(TimeUnit.SECONDS, maxWait);
  }

  protected void init(TimeUnit unit, long maxWait)
  {
    started = System.currentTimeMillis();
    stopAt = started + unit.toMillis(maxWait);
  }

  public boolean timeout()
  {
    long cur = System.currentTimeMillis();
    return cur > stopAt;
  }
}
