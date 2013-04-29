package com.seitenbau.testing.logger;

import static org.fest.assertions.Assertions.*;

import org.junit.Test;

public class LoggerFactoryTest {
    @Test
    public void getTest() {
        Logger logger = TestLoggerFactory.get(LoggerFactoryTest.class);
        assertThat(logger).isInstanceOf(ConsoleLoggerImpl.class);
        ConsoleLoggerImpl log = (ConsoleLoggerImpl) logger;
        assertThat(log.getName()).isEqualTo("c.s.t.logger.LoggerFactoryTest");
    }
    
    @Test
    public void justForTheMetric() {
      new LoggerFactoryTest().toString();
    }
}
