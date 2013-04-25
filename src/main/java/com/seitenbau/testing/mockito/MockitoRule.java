package com.seitenbau.testing.mockito;

import org.mockito.MockitoAnnotations;

import com.seitenbau.testing.rules.BeforeAfterRule;
import com.seitenbau.testing.rules.ITestMethodDescriptor;

/**
 * Rule to run process @Mock with Mockito
 */
public class MockitoRule extends BeforeAfterRule {

    /** {@inheritDoc} */
    @Override
    protected void before(ITestMethodDescriptor descriptor) throws Throwable {
        MockitoAnnotations.initMocks(descriptor.getTarget());
    }

}
