package com.seitenbau.testing.config;

import java.util.HashMap;
import java.util.Map;
import static com.seitenbau.testing.asserts.fest.Assertions.*;
import org.junit.Test;

import junit.framework.TestCase;

public class SystemToolsTest 
{
  Map<String, String> pros = new HashMap<String, String>();

  SystemTools sut = new SystemTools()
  {
    public String getProperty(String property)
    {
      return pros.get(property);
    };
  };

  @Test
  public void getUsernameLinux()
  {
    // prepare
    pros.put("USER", "LName");
    // execute
    String username = sut.getUsername();
    // verify
    assertThat(username).isEqualTo("LName");
  }

  @Test
  public void getUsernameWindows()
  {
    // prepare
    pros.put("USERNAME", "WName");
    // execute
    String username = sut.getUsername();
    // verify
    assertThat(username).isEqualTo("WName");
  }
}
