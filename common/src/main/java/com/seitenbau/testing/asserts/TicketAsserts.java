package com.seitenbau.testing.asserts;

import org.easymock.EasyMock;

public class TicketAsserts
{

    class TargetInvokeAssertion implements InvokeAssertion
    {
        @Override
        public <T> T invoke(T obj)
        {
            return obj;
        }

        @Override
        public void invoke(Verifier verifier) throws Exception
        {
            verifier.verify();
        }
    }

    class MockInvokeAssertion implements InvokeAssertion
    {
        @Override
        @SuppressWarnings("unchecked")
        public <T> T invoke(T obj)
        {
            return (T) EasyMock.createNiceMock(obj.getClass());
        }

        @Override
        public void invoke(Verifier verifier) throws Exception
        {
        }
    }

    private final TargetInvokeAssertion targetInvoker = new TargetInvokeAssertion();

    private final MockInvokeAssertion mockInvoker = new MockInvokeAssertion();

    public InvokeAssertion assumeTicketIsFixed(String ticked)
    {
        if (!ticked.startsWith("fixed:"))
        {
            return mockInvoker;
        }
        return targetInvoker;
    }

    public <T> T assumeTicketIsFixed(String ticked, T targetAssertionObj)
    {
        return assumeTicketIsFixed(ticked).invoke(targetAssertionObj);
    }

}
