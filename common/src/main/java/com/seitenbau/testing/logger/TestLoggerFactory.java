package com.seitenbau.testing.logger;

public class TestLoggerFactory {
    public static Logger get(Class<?> logger) 
    {
      return LogManager.get(logger);
    }
}
