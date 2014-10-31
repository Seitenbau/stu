package com.seitenbau.stu.logger;

import static com.seitenbau.stu.asserts.fest.Assertions.*;
import static org.fest.assertions.Assertions.*;

import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.seitenbau.stu.logger.LogManager.Levels;
import com.seitenbau.stu.util.StringOutputStream;

public class ConsoleLoggerImplTest
{
    private static final String NL = System.getProperty("line.separator");

    StringOutputStream stream = new StringOutputStream();

    ConsoleLoggerImpl sut = new ConsoleLoggerImpl("theName")
    {
        PrintStream getOutputStream()
        {
            return new PrintStream(stream);
        };
    };

    Object value = new Object() {
        public String toString() {
            return "anObject";
        }
    };

    String text = "textanObjectnull";

    @Before
    public void setup()
    {
        LogManager.setLevel(Levels.TRACE);
    }

    @After
    public void cleanup()
    {
        LogManager.reset();
    }

    @Test
    public void other()
    {
        assertThat(new ConsoleLoggerImpl("theName").getOutputStream()).isSameAs(System.out);
    }

    @Test
    public void testTrace()
    {
        sut.trace();
        assertThat(stream.getString()).isEqualTo("theName [TRACE]: " + NL);
    }

    @Test
    public void testInfo()
    {
        sut.info();
        assertThat(stream.getString()).isEqualTo("theName [INFO]: " + NL);
    }

    @Test
    public void testDebug()
    {
        sut.debug();
        assertThat(stream.getString()).isEqualTo("theName [DEBUG]: " + NL);
    }

    @Test
    public void testWarn()
    {
        sut.warn();
        assertThat(stream.getString()).isEqualTo("theName [WARN]: " + NL);
    }

    @Test
    public void testError()
    {
        sut.error();
        assertThat(stream.getString()).isEqualTo("theName [ERROR]: " + NL);
    }

    @Test
    public void testFatal()
    {
        sut.fatal();
        assertThat(stream.getString()).isEqualTo("theName [FATAL]: " + NL);
    }

    @Test
    public void testTraceWithObjects()
    {
        sut.trace("text", value, null);
        assertThat(stream.getString()).isEqualTo("theName [TRACE]: " + text + NL);
    }

    @Test
    public void testInfoWithObjects()
    {
        sut.info("text", value, null);
        assertThat(stream.getString()).isEqualTo("theName [INFO]: " + text + NL);
    }

    @Test
    public void testDebugWithObjects()
    {
        sut.debug("text", value, null);
        assertThat(stream.getString()).isEqualTo("theName [DEBUG]: " + text + NL);
    }

    @Test
    public void testWarnWithObjects()
    {
        sut.warn("text", value, null);
        assertThat(stream.getString()).isEqualTo("theName [WARN]: " + text + NL);
    }

    @Test
    public void testErrorWithObjects()
    {
        sut.error("text", value, null);
        assertThat(stream.getString()).isEqualTo("theName [ERROR]: " + text + NL);
    }

    @Test
    public void testFatalWithObjects()
    {
        sut.fatal("text", value, null);
        assertThat(stream.getString()).isEqualTo("theName [FATAL]: " + text + NL);
    }

    @Test
    public void testDebugStillOn()
    {
        LogManager.setLevel(Levels.DEBUG);
        sut.debug("text", value, null);
        assertThat(stream.getString()).isEqualTo("theName [DEBUG]: " + text + NL);
    }

    @Test
    public void testDebugStillOff()
    {
        LogManager.setLevel(Levels.WARN);
        sut.debug("text", value, null);
        assertThat(stream.getString()).isEmpty();
    }

}
