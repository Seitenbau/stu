package com.seitenbau.testing.config.impl;

import static com.seitenbau.testing.asserts.fest.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.fest.assertions.StringAssert;
import org.junit.Test;

public class VariableDslProcessorTest extends TestCase
{
  VariableDslProcessor sut = new VariableDslProcessor();

  Map<String, String> data;

  {
    data = new HashMap<String, String>();
    data.put("rainer", "val-rainer");
    data.put("key02", "val-key02");
    data.put("key03", "val-key03");
    data.put("server-val-key03", "val-key03-server");
    data.put("innerKey0", "inner-value");
    data.put("innerKey1", "level1-${innerKey0}");
    data.put("verschachtelt", "level2-${innerKey1}");
    data.put("escaped", "esx-${rainer}-\\${rainer}");
  }

  @Test
  public void testOuterKeyHasNoDSL()
  {
    verify("noDsl").isNull();
    verify("key02").isEqualTo("val-key02");
    verify("key02 ").isNull();
  }

  @Test
  public void testReplacement()
  {
    verify("${notFound}").isEqualTo("${notFound}");
    verify("${rainer}").isEqualTo("val-rainer");
    verify("${verschachtelt}").isEqualTo("level2-level1-inner-value");
  }

  @Test
  public void testVerschachteltesReplacement()
  {
    verify("notFound${rainer}$${verschachtelt}").isEqualTo("notFoundval-rainer$level2-level1-inner-value");

    verify("notFound${rainer${rainer}}").isEqualTo("notFound${rainerval-rainer}");
    verify("notFound${server-${key03}}").isEqualTo("notFoundval-key03-server");
  }

  @Test
  public void testEscaping()
  {
    verify("escaped").isEqualTo("esx-val-rainer-${rainer}");
  }

  private StringAssert verify(String dsl)
  {
    return assertThat(sut.getValue(data, dsl, null));
  }
}
