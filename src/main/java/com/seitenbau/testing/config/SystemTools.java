package com.seitenbau.testing.config;

public class SystemTools
{
  protected static SystemTools _instance;

  protected SystemTools(){}
  /**
   * Reads the current username from the environment
   * @return The current Username or null if non detected
   */
  public String getUsername()
  {
    String user = getProperty("USER");
    if (user != null)
    {
      return user;
    }
    String username = getProperty("USERNAME");
    if (username != null)
    {
      return username;
    }
    return null;
  }

  /**
   * Get the value of a System property, or Environment property
   * @param property to read
   * @return the value of the property or null
   */
  public String getProperty(String property)
  {
    String value = System.getProperty(property);
    if (value != null)
    {
      return value;
    }
    return System.getenv(property);
  }

  /**
   * Get current instance
   * @return
   */
  public static SystemTools get()
  {
    if (_instance == null)
    {
      _instance = new SystemTools();
    }
    return _instance;
  }

  @Deprecated
  /** 
   * Do only use this if you know you are doing!
   * <br/>
   * Call reset() to cleanup this internal manipulation!
   */
  public static void setInternal(SystemTools instance)
  {
    _instance = instance;
  }

  @Deprecated
  /**
   * Do only use this if you know you are doing! 
   */
  public static void reset()
  {
    _instance = null;
  }
}
