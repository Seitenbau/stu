package com.seitenbau.testing.logger;


public abstract class LogManager
{

  protected static Levels _current = Levels.WARN;

  protected static boolean isDefault = true;

  public static Logger get(Class<?> logger)
  {
    return new ConsoleLoggerImpl(cleanup(logger.getCanonicalName()));
  }

  protected static String cleanup(String name)
  {
    if(name==null) {
      return "";
    }
    if(name.startsWith("com.seitenbau.testing.")) {
      return "c.s.t." + name.substring(22);
    }
    if(name.startsWith("com.seitenbau.")) {
      return "c.s." + name.substring(14);
    }
    return name;
  }

  public static void setLevel(Levels currentLevel)
  {
    if (currentLevel == null)
    {
      throw new IllegalArgumentException();
    }
    isDefault = false;
    _current = currentLevel;
  }

  public enum Levels
  {
    TRACE, DEBUG, INFO, WARN, ERROR, FATAL
  }

  public static boolean isActive(Levels level, String name)
  {
    return level.ordinal() >= _current.ordinal();
  }

  public static Levels getLevel()
  {
    return _current;
  }

  /**
   * Changes the log level temporarily in case it was never changed before.
   * to reset to old state call reset() on the returned object
   */
  public static LogBlock setTemporarily(Levels newLevel)
  {
    if (isDefault)
    {
      return new LogBlock(newLevel);
    }
    return new LogBlock();
  }

  public static class LogBlock
  {
    Levels oldLevel;

    public LogBlock() {}
    public LogBlock(Levels newLevel)
    {
      if (newLevel != null)
      {
        oldLevel = getLevel();
        setLevel(newLevel);
      }
    }

    public void rollback()
    {
      if (oldLevel != null)
      {
        setLevel(oldLevel);
        isDefault = true;
      }
    }

  }

}
