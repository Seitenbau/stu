package com.seitenbau.stu.logger;

public class TestLoggerFactory {
    public static Logger get(Class<?> logger) 
    {
      return LogManager.get(logger);
    }
}
