package com.seitenbau.stu.dsl.requirement;

import static org.fest.assertions.Assertions.*;

import org.junit.Test;

import com.seitenbau.stu.dsl.requirement.RequirementDsl.RequirementState;

public class DetectorFactoryTest
{
  @Test(expected = RuntimeException.class)
  public void checkNoClassFound()
  {
    DetectorFactory.create("rainer.test:value=12");
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalargument()
  {
    DetectorFactory.create("1:2:3:4");
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalargument_empty()
  {
    DetectorFactory.create("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalargument_Null()
  {
    DetectorFactory.create(null);
  }

  @Test
  public void checkCreateInstance()
  {
    SampleDetector detector = (SampleDetector) DetectorFactory
        .create(SampleDetector.class.getName() + ":value=12");
    assertThat(detector).isNotNull();
    assertThat(detector.getParameters()).isEqualTo("value=12");
  }

  @Test
  public void checkCreateInstanceWithoutParameter()
  {
    SampleDetector detector = (SampleDetector) DetectorFactory
        .create(SampleDetector.class.getName() + "");
    assertThat(detector).isNotNull();
    assertThat(detector.getParameters()).isNull();
  }

  public static class SampleDetector extends AbstractDetector
  {
    public RequirementState getState()
    {
      return RequirementState.IMPLEMENTED;
    }
  }
}
