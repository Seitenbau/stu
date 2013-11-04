package com.seitenbau.stu.asserts.fest.impl;

import java.util.concurrent.atomic.AtomicInteger;
import static org.fest.assertions.Assertions.*;

import org.fest.assertions.GenericAssert;

public class AtomicIntegerAssert extends GenericAssert<AtomicIntegerAssert, AtomicInteger>
{
  public AtomicIntegerAssert(AtomicInteger number)
  {
    super(AtomicIntegerAssert.class, number);
  }

  public void isEqualTo(int expected)
  {
    assertThat(actual).isNotNull();
    assertThat(actual.intValue()).isEqualTo(expected);
  }

}
