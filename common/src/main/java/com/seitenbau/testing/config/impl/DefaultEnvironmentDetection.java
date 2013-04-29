package com.seitenbau.testing.config.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.seitenbau.testing.config.EnvironmentDetector;
import com.seitenbau.testing.config.SystemTools;
import com.seitenbau.testing.config.TestConfiguration;

public class DefaultEnvironmentDetection implements EnvironmentDetector
{

  public String[] getEnvironmentIds()
  {
    List<String> result = createEnvironmentIds();
    if (result == null)
    {
      return null;
    }
    return result.toArray(new String[] {});
  }

  private List<String> createEnvironmentIds()
  {
    List<String> result = new ArrayList<String>();
    if (detectEnvironmentVariable(result))
    {
      return result;
    }

    result = new ArrayList<String>();
    detectUserName(result);
    detectHostname(result);
    if (detectJUnitMax())
    {
      for (String env : new ArrayList<String>(result))
      {
        result.add(env + "-max");
      }
    }
    if (detectInfinitest())
    {
      for (String env : new ArrayList<String>(result))
      {
        result.add(env + "-infini");
      }
    }
    return result;
  }

  protected boolean detectUserName(List<String> result)
  {
    String user = SystemTools.get().getUsername();
    if (user != null)
    {
      result.add(user);
      return true;
    }
    return false;
  }

  protected boolean detectJUnitMax()
  {
    // org.junit.experimental.max.MaxCore
    StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    for (StackTraceElement call : trace)
    {
      if (call.getClassName().endsWith("MaxCore"))
      {
        return true;
      }
    }
    return false;
  }
  
  protected boolean detectInfinitest()
  {
    StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    for (StackTraceElement call : trace)
    {
      if (call.getClassName().startsWith("org.infinitest.testrunner"))
      {
        return true;
      }
    }
    return false;
  }

  protected boolean detectHostname(List<String> result)
  {
    try
    {
      String computername = InetAddress.getLocalHost().getHostName();
      result.add(computername);
      return true;
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
      return false;
    }
  }

  protected boolean detectEnvironmentVariable(List<String> result)
  {
    String value = getProperty(TestConfiguration.ENV_PROPERTY_NAME);
    if (StringUtils.isNotBlank(value))
    {
      result.add(value);
      return true;
    }
    return false;
  }

  protected String getProperty(String property)
  {
    String value = System.getProperty(property);
    if (value != null)
    {
      return value;
    }
    return System.getenv(property);
  }
}
