package com.seitenbau.testing.dsl.requirement;

import org.junit.Test;
import static com.seitenbau.testing.asserts.fest.Assertions.*;

public class TestPropertyIsTest
{
  @Test
  public void check()
  {
    TestPropertyIs sut = new TestPropertyIs();

    assertThat(sut.check(null, "wert")).isFalse();
    assertThat(sut.check("werT", "wert")).isFalse();
    assertThat(sut.check("wert", "werT")).isFalse();
    assertThat(sut.check("wert", null)).isFalse();

    assertThat(sut.check(null, null)).isTrue();
    assertThat(sut.check("werT", "werT")).isTrue();
  }
}
