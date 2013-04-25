package com.seitenbau.testing.logger;

import java.io.PrintStream;

import com.seitenbau.testing.logger.LogManager.Levels;

public class ConsoleLoggerImpl extends Logger
{
    String _name;

    public ConsoleLoggerImpl(String name)
    {
        _name = name;
    }

    @Override
    public void trace(Object... messages)
    {
        print(Levels.TRACE, messages);
    }

    @Override
    public void debug(Object... messages)
    {
        print(Levels.DEBUG, messages);
    }

    @Override
    public void info(Object... messages)
    {
        print(Levels.INFO, messages);
    }

    @Override
    public void warn(Object... messages)
    {
        print(Levels.WARN, messages);
    }

    @Override
    public void error(Object... messages)
    {
        print(Levels.ERROR, messages);
    }

    @Override
    public void fatal(Object... messages)
    {
        print(Levels.FATAL, messages);
    }

    synchronized void print(Levels level, Object[] messages)
    {
        if (LogManager.isActive(level, _name))
        {
            PrintStream steam = getOutputStream();
            steam.print(_name);
            steam.print(" [");
            steam.print(level);
            steam.print("]: ");
            for (Object msg : messages)
            {
                steam.print(msg);
            }
            steam.println();
        }
    }

    /**
     * 4 Testing
     */
    PrintStream getOutputStream()
    {
        return System.out;
    }

    String getName() {
        return _name;
    }
}
