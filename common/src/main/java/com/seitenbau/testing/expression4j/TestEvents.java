package com.seitenbau.testing.expression4j;

import java.util.ArrayList;
import java.util.List;

import com.seitenbau.testing.rules.BeforeAfterRule;

public class TestEvents
{
  public static final String EVENT_PREPARE = "events.rules:begin-of-prepare";
  public static final String EVENT_EXECUTE = "events.rules:begin-of-execution";
  public static final String EVENT_VERIFY = "events.rules:begin-of-verify";

  public static class Listeners {
    static List<TestFlowListener> listeners = new ArrayList<TestFlowListener>();
    public static void add(TestFlowListener listener) {
      listeners.add(listener);
    }
    public static void remove(TestFlowListener listener) {
      listeners.remove(listener);
    }
    public static void notifyAll(Object event)
    {
      ArrayList<TestFlowListener> copy = new ArrayList<TestFlowListener>();
      copy.addAll(listeners);
      for(TestFlowListener listener : copy) {
        listener.onTestFlowEvent(event);
      }
    }
  }
  
  public interface TestFlowListener {
    void onTestFlowEvent(Object event);
  }
  /**
   * Convenient Method to signal all rules that the Test is now going
   * to get executed. Supported by Rules based on
   * {@link BeforeAfterRule}. This could be used to get mocks into
   * replay mode.
   */
  public static void execute()
  {
    Listeners.notifyAll(EVENT_EXECUTE);
  }

  /**
   * Convenient Method to signal all rules that the Test is now just do preparation stuff.
   * Supported by Rules based on {@link BeforeAfterRule}. This could be used to reset mock objects.
   */
  public static void prepare()
  {
    Listeners.notifyAll(EVENT_PREPARE);
  }

  /**
   * Convenient Method to signal all rules that the Test has now been executed.
   * Supported by Rules based on {@link BeforeAfterRule}. This could be used to do early verifies.
   * Eg. no errors occured in an rest call, so the actuall error could be display nicer
   */
  public static void verify()
  {
    Listeners.notifyAll(EVENT_VERIFY);
  }

  public static void notify(Object event)
  {
    Listeners.notifyAll(event);
  }

}
