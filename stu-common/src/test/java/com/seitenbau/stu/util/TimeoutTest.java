package com.seitenbau.stu.util;

import static org.fest.assertions.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TimeoutTest
{
  @Test
  public void checkTimeoutBehaviour() throws InterruptedException
  {
    Timeout sut = new Timeout(TimeUnit.MILLISECONDS, 100);
    assertThat(sut.timeout()).isFalse();
    Thread.sleep(200);
    assertThat(sut.timeout()).isTrue();
  }

  @Test
  public void checkTimeoutBehaviourS() throws InterruptedException
  {
    Timeout sut = new Timeout(2); // default unit is seconds!
    assertThat(sut.timeout()).isFalse();
    Thread.sleep(1100);
    assertThat(sut.timeout()).isFalse();
    Thread.sleep(910);
    assertThat(sut.timeout()).isTrue();
  }
}
