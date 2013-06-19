package com.seitenbau.testing.dbunit.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

public class TestNullCompatibleEquivalenceTest
{
  @Test
  public void nullWithNull()
  {
    assertTrue(NullCompatibleEquivalence.equals(null, null));
  }

  @Test
  public void longWithNull()
  {
    assertFalse(NullCompatibleEquivalence.equals(Long.valueOf(1), null));
  }

  @Test
  public void longWithEqualInteger()
  {
    assertTrue(NullCompatibleEquivalence.equals(Long.valueOf(1), Integer.valueOf(1)));
  }

  @Test
  public void longWithDifferentInteger()
  {
    assertFalse(NullCompatibleEquivalence.equals(Long.valueOf(1), Integer.valueOf(2)));
  }

  @Test
  public void longWithEqualLong()
  {
    assertTrue(NullCompatibleEquivalence.equals(Long.valueOf(1), Long.valueOf(1)));
  }

  @Test
  public void longWithDifferentLong()
  {
    assertFalse(NullCompatibleEquivalence.equals(Long.valueOf(1), Long.valueOf(2)));
  }

  @Test
  public void doubleWithEqualFloat()
  {
    assertTrue(NullCompatibleEquivalence.equals(Double.valueOf(0.5), Float.valueOf(0.5f)));
  }

  @Test
  public void doubleWithDifferentFloat()
  {
    assertFalse(NullCompatibleEquivalence.equals(Double.valueOf(0.5), Float.valueOf(0.25f)));
  }

  @Ignore("BigDecimal from Long is 1 and from Double is 1.0 ...")
  @Test
  public void longWithEqualDouble()
  {
    assertTrue(NullCompatibleEquivalence.equals(Long.valueOf(1), Double.valueOf(1)));
  }

  @Test
  public void longWithDifferentDouble()
  {
    assertFalse(NullCompatibleEquivalence.equals(Long.valueOf(1), Double.valueOf(2)));
  }
}
