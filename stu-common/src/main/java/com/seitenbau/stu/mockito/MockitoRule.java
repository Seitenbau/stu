package com.seitenbau.stu.mockito;

import org.mockito.MockitoAnnotations;

import com.seitenbau.stu.rules.BeforeAfterRule;
import com.seitenbau.stu.rules.ITestMethodDescriptor;

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
