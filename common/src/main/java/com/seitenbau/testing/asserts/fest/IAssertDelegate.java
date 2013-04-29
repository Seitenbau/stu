package com.seitenbau.testing.asserts.fest;

/**
 * Wrapper to delegate Assertions into a anonymous method
 * 
 * @param <A> The actual typ of the assertion
 */
public interface IAssertDelegate<A> 
{
    void doAssert(A assertApi) throws Exception;
}
