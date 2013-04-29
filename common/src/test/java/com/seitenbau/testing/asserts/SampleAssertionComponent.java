package com.seitenbau.testing.asserts;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SampleAssertionComponent
{

    public void verify(String msg)
    {
        assertThat(msg, is("Hello World"));
    }

}
