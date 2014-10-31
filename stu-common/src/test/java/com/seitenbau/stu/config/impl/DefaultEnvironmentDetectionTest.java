package com.seitenbau.stu.config.impl;

import static org.fest.assertions.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.seitenbau.stu.config.SystemTools;

public class DefaultEnvironmentDetectionTest
{
  @Before
  public void setup() {
    SystemTools.setInternal(new SystemTools(){
      @Override
      public String getUsername()
      {
        return "TheUserIs";
      }
    });
  }
  @After
  public void reset() {
    SystemTools.reset();
  }
  @Test
  public void testEnvironmentIsLeading() throws Exception
  {
    DefaultEnvironmentDetection detector = new DefaultEnvironmentDetection()
    {
      @Override
      protected boolean detectEnvironmentForceVariable(List<String> result)
      {
        result.add("GotEnvVar");
        return true;
      }

      @Override
      protected boolean detectJUnitMax()
      {
        return true;
      }

      @Override
      protected boolean detectHostname(List<String> result)
      {
        result.add("fixHostname");
        return true;
      }
    };
    assertThat(detector.getEnvironmentIds()).hasSize(1).contains("GotEnvVar");
  }

  @Test
  public void testJUnitMaxIsDetectd() throws Exception
  {
    DefaultEnvironmentDetection detector = new DefaultEnvironmentDetection()
    {

      @Override
      protected boolean detectJUnitMax()
      {
        return true;
      }

      @Override
      protected boolean detectHostname(List<String> result)
      {
        result.add("fixHostname");
        return true;
      }
    };
    assertThat(detector.getEnvironmentIds())
          // .hasSize(4) not predictable, is 10 at the moment
          .contains("fixHostname", "fixHostname-max");
  }

  @Test
  public void testHostname() throws Exception
  {
    DefaultEnvironmentDetection detector = new DefaultEnvironmentDetection()
    {
      @Override
      protected boolean detectJUnitMax()
      {
        return false;
      }

      @Override
      protected boolean detectHostname(List<String> result)
      {
        result.add("fixHostname");
        return true;
      }
      
      @Override
      protected boolean detectUserName(List<String> result)
      {
        result.add("fixUser");
        return true;
      }
      
    };
    String[] ids = detector.getEnvironmentIds();
    assertThat(ids).contains("fixUser","fixHostname");
  }
  
  @Test
  public void testUserName() throws Exception
  {
    final Map<String,String> props = new HashMap<String,String>();
    DefaultEnvironmentDetection detector = new DefaultEnvironmentDetection()
    {
      @Override
      protected String getProperty(String property)
      {
        return props.get(property);
      }
    };
    // prepare
    String[] ids = detector.getEnvironmentIds();
    assertThat(ids).contains("TheUserIs");
  }
  
}
