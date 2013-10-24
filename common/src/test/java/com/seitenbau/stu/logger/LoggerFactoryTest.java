package com.seitenbau.stu.logger;

import static org.fest.assertions.Assertions.*;

import org.junit.Test;

import com.seitenbau.stu.logger.ConsoleLoggerImpl;
import com.seitenbau.stu.logger.Logger;
import com.seitenbau.stu.logger.TestLoggerFactory;

public class LoggerFactoryTest {
    @Test
    public void getTest() {
        Logger logger = TestLoggerFactory.get(LoggerFactoryTest.class);
        assertThat(logger).isInstanceOf(ConsoleLoggerImpl.class);
        ConsoleLoggerImpl log = (ConsoleLoggerImpl) logger;
        assertThat(log.getName()).isEqualTo("c.s.s.logger.LoggerFactoryTest");
    }
    
    @Test
    public void justForTheMetric() {
      new LoggerFactoryTest().toString();
    }
}
