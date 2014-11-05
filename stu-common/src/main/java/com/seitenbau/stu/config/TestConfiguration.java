package com.seitenbau.stu.config;

import com.seitenbau.stu.config.ValueProvider.ValuesPrintable;
import com.seitenbau.stu.config.impl.NonStaticFieldException;
import com.seitenbau.stu.logger.LogManager;
import com.seitenbau.stu.logger.LogManager.Levels;

public class TestConfiguration
{
  static TestConfigurationIntern implementation;

  public static final String ENV_PROPERTY_NAME = "SB_CONFIG_TARGET";
  public static final String ENV_PROPERTY_TYPE = "SB_CONFIG_TYPE";

  synchronized static protected TestConfigurationIntern getIntern()
  {
    if (implementation == null)
    {
      implementation = new TestConfigurationIntern();
    }
    return implementation;
  }

  public static void setFastFail(boolean fastFail)
  {
    getIntern().setFastFail(fastFail);
  }

  /**
   * Load configuration from properties files based on the current
   * environment.
   * 
   * @param target can be a Object or a Class with static fields
   * @return
   * 
   */
  public static ValueProvider load(Object target)
  {
    return load(target, false, null);
  }

  /**
   * Load configuration from properties files based on the current
   * environment.
   * 
   * @param Class with static fields
   * @return configuration values.
   * 
   * @throws NonStaticFieldException thrown when the class has a non
   *         static field with the {@link StoredProperty} annotation.
   * 
   */
  public static ValueProvider load(Class<?> target)
  {
    return load(target, false, null);
  }
  

  /**
   * Load configuration from properties files based on the current
   * environment.
   * 
   * @param target can be a Object or a Class with static fields
   * @return
   * 
   */
  public static ValueProvider load(Object target, boolean print, String type)
  {
    ValueProvider provider = getIntern().load(target, print, type);
    Boolean flag = provider.getBoolean("_debug.config.print");
    if (flag != null && flag && provider instanceof ValuesPrintable)
    {
      ((ValuesPrintable) provider).printValues();
    }
    String level = provider.getString("_debug.loglevel");
    if (level != null && !level.isEmpty())
    {
      Levels newLevel = Levels.valueOf(level.toUpperCase());
      LogManager.setLevel(newLevel);
    }

    return provider;
  }

  public static ValueProvider getInstance()
  {
    return getConfig(true);
  }

  static ValueProvider getConfig(boolean allowAutoload)
  {
    return getIntern().getConfig(null, allowAutoload);
  }

  /**
   * Get the value for the given key or {@code null}
   * @param key
   * @return
   */
  public static String getString(String key)
  {
    return getConfig(true).getString(key);
  }

  public static String getEntry(String nameOfMap, String key, String defaultValue)
  {
    return getConfig(true).getMapEntry(nameOfMap, key, defaultValue);
  }

  /**
   * Get the value for the given key or {@code null}
   * @param key
   * @return
   */
  public static Integer getInteger(String key)
  {
    try
    {
      return getConfig(true).getInteger(key);
    }
    catch (NumberFormatException ne)
    {
      throw new RuntimeException("key was = " + key, ne);
    }
  }

  public static boolean getBoolean(String key)
  {
    return getConfig(true).getBoolean(key);
  }

  /**
   * Set the Detector for the environment
   * @param environmentDetector
   */
  public static void setEnvironmentProvider(EnvironmentDetector environmentDetector)
  {
    getIntern().setEnvironmentProvider(environmentDetector);
  }

  /**
   * Set the Detector for the environment
   * @param environmentDetector
   */
  public static void addValueProcessor(ValueProcessor processor)
  {
    getIntern().setValueProcessor(processor);
  }

  /**
   * Resets internal cache.
   */
  public static void reset()
  {
    getIntern().reset();
    implementation = null;
  }

}
