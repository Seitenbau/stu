package com.seitenbau.stu.config.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.seitenbau.stu.config.EnvironmentDetector;
import com.seitenbau.stu.config.SystemTools;
import com.seitenbau.stu.config.TestConfiguration;

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
    if (detectEnvironmentForceVariable(result))
    {
      return result;
    }
    
    result = new ArrayList<String>();
    detectLowPrioity(result);
    detectJenkinsNodeTypes(result);
    detectConfigType(result);
    detectUserName(result);
    detectHostname(result);
    detectHighPrioity(result);
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
    removeDuplicates(result);
    detectProcess(result);
    return result;
  }

 protected void removeDuplicates(List<String> result)
  {
    // 1st reverse to get highes prio on top ->
    Collections.reverse(result);

    // LinkedHashSet for : Set interface, with predictable iteration
    // order.
    Set<String> clean = new LinkedHashSet<String>();
    clean.addAll(result);
    result.clear();
    result.addAll(clean);

    // 2st reverse to get highes prio on bottom again
    Collections.reverse(result);
  }

  protected void detectJenkinsNodeTypes(List<String> result)
  {
    String value = getProperty("NODE_LABELS");
    if (StringUtils.isNotBlank(value))
    {
      String[] labels = value.split(" ");
      for (String label : labels)
      {
        result.add(label);
      }
    }
  }

  protected boolean detectConfigType(List<String> result)
  {
    String value = getProperty(TestConfiguration.ENV_PROPERTY_TYPE);
    if (StringUtils.isNotBlank(value))
    {
      String[] data = value.split(":");
      if (data != null && data.length > 0)
      {
        result.addAll(Arrays.asList(data));
        return true;
      }
    }
    return false;
  }

  protected void detectProcess(List<String> result)
  {

  }

  protected void detectHighPrioity(List<String> result)
  {

  }

  protected void detectLowPrioity(List<String> result)
  {

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

  protected boolean detectEnvironmentForceVariable(List<String> result)
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
