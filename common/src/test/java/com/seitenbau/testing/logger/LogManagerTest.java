package com.seitenbau.testing.logger;

import org.junit.Test;

import com.seitenbau.testing.logger.LogManager.Levels;

import static com.seitenbau.testing.asserts.fest.Assertions.*;

public class LogManagerTest
{
    @Test
    public void testGet()
    {
        Logger sut = LogManager.get(LogManager.class);
        assertThat(sut).isInstanceOf(ConsoleLoggerImpl.class);
        ConsoleLoggerImpl log = (ConsoleLoggerImpl) sut;
        assertThat(log.getName()).isEqualTo("c.s.t.logger.LogManager");
    }
    
    @Test
    public void levels() {
        assertThat(Levels.TRACE.ordinal()).isEqualTo(0);
        assertThat(Levels.DEBUG.ordinal()).isEqualTo(1);
        assertThat(Levels.INFO.ordinal()).isEqualTo(2);
        assertThat(Levels.WARN.ordinal()).isEqualTo(3);
        assertThat(Levels.ERROR.ordinal()).isEqualTo(4);
        assertThat(Levels.FATAL.ordinal()).isEqualTo(5);
        assertThat(Levels.values()).hasSize(6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLevel_Null()
    {
        LogManager.setLevel(null);
    }

    @Test
    public void getLevel_default()
    {
        assertThat(LogManager.getLevel()).isSameAs(Levels.WARN);
    }

    @Test
    public void testLevelTrace()
    {
        LogManager.setLevel(Levels.TRACE);
        assertThat(LogManager.isActive(Levels.TRACE, "")).isTrue();
        assertThat(LogManager.isActive(Levels.DEBUG, "")).isTrue();
        assertThat(LogManager.isActive(Levels.INFO, "")).isTrue();
        assertThat(LogManager.isActive(Levels.WARN, "")).isTrue();
        assertThat(LogManager.isActive(Levels.ERROR, "")).isTrue();
        assertThat(LogManager.isActive(Levels.FATAL, "")).isTrue();
    }

    @Test
    public void testLevelDebug()
    {
        LogManager.setLevel(Levels.DEBUG);
        assertThat(LogManager.isActive(Levels.TRACE, "")).isFalse();
        assertThat(LogManager.isActive(Levels.DEBUG, "")).isTrue();
        assertThat(LogManager.isActive(Levels.INFO, "")).isTrue();
        assertThat(LogManager.isActive(Levels.WARN, "")).isTrue();
        assertThat(LogManager.isActive(Levels.ERROR, "")).isTrue();
        assertThat(LogManager.isActive(Levels.FATAL, "")).isTrue();
    }

    @Test
    public void testLevelInfo()
    {
        LogManager.setLevel(Levels.INFO);
        assertThat(LogManager.isActive(Levels.TRACE, "")).isFalse();
        assertThat(LogManager.isActive(Levels.DEBUG, "")).isFalse();
        assertThat(LogManager.isActive(Levels.INFO, "")).isTrue();
        assertThat(LogManager.isActive(Levels.WARN, "")).isTrue();
        assertThat(LogManager.isActive(Levels.ERROR, "")).isTrue();
        assertThat(LogManager.isActive(Levels.FATAL, "")).isTrue();
    }

    @Test
    public void testLevelWarn()
    {
        LogManager.setLevel(Levels.WARN);
        assertThat(LogManager.isActive(Levels.TRACE, "")).isFalse();
        assertThat(LogManager.isActive(Levels.DEBUG, "")).isFalse();
        assertThat(LogManager.isActive(Levels.INFO, "")).isFalse();
        assertThat(LogManager.isActive(Levels.WARN, "")).isTrue();
        assertThat(LogManager.isActive(Levels.ERROR, "")).isTrue();
        assertThat(LogManager.isActive(Levels.FATAL, "")).isTrue();
    }

    @Test
    public void testLevelError()
    {
        LogManager.setLevel(Levels.ERROR);
        assertThat(LogManager.isActive(Levels.TRACE, "")).isFalse();
        assertThat(LogManager.isActive(Levels.DEBUG, "")).isFalse();
        assertThat(LogManager.isActive(Levels.INFO, "")).isFalse();
        assertThat(LogManager.isActive(Levels.WARN, "")).isFalse();
        assertThat(LogManager.isActive(Levels.ERROR, "")).isTrue();
        assertThat(LogManager.isActive(Levels.FATAL, "")).isTrue();
    }

    @Test
    public void testLevelFatal()
    {
        LogManager.setLevel(Levels.FATAL);
        assertThat(LogManager.isActive(Levels.TRACE, "")).isFalse();
        assertThat(LogManager.isActive(Levels.DEBUG, "")).isFalse();
        assertThat(LogManager.isActive(Levels.INFO, "")).isFalse();
        assertThat(LogManager.isActive(Levels.WARN, "")).isFalse();
        assertThat(LogManager.isActive(Levels.ERROR, "")).isFalse();
        assertThat(LogManager.isActive(Levels.FATAL, "")).isTrue();
    }
}
