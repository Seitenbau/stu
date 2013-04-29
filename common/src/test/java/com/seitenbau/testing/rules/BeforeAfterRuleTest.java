package com.seitenbau.testing.rules;

import java.util.concurrent.atomic.AtomicInteger;

import static com.seitenbau.testing.asserts.fest.Assertions.*;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.seitenbau.testing.expression4j.TestEvents;

public class BeforeAfterRuleTest 
{
    @Test
    public void testJustCall() throws Throwable {
        FrameworkMethod method = new FrameworkMethod(getClass().getMethods()[0]);
        Statement base = new Statement() {
            @Override
            public void evaluate() throws Throwable {

            }
        };
        new BeforeAfterRule().apply(base, method, this).evaluate();
    }
    
    @Test
    public void testEventHandling() throws Throwable {
      FrameworkMethod method = new FrameworkMethod(getClass().getMethods()[0]);
      final AtomicInteger wasPrepared = new AtomicInteger();
      final AtomicInteger wasExecuted = new AtomicInteger();
      final AtomicInteger wasVerified = new AtomicInteger();
      Statement base = new Statement() {
        @Override
        public void evaluate() throws Throwable {
          TestEvents.execute();
        }
      };
      BeforeAfterRule myRule = new BeforeAfterRule() 
      {
        protected void onTestFlowPrepare() { wasPrepared.incrementAndGet();}
        protected void onTestFlowExecute() { wasExecuted.incrementAndGet();}
        protected void onTestFlowVerify()  { wasVerified.incrementAndGet();}
      };
      Statement stmt = myRule.apply(base, method, this);
      assertThat(wasPrepared).isEqualTo(0);
      assertThat(wasExecuted).isEqualTo(0);
      assertThat(wasVerified).isEqualTo(0);
      stmt.evaluate();
      assertThat(wasPrepared).isEqualTo(0);
      assertThat(wasExecuted).isEqualTo(1);
      assertThat(wasVerified).isEqualTo(0);
    }

    @Test(expected = MyException.class)
    public void testWithThrow() throws Throwable {
        FrameworkMethod method = new FrameworkMethod(getClass().getMethods()[0]);
        Statement base = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                throw new MyException();
            }
        };
        new BeforeAfterRule().apply(base, method, this).evaluate();
    }

    @Test
    public void testWithThrowSuppress() throws Throwable {
        FrameworkMethod method = new FrameworkMethod(getClass().getMethods()[0]);
        Statement base = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                throw new MyException();
            }
        };
        new BeforeAfterRule() {
            @Override
            protected boolean afterError(ITestMethodDescriptor descriptor,
                    Throwable t) throws Throwable {
                return false;
            }
        }.apply(base, method, this).evaluate();
    }

    @Test(expected = MyException2.class)
    public void testWithReThrow() throws Throwable {
        FrameworkMethod method = new FrameworkMethod(getClass().getMethods()[0]);
        Statement base = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                throw new MyException();
            }
        };
        new BeforeAfterRule() {
            @Override
            protected boolean afterError(ITestMethodDescriptor descriptor,
                    Throwable t) throws Throwable {
                throw new MyException2();
            }
        }.apply(base, method, this).evaluate();
    }

    @Test(expected = MyException2.class)
    public void testWithReThrowSuccess() throws Throwable {
        FrameworkMethod method = new FrameworkMethod(getClass().getMethods()[0]);
        Statement base = new Statement() {
            @Override
            public void evaluate() throws Throwable {
            }
        };
        new BeforeAfterRule() {
            protected void afterSuccess(ITestMethodDescriptor descriptor)
                    throws Throwable {
                throw new MyException2();
            };
        }.apply(base, method, this).evaluate();
    }

    @Test(expected = MyException2.class)
    public void testWithReThrowFinal() throws Throwable {
        FrameworkMethod method = new FrameworkMethod(getClass().getMethods()[0]);
        Statement base = new Statement() {
            @Override
            public void evaluate() throws Throwable {
            }
        };
        new BeforeAfterRule() {
            protected void afterAll(ITestMethodDescriptor descriptor)
                    throws Throwable {
                throw new MyException2();
            };
        }.apply(base, method, this).evaluate();
    }

    class MyException extends Throwable {
    }

    class MyException2 extends Throwable {
    }
}
