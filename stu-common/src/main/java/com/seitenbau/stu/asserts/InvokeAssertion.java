package com.seitenbau.stu.asserts;

public interface InvokeAssertion
{
    /**
     * Invoke the assert method on the target assert component.
     * @param targetAssertComponent the object which contains the assert or verify methods.
     * @param <T> the type of the target assert / verify component
     * @return the object on which the verify or assert method can be invoked.
     */
    <T> T invoke(T targetAssertComponent);

    void invoke(Verifier verifier) throws Exception;

}
