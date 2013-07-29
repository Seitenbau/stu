package com.seitenbau.testing.dsl.requirement;

import org.junit.Test;
import static com.seitenbau.testing.asserts.fest.Assertions.*;

public class TestPropertyIsTest
{
  @Test
  public void check()
  {
    TestPropertyIs sut = new TestPropertyIs();

    assertThat(sut.check(null, "value")).isFalse();
    assertThat(sut.check("valuE", "value")).isFalse();
    assertThat(sut.check("value", "valuE")).isFalse();
    assertThat(sut.check("value", null)).isFalse();

    assertThat(sut.check(null, null)).isTrue();
    assertThat(sut.check("valuE", "valuE")).isTrue();
  }
}
