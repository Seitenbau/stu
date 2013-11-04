package com.seitenbau.stu.database.validator;

import static org.fest.assertions.Assertions.*;

import org.junit.Test;

import com.seitenbau.stu.database.validator.ValueSet;

public class ValueSetTest
{
  String VAR = "${var}";

  String VALUE = "2000";

  @Test
  public void test_getter()
  {
    ValueSet sut = new ValueSet(VAR, VALUE);
    assertThat(sut.getMarkerString()).isSameAs(VAR);
    assertThat(sut.getReplacementObject()).isSameAs(VALUE);
  }

  @Test
  public void test_getter_VarNull()
  {
    ValueSet sut = new ValueSet(null, VALUE);
    assertThat(sut.getMarkerString()).isNull();
    assertThat(sut.getReplacementObject()).isSameAs(VALUE);
  }

  @Test
  public void test_getter_ValNull()
  {
    ValueSet sut = new ValueSet(VAR, null);
    assertThat(sut.getMarkerString()).isSameAs(VAR);
    assertThat(sut.getReplacementObject()).isNull();
  }

  @Test
  public void test_setMarkerString()
  {
    // prepare
    ValueSet sut = new ValueSet(VAR, VALUE);
    // execute
    sut.setMarkerString("Rainer");
    // verify
    assertThat(sut.getMarkerString()).isEqualTo("Rainer");
  }

}
