package com.seitenbau.stu.expression4j;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import com.seitenbau.stu.expression4j.TestEvents;
import com.seitenbau.stu.expression4j.TestEvents.Listeners;
import com.seitenbau.stu.expression4j.TestEvents.TestFlowListener;
import com.seitenbau.stu.mockito.MockitoRule;

public class TestEventsTest
{
  @Before
  public void reset()
  {
    Listeners.listeners = new ArrayList<TestEvents.TestFlowListener>();
  }

  @Rule
  public MockitoRule mockito = new MockitoRule();

  @Mock TestFlowListener listener1;
  @Mock TestFlowListener listener2;

  @Test
  public void invokeEmpty()
  {
    TestEvents.prepare();
    TestEvents.execute();
    TestEvents.verify();
  }

  @Test
  public void invokeOnce()
  {
    TestEvents.Listeners.add(listener1);
    
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-prepare");
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-execution");
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-verify");
    
    TestEvents.prepare();
    TestEvents.execute();
    TestEvents.verify();
    
    verify(listener1, times(1)).onTestFlowEvent("events.rules:begin-of-prepare");
    verify(listener1, times(1)).onTestFlowEvent("events.rules:begin-of-execution");
    verify(listener1, times(1)).onTestFlowEvent("events.rules:begin-of-verify");

  }
  
  @Test
  public void invokeWithGenericMethod()
  {
    TestEvents.Listeners.add(listener1);
    
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-prepare");
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-execution");
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-verify");
    
    TestEvents.notify( TestEvents.EVENT_VERIFY);
    TestEvents.notify( TestEvents.EVENT_PREPARE);
    TestEvents.notify( TestEvents.EVENT_EXECUTE);
    
    verify(listener1, times(1)).onTestFlowEvent("events.rules:begin-of-prepare");
    verify(listener1, times(1)).onTestFlowEvent("events.rules:begin-of-execution");
    verify(listener1, times(1)).onTestFlowEvent("events.rules:begin-of-verify");
    
  }
  
  @Test
  public void invokeWithObject()
  {
    Object object = new Object();
    TestEvents.Listeners.add(listener1);
    
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-prepare");
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-execution");
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-verify");
    verify(listener1, never()).onTestFlowEvent(object);
    
    TestEvents.notify( object );
    
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-prepare");
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-execution");
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-verify");
    verify(listener1, times(1)).onTestFlowEvent(object);
    
  }
  
  @Test
  public void invokeOnceAndRemoveWorks()
  {
    // prepare
    TestEvents.Listeners.add(listener1);
    TestEvents.Listeners.add(listener2);
    
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-prepare");
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-execution");
    verify(listener1, never()).onTestFlowEvent("events.rules:begin-of-verify");
    verify(listener2, never()).onTestFlowEvent("events.rules:begin-of-prepare");
    verify(listener2, never()).onTestFlowEvent("events.rules:begin-of-execution");
    verify(listener2, never()).onTestFlowEvent("events.rules:begin-of-verify");
    
    TestEvents.prepare();
    TestEvents.execute();
    TestEvents.verify();
    
    verify(listener1, times(1)).onTestFlowEvent("events.rules:begin-of-prepare");
    verify(listener1, times(1)).onTestFlowEvent("events.rules:begin-of-execution");
    verify(listener1, times(1)).onTestFlowEvent("events.rules:begin-of-verify");
    verify(listener2, times(1)).onTestFlowEvent("events.rules:begin-of-prepare");
    verify(listener2, times(1)).onTestFlowEvent("events.rules:begin-of-execution");
    verify(listener2, times(1)).onTestFlowEvent("events.rules:begin-of-verify");
    
    // execute
    
    TestEvents.Listeners.remove(listener2);
    TestEvents.prepare();
    TestEvents.execute();
    TestEvents.verify();
    
    verify(listener1, times(2)).onTestFlowEvent("events.rules:begin-of-prepare");
    verify(listener1, times(2)).onTestFlowEvent("events.rules:begin-of-execution");
    verify(listener1, times(2)).onTestFlowEvent("events.rules:begin-of-verify");
    verify(listener2, times(1)).onTestFlowEvent("events.rules:begin-of-prepare");
    verify(listener2, times(1)).onTestFlowEvent("events.rules:begin-of-execution");
    verify(listener2, times(1)).onTestFlowEvent("events.rules:begin-of-verify");

  }
}
