package com.seitenbau.stu.dsl.requirement;

import static com.seitenbau.stu.asserts.fest.Assertions.assertThat;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.seitenbau.stu.dsl.requirement.AbstractKeyValueDetector;
import com.seitenbau.stu.dsl.requirement.RequirementDsl.RequirementState;

public class AbstractKeyValueDetectorTest
{
  boolean _state;

  AbstractKeyValueDetector sut = new AbstractKeyValueDetector()
  {
    @Override
    protected boolean readState(String key, String value)
    {
      return _state;
    }
  };

  @Test
  public void notparsable()
  {
    // execute
    sut.initWithParameter("");

    // verify
    assertThat(sut._key).isNull();
    assertThat(sut._value).isNull();
    assertThat(sut._not).isFalse();
  }

  @Test
  public void nill()
  {
    // execute
    sut.initWithParameter(null);

    // verify
    assertThat(sut._key).isNull();
    assertThat(sut._value).isNull();
    assertThat(sut._not).isFalse();
  }

  @Test
  public void not()
  {
    // execute
    sut.initWithParameter("!");

    // verify
    assertThat(sut._key).isEmpty();
    assertThat(sut._value).isNull();
    assertThat(sut._not).isTrue();
  }

  @Test
  public void justAKey()
  {
    // execute
    sut.initWithParameter("key");

    // verify
    assertThat(sut._key).isEqualTo("key");
    assertThat(sut._value).isNull();
    assertThat(sut._not).isFalse();
  }

  @Test
  public void justNotAKey()
  {
    // execute
    sut.initWithParameter("!key");

    // verify
    assertThat(sut._key).isEqualTo("key");
    assertThat(sut._value).isNull();
    assertThat(sut._not).isTrue();
  }

  @Test
  public void keyWithValue()
  {
    // execute
    sut.initWithParameter("key=v");

    // verify
    assertThat(sut._key).isEqualTo("key");
    assertThat(sut._value).isEqualTo("v");
    assertThat(sut._not).isFalse();
  }

  @Test
  public void keyNotWithValue()
  {
    // execute
    sut.initWithParameter("!key=v");

    // verify
    assertThat(sut._key).isEqualTo("key");
    assertThat(sut._value).isEqualTo("v");
    assertThat(sut._not).isTrue();
  }

  @Test
  public void keyNotWithNotValue()
  {
    // execute
    sut.initWithParameter("!key=!v");

    // verify
    assertThat(sut._key).isEqualTo("key");
    assertThat(sut._value).isEqualTo("v");
    assertThat(sut._not).isTrue();
  }

  @Test
  public void keyWithNotValue()
  {
    // execute
    sut.initWithParameter("key=!value is hot!");

    // verify
    assertThat(sut._key).isEqualTo("key");
    assertThat(sut._value).isEqualTo("value is hot!");
    assertThat(sut._not).isTrue();
  }

  @Test
  public void isActive()
  {
    assertThat(sut.isActive(false, false)).isFalse();
    assertThat(sut.isActive(false, true)).isTrue();
    assertThat(sut.isActive(true, false)).isTrue();
    assertThat(sut.isActive(true, true)).isFalse();
  }

  @Test
  public void getState()
  {
    assertThat(sut.getState(true)).isEqualTo(RequirementState.IMPLEMENTED);
    assertThat(sut.getState(false)).isEqualTo(RequirementState.NOT_IMPLEMENTED);

    // same again
    _state = true;
    assertThat(sut.getState()).isEqualTo(RequirementState.IMPLEMENTED);
    _state = false;
    assertThat(sut.getState(false)).isEqualTo(RequirementState.NOT_IMPLEMENTED);
  }
}
