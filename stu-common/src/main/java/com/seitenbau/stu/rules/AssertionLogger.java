package com.seitenbau.stu.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Lazy Console Logging on verify. <br/>
 * To make use of it, just add the following Rule to your Test class.
 * 
 * <pre>
 * &#064;Rule
 * public AssertionLogger assertLog = new AssertionLogger();
 * </pre>
 * 
 * Now it is possible to log to the system console via the method
 * {@link #log(Object...)}. In difference to {@link System.out} the
 * output is buffered and only displayed if a Test fails.
 * 
 * <br/>
 * Useful to enable debug messages that can help to track down errors but have no other benefit, e.g. URLs or raw HTTP repsonse bodies.
 * 
 */
public class AssertionLogger extends BeforeAfterRule
{

    // TODO: Use Thread Local
    protected static List<Object> _messages = new ArrayList<Object>();

    protected boolean _logOnlyOnAssertionError;

    public AssertionLogger()
    {
        this(true);
    }

    public AssertionLogger(boolean logOnlyOnAssertionError)
    {
        _logOnlyOnAssertionError = logOnlyOnAssertionError;
    }

    @Override
    protected void before(ITestMethodDescriptor descriptor) throws Throwable {
        _messages = new ArrayList<Object>();
    }
     
    @Override
    protected boolean afterError(ITestMethodDescriptor descriptor, Throwable t)
            throws Throwable {
        doLog();
        return true;
    }
     
    @Override
    protected void afterSuccess(ITestMethodDescriptor descriptor)
            throws Throwable {
        if (!_logOnlyOnAssertionError)
        {
            doLog();
        }
    }

    protected void doLog()
    {
        if (_messages == null || _messages.isEmpty())
        {
            return;
        }
        System.out.println("-- lazy Assertion log :");
        for (Object msg : _messages)
        {
            if (msg instanceof Throwable)
            {
                ((Throwable) msg).printStackTrace();
            }
            else
            {
                System.out.println(msg);
            }
        }
    }

    /**
     * Logs the given objects if a failure occurs. The String is build
     * when the call is executed. Changes done until the call is
     * executed are reflected.
     * 
     * @param messages varargs of objects.
     */
    static public void logLater(Object... messages)
    {
        _messages.add(new LogLater(messages));
    }

    /**
     * Stores given objects into a List. The List is only printed out if
     * a Test fails and the AssertionLogger rule is used. By default the
     * result of the toString() method is stored. All given objects are
     * stored/printed out as one coherent String.
     * 
     * <pre>
     *   AssertionLogger.log("url  : ", "http://localhost","/suffix");
     *   
     *   // results in output:
     *   url  : http://localhost/suffix
     * </pre>
     * 
     * @param message varargs of objects.
     */
    static public void log(Object... message)
    {
        if (message != null)
        {
            StringBuilder sb = new StringBuilder();
            for (Object msg : message)
            {
                if (msg == null)
                {
                  sb.append("null");
                }
                else if (msg instanceof Throwable)
                {
                  _messages.add(msg);
                }
                else
                {
                  sb.append(msg.toString());
                }
            }
            _messages.add(sb.toString());
          }
    }

    static class LogLater
    {

        Object[] _objects;

        public LogLater(Object... messages)
        {
            _objects = messages;
        }

        @Override
        public String toString()
        {
            if (_objects == null)
            {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (Object msg : _objects)
            {
                if (msg == null)
                {
                    sb.append("null");
                }
                else if (msg instanceof Throwable)
                {
                    sb.append(((Throwable) msg).getMessage());
                }
                else
                {
                    sb.append(msg.toString());
                }
            }
            return sb.toString();
        }
    }
}
