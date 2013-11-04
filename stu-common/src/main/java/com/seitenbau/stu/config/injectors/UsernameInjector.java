package com.seitenbau.stu.config.injectors;

import java.util.Properties;

import com.seitenbau.stu.config.SystemTools;

public class UsernameInjector implements ValueInjector
{

  public void injectValues(Properties prop)
  {
    String username = SystemTools.get().getUsername();
    if (username != null)
    {
      prop.put("_username", username);
    }
  }

}
