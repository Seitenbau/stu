package com.seitenbau.testing.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Lazy Console Logging on verify. <br/>
 * Einfach in der Testklasse diese Rule einh�ngen:
 * 
 * <pre>
 * &#064;Rule
 * public AssertionLogger assertLog = new AssertionLogger();
 * </pre>
 * 
 * Nun kann jederzeit �ber die {@link #log(Object...)} Methode eine
 * Loggausgabe auf die Konsole erfolgen. Anders als via {@link System.out} wird
 * die Ausgabe allerdings zwischengespeichert und nur bei einem Test Fehlschlag
 * ausgegeben.
 * 
 * <br/>
 * N�tzlich f�r debug Ausgaben die zum Finden von Fehlern praktisch sind,
 * aber sonst eigentlich recht wertlost sind. Z.b. URLs, oder RAW HTTP Response
 * Bodies.
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
     * Gibt die uebergebenen Objekte im Fehlerfall aus. Der String wird
     * ebenfalls erst dann zusammengefuegt.! Alle Aenderungen bist
     * dorthin werden also noch reflektiert!
     * 
     * @param messages
     */
    static public void logLater(Object... messages)
    {
        _messages.add(new LogLater(messages));
    }

    /**
     * Speichert alle �bergebenen Objekte in eine Liste, welche im Falle nur
     * im Falle eines Testfehlschlages ausgegeben wird. ( Hierzu muss eine
     * Instanz als JUnit Rule im Test aktiv sein )
     * 
     * Per default wird intern das Ergebnis der toString() Methode abgelegt.
     * Alle �bergebenen Objekte werden als ein zusammenh�ngende Zeichenfolge
     * gespeichert/ausgegeben.
     * 
     * 
     * 
     * <pre>
     *   AssertionLogger.log("url  : ", "http://localhost","/suffix");
     *   
     *   // f�hrt sp�ter zur Ausgabe von:
     *   url  : http://localhost/suffix
     * </pre>
     * 
     * @param message
     *            Vararg von Objects.
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
