package com.seitenbau.stu.logger;

@Deprecated
public class TestLogger
{
  public static void warn(String... messages)
  {
    print("WARN ", messages);
  }

  public static void error(String... messages)
  {
    print("ERROR", messages);
  }

  public static void debug(String... messages)
  {
    print("DEBUG", messages);
  }

  public static void info(String... messages)
  {
    print("INFO ", messages);
  }

  private static void print(String level, String[] messages)
  {
    System.out.print(level);
    System.out.print(" : ");
    for (String msg : messages)
    {
      System.out.print(msg);
    }
    System.out.println();
  }
}
